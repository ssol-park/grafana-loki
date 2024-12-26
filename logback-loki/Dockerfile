FROM openjdk:17-jdk-slim

WORKDIR /app

COPY ./build/libs/logback-loki-0.0.1.jar /app/grafana-loki.jar

RUN apt-get update && apt-get install -y curl && \
    apt-get clean && rm -rf /var/lib/apt/lists/*

RUN mkdir -p /data/log && chmod -R 777 /data/log

CMD ["java", "-Dspring.profiles.active=dev", "-jar", "/app/grafana-loki.jar"]


