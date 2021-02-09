FROM openjdk:15-jdk-alpine
LABEL maintainer="walter.longo74@gmail.com"
VOLUME /tmp
EXPOSE 8082
ADD target/ibolletta-service-0.1.1-SNAPSHOT.jar ibolletta-service.jar
ENTRYPOINT ["java", "-jar", "/ibolletta-service.jar"]