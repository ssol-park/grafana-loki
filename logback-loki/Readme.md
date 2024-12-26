# Loki4j Configuration Options

- https://loki4j.github.io/loki-logback-appender/docs/configuration


Loki4j는 Java 애플리케이션의 로그를 Grafana Loki로 전송하기 위한 Logback 앱렌더

---

## 일반 설정

- **`batchMaxItems` (기본값: 1000)**  
  Loki로 전송하기 전에 하나의 배치에 포함될 최대 로그 이벤트 수를 지정합니다.

- **`batchMaxBytes` (기본값: 4194304)**  
  하나의 배치가 포함할 수 있는 최대 바이트 수를 설정합니다.  
  이 값은 Loki 설정의 `server.grpc_server_max_recv_msg_size`보다 크지 않아야 합니다.

- **`batchTimeoutMs` (기본값: 60000)**  
  배치의 최대 대기 시간을 밀리초 단위로 설정합니다.  
  이 시간이 지나면 최대 항목 수나 바이트 수에 도달하지 않더라도 배치가 전송됩니다.

- **`sendQueueMaxBytes` (기본값: 41943040)**  
  전송 대기 중인 큐에 저장될 수 있는 최대 바이트 수를 설정합니다.  
  큐가 가득 차면 새로운 로그 이벤트는 삭제됩니다.

- **`maxRetries` (기본값: 2)**  
  Loki로 배치 전송이 실패했을 때 재시도할 최대 횟수를 설정합니다.  
  `ConnectException`이나 Loki로부터 `429`, `503` 상태 코드를 받을 경우 재시도합니다.  
  다른 예외나 4xx-5xx 상태 코드는 중복을 피하기 위해 재시도하지 않습니다.

- **`minRetryBackoffMs` (기본값: 500)**  
  실패한 배치를 재전송하기 전 초기 지연 시간을 밀리초 단위로 설정합니다.  
  지수 백오프(예: 0.5초, 1초, 2초, 4초 등)와 지터를 사용합니다.

- **`maxRetryBackoffMs` (기본값: 60000)**  
  실패한 배치를 재전송하기 전 최대 지연 시간을 밀리초 단위로 설정합니다.

- **`maxRetryJitterMs` (기본값: 500)**  
  재시도 지연 시간에 추가될 지터의 상한값을 밀리초 단위로 설정합니다.

- **`dropRateLimitedBatches` (기본값: false)**  
  `true`로 설정하면, Loki로부터 `429` 상태 코드(TooManyRequests)를 받은 배치를 재시도하지 않고 삭제합니다.

- **`internalQueuesCheckTimeoutMs` (기본값: 25)**  
  인코딩 또는 전송 큐가 비어 있을 때 Loki4j 스레드가 대기할 시간을 밀리초 단위로 설정합니다.  
  이 값을 줄이면 지연 시간이 감소하지만 CPU 사용량이 증가할 수 있습니다.

- **`useDirectBuffers` (기본값: true)**  
  중간 데이터 저장에 오프 힙 메모리를 사용할지 여부를 설정합니다.

- **`drainOnStop` (기본값: true)**  
  `true`로 설정하면, 앱렌더 종료 시 남아 있는 모든 이벤트를 전송하려고 시도합니다.  
  종료 절차가 더 오래 걸릴 수 있습니다.  
  `false`로 설정하면 전송되지 않은 이벤트는 삭제됩니다.

- **`metricsEnabled` (기본값: false)**  
  `true`로 설정하면, 앱렌더가 Micrometer를 사용하여 자체 메트릭을 보고합니다.

- **`verbose` (기본값: false)**  
  `true`로 설정하면, 앱렌더가 자체 디버그 로그를 stderr에 출력합니다.

---

## HTTP 설정

- **`http.url` (필수)**  
  배치를 전송할 Loki 엔드포인트를 지정합니다.

- **`http.connectionTimeoutMs` (기본값: 30000)**  
  Loki에 HTTP 연결을 설정하기 위해 기다릴 최대 시간을 밀리초 단위로 설정합니다.  
  이 시간이 지나면 오류로 보고됩니다.

- **`http.requestTimeoutMs` (기본값: 5000)**  
  Loki로부터 HTTP 응답을 기다릴 최대 시간을 밀리초 단위로 설정합니다.  
  이 시간이 지나면 오류로 보고됩니다.

- **`http.auth.username`**  
  기본 인증에 사용할 사용자 이름을 지정합니다.

- **`http.auth.password`**  
  기본 인증에 사용할 비밀번호를 지정합니다.

- **`http.tenantId`**  
  멀티 테넌트 모드에서 운영되는 Loki에 직접 로그를 전송할 때 필요한 테넌트 식별자를 지정합니다.  
  그렇지 않으면 이 설정은 효과가 없습니다.

---

## 포맷 설정

- **`format.label.pattern` (필수)**  
  로그 레코드의 라벨에 사용할 Logback 패턴을 지정합니다.

- **`format.label.pairSeparator`**  
  라벨을 구분하는 데 사용할 문자열을 지정합니다.  
  "regex:"로 시작하면 나머지 부분이 정규 표현식 구분자로 적용됩니다.  
  그렇지 않으면 제공된 문자열이 그대로 구분자로 사용됩니다.

- **`format.label.keyValueSeparator`**  
  라벨의 이름과 값을 구분하는 데 사용할 문자를 지정합니다.

- **`format.label.readMarkers` (기본값: false)**  
  `true`로 설정하면, Loki4j가 각 로그 레코드에 첨부된 `LabelMarker`를 스캔하여 해당 값을 레코드의 라벨에 추가합니다.

- **`format.label.nopex` (기본값: true)**  
  `true`로 설정하면, 예외 정보가 라벨에 추가되지 않습니다.  
  `false`로 설정하면, 적절한 포맷팅을 직접 처리해야 합니다.

- **`format.label.streamCache` (기본값: BoundAtomicMapCache)**  
  사용할 스트림 캐시의 구현을 지정합니다.  
  기본적으로 최대 1000개의 고유한 라벨 세트를 캐시합니다.

- **`format.staticLabels` (기본값: false)**  
  모든 로그 레코드에 대해 고정된 라벨을 추가합니다.

---

위 옵션들을 활용하여 Loki4j 로그 전송 방식을 원하는 대로 구성할 수 있습니다.
