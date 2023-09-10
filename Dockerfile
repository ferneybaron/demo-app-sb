FROM openjdk:17.0.1-jdk-slim
ADD build/libs/*.jar app.jar
ENTRYPOINT [ "java", "-jar", "app.jar"]