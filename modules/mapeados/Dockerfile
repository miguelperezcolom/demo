FROM maven:3.9.5-eclipse-temurin-21 AS builder

WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src /app/src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:21-jre-alpine AS runner
ARG JAR_FILE=/app/target/*.jar
WORKDIR /opt/app
COPY --from=builder /app/target/mapeados-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8091
ENTRYPOINT ["java", "-jar", "app.jar"]