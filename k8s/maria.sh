helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update
helm install mariadb bitnami/mariadb \
  --namespace mperezco \
  --create-namespace \
  --set primary.persistence.enabled=true \
  --set primary.rootPassword=riu