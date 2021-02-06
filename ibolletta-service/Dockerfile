FROM openjdk:11-jdk-slim
MAINTAINER Walter Longo <walter.longo74@gmail.com>
ADD target/bolletta-service-0.1.1-SNAPSHOT.jar bolletta-service.jar
ENTRYPOINT ["java", "-jar", "/bolletta-service.jar"]
EXPOSE 8082