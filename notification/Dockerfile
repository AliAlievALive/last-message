#FROM maven:3-openjdk-11-slim AS build
#WORKDIR /app
#COPY . .
#RUN mvn -B package

FROM maven:3-openjdk-11-slim
WORKDIR /app
COPY target/notification-0.0.1.jar app.jar
CMD ["java", "-jar", "app.jar"]
EXPOSE 8080

