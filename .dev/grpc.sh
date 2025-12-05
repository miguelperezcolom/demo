grpcurl --plaintext localhost:9090 list
grpcurl --plaintext localhost:9090 list net.devh.boot.grpc.example.MyService
# Linux (Static content)
grpcurl --plaintext -d '{"name": "test"}' localhost:9090 net.devh.boot.grpc.example.MyService/sayHello
# Windows or Linux (dynamic content)
grpcurl --plaintext -d "{\"name\": \"test\"}" localhost:9090 net.devh.boot.grpc.example.MyService/sayHello



