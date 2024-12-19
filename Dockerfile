# OpenJDK 17 이미지 사용
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# JAR 파일 복사
COPY build/libs/grafana-loki-0.0.1.jar /app/grafana-loki.jar

# Promtail 바이너리 다운로드
ADD https://github.com/grafana/loki/releases/download/v2.9.2/promtail-linux-amd64.zip /tmp/promtail.zip
RUN apt-get update && apt-get install -y unzip && \
    unzip /tmp/promtail.zip -d /usr/local/bin/ && \
    mv /usr/local/bin/promtail-linux-amd64 /usr/local/bin/promtail && \
    chmod +x /usr/local/bin/promtail && \
    rm /tmp/promtail.zip

# 로그 파일 디렉토리 생성
RUN mkdir -p /data/log /etc/promtail

# Promtail 설정 파일 복사
COPY promtail-config /etc/promtail

# Spring Boot 애플리케이션과 Promtail 실행
CMD ["sh", "-c", "java -jar /app/grafana-loki.jar & promtail -config.file=$PROMTAIL_CONFIG"]
