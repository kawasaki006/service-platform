FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/order-service-0.0.1.jar app.jar
EXPOSE 12000
ENTRYPOINT ["java","-jar","app.jar"]