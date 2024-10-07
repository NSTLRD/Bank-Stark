FROM openjdk:17-jdk-slim
LABEL authors="mentorly academy"

# Variables de entorno
ENV DATABASE_URL=jdbc:mysql://host.docker.internal:3306/bank_stark?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC
ENV DATABASE_USERNAME=root
ENV DATABASE_PASSWORD=root
ENV DATABASE_DRIVER=com.mysql.cj.jdbc.Driver
ENV DATABASE_DIALECT=org.hibernate.dialect.MySQL57Dialect

WORKDIR /app


COPY target/*.jar app.jar


EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]
