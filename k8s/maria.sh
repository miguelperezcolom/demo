helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update

kubectl create secret generic mariadb-secret-root \
  --from-literal=mariadb-root-password=riu \
  --namespace mperezco

helm install mariadb bitnami/mariadb \
  --namespace mperezco \
  -f maria-custom-values.yaml