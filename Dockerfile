# #
# # Build stage
# #
# FROM openjdk:17-jdk-alpine

# # Establecer el directorio de trabajo dentro del contenedor
# WORKDIR /app

# # Copiar el archivo JAR de la aplicación al contenedor
# COPY /target/products-service-1.0-SNAPSHOT.jar app.jar

# # Exponer el puerto en el que la aplicación escucha
# EXPOSE 80

# # Comando para ejecutar la aplicación
# ENTRYPOINT ["java", "-jar", "app.jar"]

#
# Build stage
#
# FROM maven:3.13.0-jdk-11-slim AS build
FROM maven:3.1.0-jdk-11 AS build
COPY src /home/app/src
COPY pom.xml /home/app
# RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:11-jre-slim
COPY --from=build /home/app/target/products-service-1.0-SNAPSHOT.jar /usr/local/lib/app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/app.jar"]
