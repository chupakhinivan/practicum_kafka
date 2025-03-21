# Итоговый проект первого модуля

---

Этот проект представляет собой реализацию Producer и Consumer для Apache Kafka, которые демонстрируют различные подходы к обработке сообщений: pull и push. Ниже приведена инструкция по запуску проекта и описание его работы.

## Инструкция по запуску

Все команды выполняются в корневой директории первого модуля

### 1. Сборка проекта с помощью Maven
Для сборки проекта выполните следующую команду:
```
mvn clean package
```
Эта команда соберет все модули проекта и создаст исполняемые JAR-файлы.

### 2. Запуск кластера Kafka с помощью Docker Compose
Для запуска кластера Kafka используйте Docker Compose. Выполните следующую команду:
```
docker-compose up -d
```
Эта команда запустит все необходимые сервисы в фоновом режиме.

### 3. Создание топика в Kafka
После запуска кластера Kafka необходимо создать топик. Для этого выполните следующую команду:
```
docker exec <container_id> /opt/bitnami/kafka/bin/kafka-topics.sh --create --topic message-topic --bootstrap-server localhost:9092 --partitions 3 --replication-factor 2 
```
Замените `<container_id>` на идентификатор контейнера Kafka, который можно узнать с помощью команды `docker ps`.

### 4. Запуск приложений
#### Pull-Consumer
- Ожидает, пока накопится 10 Кб данных или пройдет 10 секунд.
- После этого выводит все накопленные сообщения в лог.
- При ошибке десериализации выводит сообщение в лог и пропускает сообщение

Для его запуска выполните команду:

```
java -jar pull-consumer/target/pull-consumer-1.0-jar-with-dependencies.jar
```
#### Push-Consumer
- Немедленно забирает сообщения из топика, как только они становятся доступными.
- Каждое полученное сообщение выводится в лог.
- При ошибке десериализации выводит сообщение в лог и пропускает сообщение

Для его запуска выполните команду:

```
java -jar push-consumer/target/push-consumer-1.0-jar-with-dependencies.jar
```
#### Producer
- Отправляет сообщения в топик message-topic каждую секунду.
- Каждое отправленное сообщение выводится в лог. 
- Каждое 25е сообщение невалидно для десериализации.

Для его запуска выполните команду:

```
java -jar producer/target/producer-1.0-jar-with-dependencies.jar
```

### Дополнение
Получить подробную информацию по топику можно командой:
```
docker exec <container_id> /opt/bitnami/kafka/bin/kafka-topics.sh --describe --topic message-topic --bootstrap-server localhost:9092
```
Замените `<container_id>` на идентификатор контейнера Kafka, который можно узнать с помощью команды `docker ps`.

Kafka UI доступна по адресу `localhost:8080`