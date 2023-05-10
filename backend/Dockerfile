FROM eclipse-temurin:17-jdk-alpine
ADD target/backend-*.jar backend.jar
ENTRYPOINT ["java", "-jar", "/backend.jar"]