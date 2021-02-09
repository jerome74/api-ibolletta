FROM openjdk:15-jdk-alpine
LABEL maintainer="walter.longo74@gmail.com"
VOLUME /tmp
EXPOSE 8755
ADD target/zuul-api-gateway-0.1.1-SNAPSHOT.jar zuul-api-gateway.jar
ENTRYPOINT ["java", "-jar", "/zuul-api-gateway.jar"]