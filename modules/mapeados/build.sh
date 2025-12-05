newgrp docker
docker build -t mapeados:latest .
docker tag mapeados:latest localhost:5000/incoming/mapeados:v1
docker push localhost:5000/incoming/mapeados:v1
