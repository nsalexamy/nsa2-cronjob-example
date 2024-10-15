# Dockerfile for running Java application with OpenJDK 21
# This Dockerfile is supposed to be placed in the build/libs directory
# Use OpenJDK 21 as base image
FROM openjdk:21-jdk-bullseye
COPY ./nsa2-cronjob-example-0.0.1-SNAPSHOT.jar /usr/app/nsa2-cronjob-example.jar
COPY ./opentelemetry-javaagent.jar /usr/app/javaagent/opentelemetry-javaagent.jar
COPY ./nsa2-otel-extension-1.0-all.jar /usr/app/javaagent/nsa2-otel-extension-1.0-all.jar
WORKDIR /usr/app
#EXPOSE 8080
#EXPOSE 9464
#EXPOSE 8081
#ENTRYPOINT ["java", "-Xshare:off", "-Dotel.javaagent.extensions=/usr/app/javaagent/nsa2-otel-extension-1.0-all.jar", "-jar", "nsa2-cronjob-example.jar", "--spring.cloud.bootstrap.enabled=true", "--server.port=8080"]
#ENTRYPOINT ["java", "-jar", "nsa2-configwatcher-client-example-0.0.1-SNAPSHOT.jar", "--spring.cloud.bootstrap.enabled=true", "--spring.cloud.kubernetes.secrets.paths=/etc/secrets"]
