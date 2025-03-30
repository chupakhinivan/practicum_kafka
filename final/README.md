# Финальный проект

---
## Задание. Разработать аналитическую платформу для маркетплейса

### Инструкция по запуску

#### 1. Сборка проекта с помощью Maven
Для сборки проекта выполните следующую команду:
```
mvn clean package
```
Эта команда соберет все модули проекта и создаст исполняемые JAR-файлы.

#### 2. Запуск необходимых модулей с помощью Docker Compose
Для запуска модулей используйте Docker Compose (./infra/docker-compose.yaml). Выполните следующую команду:
```
docker-compose up -d
```
Эта команда запустит все необходимые сервисы в фоновом режиме.

##### 3. Деплой в spark cluster
После того, как все сервисы запустятся, зайти в контейнер `spark-master` и выполнить команду 

```
spark-submit \
--class com.chupakhin.App \
--master spark://spark-master:7077 \
--packages org.apache.spark:spark-sql-kafka-0-10_2.12:3.5.2 \
--deploy-mode cluster \
/app/app.jar
```

##### 4. Создание коннекторов

Для сохранения поступивших товаров в [products.txt](./infra/data/products.txt)
```
curl -X PUT \
-H "Content-Type: application/json" \
--data '{
"name": "file-stream-sink-filter-products",
"connector.class": "org.apache.kafka.connect.file.FileStreamSinkConnector",
"tasks.max": "1",
"topics": "filter-products-topic",
"file": "/data/products.txt",
"value.converter": "org.apache.kafka.connect.storage.StringConverter"
}' \
http://localhost:8083/connectors/file-stream-sink-filter-products/config
```
Для сохранения рекомендаций в [recommendations.txt](./infra/data/recommendations.txt)
```
curl -X PUT \
-H "Content-Type: application/json" \
--data '{
"name": "file-stream-sink-recommendations",
"connector.class": "org.apache.kafka.connect.file.FileStreamSinkConnector",
"tasks.max": "1",
"topics": "recommendation-topic",
"file": "/data/recommendations.txt", "key.converter":"org.apache.kafka.connect.storage.StringConverter",
"value.converter": "org.apache.kafka.connect.storage.StringConverter"
}' \
http://localhost:8083/connectors/file-stream-sink-recommendations/config
```

### Используемые инструменты
- Zookeeper
- Kafka
- Kafka UI
- Kafka Connect
- Kafka Streams
- Schema Registry
- Grafana
- Prometheus
- HDFS
- Spark


### Реализация

Отправить POST запрос в сервис `shop-api` на `http://localhost:8080/product` с [телом](./shop-api/src/main/resources/products.json)
Этот запрос эмулирует поступление новых товаров в маркетплейс

Сервис `shop-api` принимает список продуктов и отправляет их в Кафка, топик `products-topic`.
Далее сервис `product-filter`, используя `Kafka Streams` фильтрует данные из `products-topic` и складывает продукты, прошедшие фильтрацию, в топик `filter-products-topic`.
Фильтрацию не проходят товары с именами
```
    Вейп,
    Сигареты,
    Алкоголь,
    Виски,
    Пиво
```
Файловый коннектор следит за топиком `filter-products-topic` и перекладывает данные в [products.txt](./infra/data/products.txt)
После фильтрации [products.txt](./infra/data/products.txt) должен содержать 4 товара
```
{"name":"Умные часы","description":"Умные часы с функцией мониторинга здоровья, GPS и уведомлениями.","price":{"amount":4999.99,"currency":"RUB"},"category":"Электроника","brand":"XYZ","stock":{"available":150,"reserved":20},"sku":"XYZ-12345","tags":["умные часы","гаджеты","технологии"],"images":[{"url":"https://example.com/images/product1.jpg","alt":"Умные часы XYZ - вид спереди"},{"url":"https://example.com/images/product1_side.jpg","alt":"Умные часы XYZ - вид сбоку"}],"specifications":{"weight":"50g","dimensions":"42mm x 36mm x 10mm","battery_life":"24 hours","water_resistance":"IP68"},"index":"products","product_id":"12345","created_at":[2023,10,1,12,0],"updated_id":null,"store_id":"store_001"}
{"name":"Комплект постельного белья La Prima","description":"Комплект постельного белья из египетского хлопка с вышивкой, 100% хлопок, плотность 400 ТС.","price":{"amount":14999.99,"currency":"RUB"},"category":"Товары для дома","brand":"La Prima","stock":{"available":50,"reserved":10},"sku":"LP-BED-001","tags":["постельное белье","хлопок","люкс"],"images":[{"url":"https://example.com/images/la-prima-bed-set.jpg","alt":"Комплект постельного белья La Prima"},{"url":"https://example.com/images/la-prima-bed-set-detail.jpg","alt":"Деталь вышивки комплекта постельного белья La Prima"}],"specifications":{"weight":null,"dimensions":null,"battery_life":null,"water_resistance":null},"index":"products","product_id":"123456","created_at":[2023,10,1,12,0],"updated_id":null,"store_id":"store_001"}
{"name":"Смартфон ABC","description":"Флагманский смартфон с 6.7-дюймовым экраном и тройной камерой.","price":{"amount":79999.99,"currency":"RUB"},"category":"Электроника","brand":"ABC","stock":{"available":50,"reserved":10},"sku":"ABC-12346","tags":["смартфоны","флагманы","гаджеты"],"images":[{"url":"https://example.com/images/product2.jpg","alt":"Смартфон ABC - вид спереди"},{"url":"https://example.com/images/product2_side.jpg","alt":"Смартфон ABC - вид сбоку"}],"specifications":{"weight":"190g","dimensions":"160mm x 75mm x 8mm","battery_life":"48 hours","water_resistance":"IP67"},"index":"products","product_id":"12346","created_at":[2023,10,2,12,0],"updated_id":null,"store_id":"store_001"}
{"name":"Набор посуды 21maison Sakura","description":"Коллекция костяного фарфора с ручной росписью в японском стиле.","price":{"amount":9999.99,"currency":"RUB"},"category":"Товары для дома","brand":"21maison","stock":{"available":30,"reserved":5},"sku":"21M-SAK-001","tags":["посуда","фарфор","японский стиль"],"images":[{"url":"https://example.com/images/21maison-saka-set.jpg","alt":"Набор посуды 21maison Sakura"},{"url":"https://example.com/images/21maison-saka-set-detail.jpg","alt":"Деталь росписи набора посуды 21maison Sakura"}],"specifications":{"weight":null,"dimensions":null,"battery_life":null,"water_resistance":null},"index":"products","product_id":"234567","created_at":[2023,10,1,12,0],"updated_id":null,"store_id":"store_001"}
```

Далее нужно отправить GET запрос в сервис `client-api` от пользователя `Вася` `http://localhost:8081/product?userName=Вася&productName=Умные часы`
Этот запрос вернет товар по имени

Так же сервис `client-api` отправит в Кафка (`search-topic`) информацию о запросе пользователя.

Топик `search-topic` слушает сервис `analytics`, и при поступлении информации о запросе сохраняет его в HDFS.
Далее сервис `analytics` с помощью Spark формирует рекомендацию для пользователя и отправляет в топик `recomendations-topic`.

Топик `recomendations-topic` отслеживает файловый коннектор и перекладывает данные в [recommendations.txt](./infra/data/recommendations.txt)

Чтобы получить рекомендацию для пользователя нужно отправить GET запрос в сервис `client-api` `http://localhost:8081/recommendation/Вася`.

Сервис `client-api`, опираясь на рекомендацию (последний запрос на получение товара по имени от этого пользователя),
будет искать среди тэгов существующих товаров его рекомендацию. Если такие товары будут найдены, то они будут возвращены в качестве рекомендованых

Если запросить в сервисе `client-api` от пользователя `Вася` по товару `гаджеты` `http://localhost:8081/product?userName=Вася&productName=гаджеты`
То ничего не найдется, так как товаров с таким именем нет, но если после этого запросить рекоммендации `http://localhost:8081/recommendation/Вася`
То сервис выдаст 2 товара, которые имеют тэг `гаджеты`.
