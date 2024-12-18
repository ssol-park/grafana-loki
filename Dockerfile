FROM openjdk:17-jdk-slim

WORKDIR /app

COPY build/libs/grafana-loki-0.0.1.jar /app/grafana-loki.jar

CMD ["java", "-jar", "/app/grafana-loki.jar"]
