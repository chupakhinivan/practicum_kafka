# Задание 2. Создание собственного коннектора для переноса данных из Apache Kafka в Prometheus

---

Этот проект представляет собой коннетор для переноса данных из Apache Kafka в Prometheus. Ниже приведена инструкция по запуску проекта и описание его работы.

## Инструкция по запуску

### 1. Сборка проекта с помощью Maven
Для сборки проекта выполните в директории `/task_2` следующую команду:
```
mvn clean package
```
Эта команда соберет все модули проекта и создаст исполняемые JAR-файлы.

### 2. Запуск кластера Kafka с помощью Docker Compose

Дальнейшие команды выполняются в директории `/task_2/infra`

Для запуска кластера Kafka используйте Docker Compose. Выполните следующую команду:
```
docker-compose up -d
```
Эта команда запустит все необходимые сервисы в фоновом режиме.

### 3. Конфигурация коннектора

После того, как запустится Kafka Connect проверьте доступные плагины командой:

```
curl localhost:8083/connector-plugins | jq
```
Важно наличие `com.chupakhin.PrometheusSinkConnector`

Ожидаемый результат:
```
[
  {
    "class": "com.chupakhin.PrometheusSinkConnector",
    "type": "sink",
    "version": "1.0.0"
  },
  {
    "class": "org.apache.kafka.connect.mirror.MirrorCheckpointConnector",
    "type": "source",
    "version": "3.9.0"
  },
  {
    "class": "org.apache.kafka.connect.mirror.MirrorHeartbeatConnector",
    "type": "source",
    "version": "3.9.0"
  },
  {
    "class": "org.apache.kafka.connect.mirror.MirrorSourceConnector",
    "type": "source",
    "version": "3.9.0"
  }
]

```

Сконфигурируйте коннектор, выполнив следующую команду:
```
curl -X POST -H "Content-Type: application/json" --data @config.json http://localhost:8083/connectors
```

Далее проверьте статус коннектора командой:
```
curl http://localhost:8083/connectors/prometheus-connector/status | jq
```

Ожидаемый ответ:

```
{
  "name": "prometheus-connector",
  "connector": {
    "state": "RUNNING",
    "worker_id": "localhost:8083"
  },
  "tasks": [
    {
      "id": 0,
      "state": "RUNNING",
      "worker_id": "localhost:8083"
    }
  ],
  "type": "sink"
}

```
Если нет таски, попробуйте удалить коннектор и создать снова

```
curl --location --request DELETE 'http://localhost:8083/connectors/prometheus-connector'
```

В [Kafka UI](http://localhost:8085) должен создаться топик `metrics-topic`

### 4. Тестирование

В топик `metrics-topic` положить сообщение со значением из файла [metric-message.json](./infra/metric-message.json)

Перейти в [Prometheus](http://localhost:9090) и выполнить запрос `Alloc{job="metrics_service"}`

Получить значение метрики `Alloc`:
```
Alloc{instance="kafka-connect:8080", job="metrics_service"}  24293912
```