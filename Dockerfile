# syntax=docker/dockerfile:1

FROM openjdk:8-jre-alpine
EXPOSE 8080
WORKDIR /app
COPY /target/baitap10-0.0.1-SNAPSHOT.jar .
ENTRYPOINT [ "java", "-jar", "baitap10-0.0.1-SNAPSHOT.jar" ]