FROM openjdk:17
LABEL authors="ADMIN"

ARG JAR_FILE=target/dacn:0.0.1-SNAPSHOT.jar

COPY ${JAR_FILE} dacn.jar

ENTRYPOINT ["java", "-jar", "dacn.jar"]

EXPOSE 8080