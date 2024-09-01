FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/informator-0.0.1.jar /app/informator.jar

ENTRYPOINT ["sh", "-c", "java -jar /app/informator.jar"]
