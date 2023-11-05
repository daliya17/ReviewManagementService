FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/reviewManagementService-0.0.1-SNAPSHOT.jar /app

CMD ["java", "-jar", "reviewManagementService-0.0.1-SNAPSHOT.jar"]




