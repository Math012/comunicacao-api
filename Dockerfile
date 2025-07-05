FROM openjdk:11-jdk-alpine

WORKDIR /app

COPY target/spring-boot-starter-parent-0.0.1-SNAPSHOT.jar /app/spring-boot-starter-parent.jar

EXPOSE 8080

CMD ["java", "-jar", "/app/spring-boot-starter-parent.jar"]