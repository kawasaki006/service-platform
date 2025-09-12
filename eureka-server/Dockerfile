FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/eureka-server-0.0.1.jar app.jar
EXPOSE 8761
ENTRYPOINT ["java","-jar","app.jar"]