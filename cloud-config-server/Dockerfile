FROM openjdk:11-jdk-slim
MAINTAINER Walter Longo <walter.longo74@gmail.com>
ADD target/cloud-config-server-0.1.1-SNAPSHOT.jar cloud-config-server.jar
ENTRYPOINT ["java", "-jar", "/cloud-config-server.jar"]
EXPOSE 8888

