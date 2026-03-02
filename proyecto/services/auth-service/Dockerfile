# auth-service/Dockerfile
FROM openjdk:21-jdk
WORKDIR /app
COPY . .
RUN ./mvnw clean package -DskipTests
CMD ["java", "-jar", "target/auth-service.jar"]