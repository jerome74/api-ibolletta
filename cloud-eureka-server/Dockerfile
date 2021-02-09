FROM openjdk:15-jdk-alpine
LABEL maintainer="walter.longo74@gmail.com"
ADD target/cloud-eureka-server-0.1.1-SNAPSHOT.jar cloud-eureka-server.jar
ENTRYPOINT ["java" , "-jar", "/cloud-eureka-server.jar"]
EXPOSE 8761