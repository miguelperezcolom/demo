#!/bin/bash

# =======================================================
# CONFIGURACIÓN MANUAL NECESARIA
# =======================================================

# Si Redpanda no fue desplegado con un StatefulSet, necesitarás ejecutar su manifiesto por separado,
# o asegurarte de que ya esté corriendo.

NAMESPACE="mperezco"
MARIADB_ROOT_PASSWORD="<TU_CONTRASEÑA_ROOT_MARIADB>" # <-- ¡ACTUALIZA ESTO!
DEBEZIUM_USER_PASSWORD="<TU_CONTRASEÑA_CDC_SEGURA>" # <-- ¡ACTUALIZA ESTO!

# =======================================================
# 1. FUNCIÓN DE ESPERA DE PODS
# =======================================================

wait_for_pod() {
    local label=$1
    local name=$2
    echo "⚙️ Esperando a que el pod $name ($label) esté listo..."
    kubectl wait --namespace $NAMESPACE \
        --for=condition=ready pod \
        --selector=$label \
        --timeout=300s
    if [ $? -ne 0 ]; then
        echo "🚨 Error: El pod $name no se inició a tiempo. Abortando."
        exit 1
    fi
    echo "🟢 El pod $name está READY."
}

# =======================================================
# 2. INICIO DEL PROCESO DE RESTAURACIÓN
# =======================================================

echo "======================================================"
echo "🚀 INICIANDO RESTAURACIÓN DE INFRAESTRUCTURA CDC ($NAMESPACE)"
echo "======================================================"

# --- Limpieza de Namespace (Opcional, si quieres empezar de cero) ---
# kubectl delete namespace $NAMESPACE
# kubectl create namespace $NAMESPACE
# echo "Namespace $NAMESPACE recreado."


# --- A. APLICAR SERVICES PRIMERO (Estabilidad DNS) ---
echo -e "\n--- Aplicando SERVICES (DNS estables) ---"
kubectl apply -f service-redpanda.yaml
kubectl apply -f service-mariadb.yaml
kubectl apply -f service-mongodb.yaml


# --- B. APLICAR CONTROLADORES (StatefulSets y Deployments) ---
echo -e "\n--- Aplicando StatefulSets y Deployments (Creando Pods) ---"
# MariaDB (STS)
kubectl apply -f sts-mariadb.yaml
# MongoDB (Deployment)
kubectl apply -f deployment-mongodb.yaml
# Kafka Connect (Deployment)
kubectl apply -f deployment-connect.yaml


# --- C. ESPERAR A QUE LOS COMPONENTES CLAVE ESTÉN LISTOS ---
wait_for_pod "app=mariadb" "MariaDB"
wait_for_pod "app=my-mongodb" "MongoDB"
wait_for_pod "app=kafka-connect" "Kafka Connect" # Asumiendo la etiqueta "app=kafka-connect" para el pod de Connect

# Obtener nombres de Pods para comandos EXEC
MARIADB_POD=$(kubectl get pods -n $NAMESPACE -l app=mariadb -o jsonpath='{.items[0].metadata.name}')
MONGO_POD=$(kubectl get pods -n $NAMESPACE -l app=my-mongodb -o jsonpath='{.items[0].metadata.name}')


# --- D. CONFIGURACIÓN DE MARIA DB (Esquemas, Usuario y Permisos) ---
echo -e "\n--- 🔑 Configurando MariaDB: Esquema, Usuario Debezium y Permisos ---"

# 1. Aplicar el script de configuración y permisos (crea DB 'mapeados' y usuario 'debezium_user')
# Nota: La contraseña de root se pasa directamente ya que el script no la requiere
kubectl exec -it $MARIADB_POD -n $NAMESPACE -- mysql -u root -p"$MARIADB_ROOT_PASSWORD" < debezium_setup.sql

# 2. Aplicar el esquema de las tablas (solo estructura)
kubectl exec -it $MARIADB_POD -n $NAMESPACE -- mysql -u root -p"$MARIADB_ROOT_PASSWORD" mapeados < db_mapeados_schema.sql

# Si tuvieras datos de respaldo (db_mapeados_backup.sql) los cargarías aquí:
# kubectl exec -it $MARIADB_POD -n $NAMESPACE -- mysql -u root -p"$MARIADB_ROOT_PASSWORD" mapeados < db_mapeados_backup.sql


# --- E. CONFIGURACIÓN DE MONGODB (Importación de Datos) ---
echo -e "\n--- 📥 Importando datos de MongoDB ---"
if [ -d "./mongo_backup/dump" ]; then
    # 1. Copia el dump a la ubicación temporal del Pod
    kubectl cp ./mongo_backup/dump $NAMESPACE/$MONGO_POD:/tmp/dump

    # 2. Ejecuta mongorestore dentro del Pod
    kubectl exec -it $MONGO_POD -n $NAMESPACE -- mongorestore /tmp/dump
    echo "🟢 Datos de MongoDB importados."
else
    echo "🟡 Directorio ./mongo_backup/dump no encontrado. Omitiendo importación de datos de MongoDB."
fi


# --- F. CARGAR EL CONECTOR DEBEZIUM ---
echo -e "\n--- 🔗 Cargando Conector Debezium ---"

# 1. Hacer port-forward al Pod de Connect en segundo plano (necesario si el Service no es de tipo LoadBalancer)
CONNECT_POD=$(kubectl get pods -n $NAMESPACE -l app=kafka-connect -o jsonpath='{.items[0].metadata.name}')
kubectl port-forward $CONNECT_POD 8083:8083 -n $NAMESPACE &
PORT_FORWARD_PID=$!
sleep 5 # Dar tiempo al port-forward para iniciarse

# 2. Cargar la configuración del conector (POST, usando el formato con {name: "...", config: {...}})
# NOTA: Debes adaptar debezium-mysql-config.json si necesita el formato POST.
curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" \
  http://localhost:8083/connectors/ \
  -d @debezium-mysql-config.json

# 3. Terminar el port-forward
kill $PORT_FORWARD_PID

echo -e "\n======================================================"
echo "✅ RESTAURACIÓN COMPLETADA."
echo "   MariaDB, MongoDB y Debezium están corriendo."
echo "======================================================"