FROM openjdk:21-jdk
WORKDIR /app
COPY target/paymentService-0.0.1-SNAPSHOT.jar paymentService.jar
CMD ["java", "-jar", "paymentService.jar"]