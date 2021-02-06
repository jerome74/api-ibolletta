FROM openjdk:11-jdk-slim
MAINTAINER Walter Longo <walter.longo74@gmail.com>
ADD target/api-rest-0.1.1-SNAPSHOT.jar api-rest.jar
ENTRYPOINT ["java", "-jar", "/api-rest.jar"]
EXPOSE 8081