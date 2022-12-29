FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8081
COPY target/IntegrationTests-0.0.1-SNAPSHOT.jar prod.jar
CMD ["java", "-jar", "prod.jar"]