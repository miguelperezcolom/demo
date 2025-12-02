Guía Rápida de Recuperación

En caso de que pierdas el clúster, este es el orden de las acciones:

    Limpiar sts-mariadb.yaml: Edita el archivo sts-mariadb.yaml para eliminar los campos de metadatos específicos del clúster (creationTimestamp, resourceVersion, uid) y la sección completa status.

    Aplicar K8s Base:
    Bash

kubectl create namespace mperezco
kubectl apply -f sts-mariadb.yaml
# Aplicar otros StatefulSets si existen (p. ej., Redpanda)
kubectl apply -f deployment-connect.yaml
kubectl apply -f service-mariadb.yaml
kubectl apply -f service-redpanda.yaml
kubectl apply -f service-mongodb.yaml
kubectl apply -f deployment-mongodb.yaml

Configurar MariaDB (Esquema y Permisos):

    Espera a que el Pod de MariaDB esté READY.

    Ejecuta el script de configuración y el dump de datos (usando el Pod de MariaDB):
    Bash

    # Crea el esquema
    kubectl exec -it <MARIADB_POD> -n mperezco -- mysql -u root -p < db_mapeados_schema.sql
    # Asegúrate de tener db_mapeados_backup.sql si necesitas los datos
    kubectl exec -it <MARIADB_POD> -n mperezco -- mysql -u root -p < debezium_setup.sql

Cargar Conector Debezium:

    Espera a que el Pod de Kafka Connect esté READY.

    Realiza el port-forward al Pod de Connect (8083).

    Carga la configuración final del conector:
    Bash

        curl -i -X POST -H "Accept:application/json" -H "Content-Type:application/json" \
          http://localhost:8083/connectors/ \
          -d @debezium-mysql-config.json

¡Con esto, todo tu entorno de CDC estará completamente restaurado y listo para funcionar!