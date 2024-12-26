FROM grafana/loki:2.9.2

USER root

RUN apk add --no-cache tzdata && \
    cp /usr/share/zoneinfo/Asia/Seoul /etc/localtime && \
    echo "Asia/Seoul" > /etc/timezone && \
    apk del tzdata

USER loki

EXPOSE 3100

# --- Documentation ---
# Loki 기본 정보:
# Loki는 기본적으로 UTC 타임존을 기준으로 로그를 처리하며, 분산 환경에서의 시간 일관성을 보장하기 위해 설계되었습니다.
# 저장된 UTC 시간은 Grafana와 같은 클라이언트에서 필요한 타임존으로 변환하여 표시할 수 있습니다.

# Dockerfile 주석:
# 1. 기본 사용자:
#    - Loki 기본 이미지는 'loki' 사용자로 동작하도록 설계되어 있습니다.
#    - 'root' 사용자로 전환 후 필요한 작업을 수행한 뒤 다시 'loki' 사용자로 복구해야 합니다.

# 2. 타임존 설정:
#    - 이 Dockerfile은 컨테이너 내부의 시스템 타임존을 "Asia/Seoul"로 변경합니다.
#    - 이는 컨테이너 내부에서 실행되는 도구(예: date)와 디버깅 작업에서 유용할 수 있습니다.
#    - 그러나 Loki 자체는 여전히 UTC를 기준으로 로그를 저장하며, 타임존 설정은 로그 저장 동작에 영향을 미치지 않습니다.

# 3. 에러 발생 가능성:
#    - '/loki' 디렉터리 권한 문제:
#      - Loki는 '/loki' 디렉터리에서 데이터를 저장합니다.
#      - 'root' 사용자로 작업 후 권한 복구가 제대로 이루어지지 않으면 "mkdir: no such file or directory" 또는 "permission denied" 오류가 발생할 수 있습니다.
#    - ENTRYPOINT 변경:
#      - Loki 이미지는 기본 ENTRYPOINT로 '/usr/bin/loki'를 사용하며, 이를 덮어쓰면 실행 오류가 발생할 수 있습니다.

# 4. 타임존 철학:
#    - Loki는 기본적으로 저장 시 UTC를 기준으로 동작합니다.
#    - 이는 분산 환경에서 시간의 일관성을 유지하기 위한 설계이며, 한국 서버에서만 사용하는 경우에도 로그 저장은 UTC로 진행됩니다.
#    - 클라이언트(Grafana)에서 타임존 변환을 적용하여 한국 시간으로 표시하도록 설정하는 것이 권장됩니다.
