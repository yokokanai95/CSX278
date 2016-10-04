FROM openjdk:8
MAINTAINER yoko.m.kanai@vanderbilt.edu
WORKDIR /app/jhipster-project
ADD ./build/libs/api-samples-0.0.1-SNAPSHOT.war .
EXPOSE 8080
CMD java -jar api-samples-0.0.1-SNAPSHOT.war --spring.profiles.active=dev,swagger
