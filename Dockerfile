FROM openjdk:18
WORKDIR /app
COPY ./target/loyalty-service-0.0.1-SNAPSHOT.jar /app
EXPOSE 8080
CMD ["java", "-jar", "loyalty-service-0.0.1-SNAPSHOT.jar"]