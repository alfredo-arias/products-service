# Usar una imagen base de Java
FROM openjdk:17-jdk-alpine

# Establecer el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copiar el archivo JAR de la aplicación al contenedor
COPY /target/products-service-1.0-SNAPSHOT.jar app.jar

# Exponer el puerto en el que la aplicación escucha
EXPOSE 80

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
