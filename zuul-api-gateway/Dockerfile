FROM openjdk:11-jdk-slim
MAINTAINER Walter Longo <walter.longo74@gmail.com>
ADD target/zuul-api-gateway-0.1.1-SNAPSHOT.jar zuul-api-gateway.jar
ENTRYPOINT ["java", "-jar", "/zuul-api-gateway.jar"]
EXPOSE 8755