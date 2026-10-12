FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

RUN addgroup -S addgroup && adduser -S appuser -G appgroup

USER appuser

COPY target/java-app-1.0.0.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
