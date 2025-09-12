FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/user-service-0.0.1.jar app.jar
EXPOSE 8000
ENTRYPOINT ["java","-jar","app.jar"]