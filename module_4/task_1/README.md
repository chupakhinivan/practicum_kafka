# Задание 1. Балансировка партиций и диагностика кластера

---
### 1. Создаем топик `balanced-topic` с 8 партициями и фактором репликации 3
![create-topic](images/create-topic.png)

### 2. Текущее распределение партиций
![describe](images/describe.png)

### 3. Создаем JSON-файл [reassignment.json](reassignment.json) для перераспределения партиций
![reassignment-create](images/reassignment-create.png)

### 4. Перераспределяем партиции
![reassignment-execute](images/reassignment-execute.png)

### 5. Проверяем статус перераспределения
![reassignment-status](images/reassignment-status.png)

### 6. Убеждаемся, что конфигурация изменилась
![describe-new](images/describe-new.png)

### 7. Моделируем сбой брокера
#### a. Останавливаем брокер `kafka-1` командой `docker stop kafka-1`
#### b. Проверяем состояние топиков после сбоя
![describe-crash](images/describe-crash.png)
#### c. Запускаем брокер `kafka-1` командой `docker start kafka-1`
#### d. Проверяем синхронизацию топиков после восстановления
![describe-after-crash](images/describe-after-crash.png)

Видим, что лидеры по брокерам не сбалансированы

#### Запустим команду выбора предпочтительного лидера
![leader-election](images/leader-election.png)

### Проверим текущее распределение партиций
![describe-fix-after-crash](images/describe-fix-after-crash.png)