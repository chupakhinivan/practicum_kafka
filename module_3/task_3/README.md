# Задание 3. Получение лога вывода Debezium PostgresConnector

---

Лог при запуске коннектора
```
[2025-02-23 16:52:22,305] INFO Loading the custom source info struct maker plugin: io.debezium.connector.postgresql.PostgresSourceInfoStructMaker (io.debezium.config.CommonConnectorConfig)
[2025-02-23 16:52:22,382] INFO Successfully tested connection for jdbc:postgresql://postgres:5432/customers with user 'postgres-user' (io.debezium.connector.postgresql.PostgresConnector)
[2025-02-23 16:52:22,391] INFO Connection gracefully closed (io.debezium.jdbc.JdbcConnection)
[2025-02-23 16:52:22,393] INFO AbstractConfig values: 
 (org.apache.kafka.common.config.AbstractConfig)
[2025-02-23 16:52:22,400] INFO [Worker clientId=connect-localhost:8083, groupId=kafka-connect] Connector pg-connector config updated (org.apache.kafka.connect.runtime.distributed.DistributedHerder)
[2025-02-23 16:52:22,400] INFO [Worker clientId=connect-localhost:8083, groupId=kafka-connect] Rebalance started (org.apache.kafka.connect.runtime.distributed.WorkerCoordinator)
[2025-02-23 16:52:22,400] INFO [Worker clientId=connect-localhost:8083, groupId=kafka-connect] (Re-)joining group (org.apache.kafka.connect.runtime.distributed.WorkerCoordinator)
[2025-02-23 16:52:22,402] INFO [Worker clientId=connect-localhost:8083, groupId=kafka-connect] Successfully joined group with generation Generation{generationId=2, memberId='connect-localhost:8083-d2f1bdbc-7d2c-4cbd-8bf7-7a326d6ba4ef', protocol='sessioned'} (org.apache.kafka.connect.runtime.distributed.WorkerCoordinator)
[2025-02-23 16:52:22,405] INFO [Worker clientId=connect-localhost:8083, groupId=kafka-connect] Successfully synced group in generation Generation{generationId=2, memberId='connect-localhost:8083-d2f1bdbc-7d2c-4cbd-8bf7-7a326d6ba4ef', protocol='sessioned'} (org.apache.kafka.connect.runtime.distributed.WorkerCoordinator)
[2025-02-23 16:52:22,405] INFO [Worker clientId=connect-localhost:8083, groupId=kafka-connect] Joined group at generation 2 with protocol version 2 and got assignment: Assignment{error=0, leader='connect-localhost:8083-d2f1bdbc-7d2c-4cbd-8bf7-7a326d6ba4ef', leaderUrl='http://localhost:8083/', offset=2, connectorIds=[pg-connector], taskIds=[], revokedConnectorIds=[], revokedTaskIds=[], delay=0} with rebalance delay: 0 (org.apache.kafka.connect.runtime.distributed.DistributedHerder)
[2025-02-23 16:52:22,405] INFO [Worker clientId=connect-localhost:8083, groupId=kafka-connect] Starting connectors and tasks using config offset 2 (org.apache.kafka.connect.runtime.distributed.DistributedHerder)
[2025-02-23 16:52:22,406] INFO [Worker clientId=connect-localhost:8083, groupId=kafka-connect] Starting connector pg-connector (org.apache.kafka.connect.runtime.distributed.DistributedHerder)
[2025-02-23 16:52:22,408] INFO Creating connector pg-connector of type io.debezium.connector.postgresql.PostgresConnector (org.apache.kafka.connect.runtime.Worker)
[2025-02-23 16:52:22,408] INFO SourceConnectorConfig values: 
	config.action.reload = restart
	connector.class = io.debezium.connector.postgresql.PostgresConnector
	errors.log.enable = false
	errors.log.include.messages = false
	errors.retry.delay.max.ms = 60000
	errors.retry.timeout = 0
	errors.tolerance = none
	exactly.once.support = requested
	header.converter = null
	key.converter = null
	name = pg-connector
	offsets.storage.topic = null
	predicates = []
	tasks.max = 1
	tasks.max.enforce = true
	topic.creation.groups = []
	transaction.boundary = poll
	transaction.boundary.interval.ms = null
	transforms = [unwrap]
	value.converter = null
 (org.apache.kafka.connect.runtime.SourceConnectorConfig)
[2025-02-23 16:52:22,408] INFO EnrichedConnectorConfig values: 
	config.action.reload = restart
	connector.class = io.debezium.connector.postgresql.PostgresConnector
	errors.log.enable = false
	errors.log.include.messages = false
	errors.retry.delay.max.ms = 60000
	errors.retry.timeout = 0
	errors.tolerance = none
	exactly.once.support = requested
	header.converter = null
	key.converter = null
	name = pg-connector
	offsets.storage.topic = null
	predicates = []
	tasks.max = 1
	tasks.max.enforce = true
	topic.creation.groups = []
	transaction.boundary = poll
	transaction.boundary.interval.ms = null
	transforms = [unwrap]
	transforms.unwrap.add.fields = []
	transforms.unwrap.add.fields.prefix = __
	transforms.unwrap.add.headers = []
	transforms.unwrap.add.headers.prefix = __
	transforms.unwrap.delete.handling.mode = rewrite
	transforms.unwrap.drop.fields.from.key = false
	transforms.unwrap.drop.fields.header.name = null
	transforms.unwrap.drop.fields.keep.schema.compatible = true
	transforms.unwrap.drop.tombstones = false
	transforms.unwrap.negate = false
	transforms.unwrap.predicate = null
	transforms.unwrap.route.by.field = 
	transforms.unwrap.type = class io.debezium.transforms.ExtractNewRecordState
	value.converter = null
 (org.apache.kafka.connect.runtime.ConnectorConfig$EnrichedConnectorConfig)
[2025-02-23 16:52:22,410] INFO EnrichedSourceConnectorConfig values: 
	config.action.reload = restart
	connector.class = io.debezium.connector.postgresql.PostgresConnector
	errors.log.enable = false
	errors.log.include.messages = false
	errors.retry.delay.max.ms = 60000
	errors.retry.timeout = 0
	errors.tolerance = none
	exactly.once.support = requested
	header.converter = null
	key.converter = null
	name = pg-connector
	offsets.storage.topic = null
	predicates = []
	tasks.max = 1
	tasks.max.enforce = true
	topic.creation.default.exclude = []
	topic.creation.default.include = [.*]
	topic.creation.default.partitions = -1
	topic.creation.default.replication.factor = -1
	topic.creation.groups = []
	transaction.boundary = poll
	transaction.boundary.interval.ms = null
	transforms = [unwrap]
	value.converter = null
 (org.apache.kafka.connect.runtime.SourceConnectorConfig$EnrichedSourceConnectorConfig)
[2025-02-23 16:52:22,410] INFO EnrichedConnectorConfig values: 
	config.action.reload = restart
	connector.class = io.debezium.connector.postgresql.PostgresConnector
	errors.log.enable = false
	errors.log.include.messages = false
	errors.retry.delay.max.ms = 60000
	errors.retry.timeout = 0
	errors.tolerance = none
	exactly.once.support = requested
	header.converter = null
	key.converter = null
	name = pg-connector
	offsets.storage.topic = null
	predicates = []
	tasks.max = 1
	tasks.max.enforce = true
	topic.creation.default.exclude = []
	topic.creation.default.include = [.*]
	topic.creation.default.partitions = -1
	topic.creation.default.replication.factor = -1
	topic.creation.groups = []
	transaction.boundary = poll
	transaction.boundary.interval.ms = null
	transforms = [unwrap]
	transforms.unwrap.add.fields = []
	transforms.unwrap.add.fields.prefix = __
	transforms.unwrap.add.headers = []
	transforms.unwrap.add.headers.prefix = __
	transforms.unwrap.delete.handling.mode = rewrite
	transforms.unwrap.drop.fields.from.key = false
	transforms.unwrap.drop.fields.header.name = null
	transforms.unwrap.drop.fields.keep.schema.compatible = true
	transforms.unwrap.drop.tombstones = false
	transforms.unwrap.negate = false
	transforms.unwrap.predicate = null
	transforms.unwrap.route.by.field = 
	transforms.unwrap.type = class io.debezium.transforms.ExtractNewRecordState
	value.converter = null
 (org.apache.kafka.connect.runtime.ConnectorConfig$EnrichedConnectorConfig)
[2025-02-23 16:52:22,413] INFO Instantiated connector pg-connector with version 3.0.6.Final of type class io.debezium.connector.postgresql.PostgresConnector (org.apache.kafka.connect.runtime.Worker)
[2025-02-23 16:52:22,413] INFO Finished creating connector pg-connector (org.apache.kafka.connect.runtime.Worker)
[2025-02-23 16:52:22,413] INFO [Worker clientId=connect-localhost:8083, groupId=kafka-connect] Finished starting connectors and tasks (org.apache.kafka.connect.runtime.distributed.DistributedHerder)
[2025-02-23 16:52:22,418] INFO SourceConnectorConfig values: 
	config.action.reload = restart
	connector.class = io.debezium.connector.postgresql.PostgresConnector
	errors.log.enable = false
	errors.log.include.messages = false
	errors.retry.delay.max.ms = 60000
	errors.retry.timeout = 0
	errors.tolerance = none
	exactly.once.support = requested
	header.converter = null
	key.converter = null
	name = pg-connector
	offsets.storage.topic = null
	predicates = []
	tasks.max = 1
	tasks.max.enforce = true
	topic.creation.groups = []
	transaction.boundary = poll
	transaction.boundary.interval.ms = null
	transforms = [unwrap]
	value.converter = null
 (org.apache.kafka.connect.runtime.SourceConnectorConfig)
[2025-02-23 16:52:22,418] INFO EnrichedConnectorConfig values: 
	config.action.reload = restart
	connector.class = io.debezium.connector.postgresql.PostgresConnector
	errors.log.enable = false
	errors.log.include.messages = false
	errors.retry.delay.max.ms = 60000
	errors.retry.timeout = 0
	errors.tolerance = none
	exactly.once.support = requested
	header.converter = null
	key.converter = null
	name = pg-connector
	offsets.storage.topic = null
	predicates = []
	tasks.max = 1
	tasks.max.enforce = true
	topic.creation.groups = []
	transaction.boundary = poll
	transaction.boundary.interval.ms = null
	transforms = [unwrap]
	transforms.unwrap.add.fields = []
	transforms.unwrap.add.fields.prefix = __
	transforms.unwrap.add.headers = []
	transforms.unwrap.add.headers.prefix = __
	transforms.unwrap.delete.handling.mode = rewrite
	transforms.unwrap.drop.fields.from.key = false
	transforms.unwrap.drop.fields.header.name = null
	transforms.unwrap.drop.fields.keep.schema.compatible = true
	transforms.unwrap.drop.tombstones = false
	transforms.unwrap.negate = false
	transforms.unwrap.predicate = null
	transforms.unwrap.route.by.field = 
	transforms.unwrap.type = class io.debezium.transforms.ExtractNewRecordState
	value.converter = null
 (org.apache.kafka.connect.runtime.ConnectorConfig$EnrichedConnectorConfig)
[2025-02-23 16:52:22,419] INFO EnrichedSourceConnectorConfig values: 
	config.action.reload = restart
	connector.class = io.debezium.connector.postgresql.PostgresConnector
	errors.log.enable = false
	errors.log.include.messages = false
	errors.retry.delay.max.ms = 60000
	errors.retry.timeout = 0
	errors.tolerance = none
	exactly.once.support = requested
	header.converter = null
	key.converter = null
	name = pg-connector
	offsets.storage.topic = null
	predicates = []
	tasks.max = 1
	tasks.max.enforce = true
	topic.creation.default.exclude = []
	topic.creation.default.include = [.*]
	topic.creation.default.partitions = -1
	topic.creation.default.replication.factor = -1
	topic.creation.groups = []
	transaction.boundary = poll
	transaction.boundary.interval.ms = null
	transforms = [unwrap]
	value.converter = null
 (org.apache.kafka.connect.runtime.SourceConnectorConfig$EnrichedSourceConnectorConfig)
[2025-02-23 16:52:22,419] INFO EnrichedConnectorConfig values: 
	config.action.reload = restart
	connector.class = io.debezium.connector.postgresql.PostgresConnector
	errors.log.enable = false
	errors.log.include.messages = false
	errors.retry.delay.max.ms = 60000
	errors.retry.timeout = 0
	errors.tolerance = none
	exactly.once.support = requested
	header.converter = null
	key.converter = null
	name = pg-connector
	offsets.storage.topic = null
	predicates = []
	tasks.max = 1
	tasks.max.enforce = true
	topic.creation.default.exclude = []
	topic.creation.default.include = [.*]
	topic.creation.default.partitions = -1
	topic.creation.default.replication.factor = -1
	topic.creation.groups = []
	transaction.boundary = poll
	transaction.boundary.interval.ms = null
	transforms = [unwrap]
	transforms.unwrap.add.fields = []
	transforms.unwrap.add.fields.prefix = __
	transforms.unwrap.add.headers = []
	transforms.unwrap.add.headers.prefix = __
	transforms.unwrap.delete.handling.mode = rewrite
	transforms.unwrap.drop.fields.from.key = false
	transforms.unwrap.drop.fields.header.name = null
	transforms.unwrap.drop.fields.keep.schema.compatible = true
	transforms.unwrap.drop.tombstones = false
	transforms.unwrap.negate = false
	transforms.unwrap.predicate = null
	transforms.unwrap.route.by.field = 
	transforms.unwrap.type = class io.debezium.transforms.ExtractNewRecordState
	value.converter = null
 (org.apache.kafka.connect.runtime.ConnectorConfig$EnrichedConnectorConfig)
[2025-02-23 16:52:22,422] INFO 172.19.0.1 - - [23/Feb/2025:16:52:22 +0000] "PUT /connectors/pg-connector/config HTTP/1.1" 201 741 "-" "curl/7.81.0" 173 (org.apache.kafka.connect.runtime.rest.RestServer)
[2025-02-23 16:52:22,432] INFO [Worker clientId=connect-localhost:8083, groupId=kafka-connect] Tasks [pg-connector-0] configs updated (org.apache.kafka.connect.runtime.distributed.DistributedHerder)
[2025-02-23 16:52:22,433] INFO [Worker clientId=connect-localhost:8083, groupId=kafka-connect] Rebalance started (org.apache.kafka.connect.runtime.distributed.WorkerCoordinator)
[2025-02-23 16:52:22,433] INFO [Worker clientId=connect-localhost:8083, groupId=kafka-connect] (Re-)joining group (org.apache.kafka.connect.runtime.distributed.WorkerCoordinator)
[2025-02-23 16:52:22,434] INFO [Worker clientId=connect-localhost:8083, groupId=kafka-connect] Successfully joined group with generation Generation{generationId=3, memberId='connect-localhost:8083-d2f1bdbc-7d2c-4cbd-8bf7-7a326d6ba4ef', protocol='sessioned'} (org.apache.kafka.connect.runtime.distributed.WorkerCoordinator)
[2025-02-23 16:52:22,438] INFO [Worker clientId=connect-localhost:8083, groupId=kafka-connect] Successfully synced group in generation Generation{generationId=3, memberId='connect-localhost:8083-d2f1bdbc-7d2c-4cbd-8bf7-7a326d6ba4ef', protocol='sessioned'} (org.apache.kafka.connect.runtime.distributed.WorkerCoordinator)
[2025-02-23 16:52:22,438] INFO [Worker clientId=connect-localhost:8083, groupId=kafka-connect] Joined group at generation 3 with protocol version 2 and got assignment: Assignment{error=0, leader='connect-localhost:8083-d2f1bdbc-7d2c-4cbd-8bf7-7a326d6ba4ef', leaderUrl='http://localhost:8083/', offset=4, connectorIds=[pg-connector], taskIds=[pg-connector-0], revokedConnectorIds=[], revokedTaskIds=[], delay=0} with rebalance delay: 0 (org.apache.kafka.connect.runtime.distributed.DistributedHerder)
[2025-02-23 16:52:22,439] INFO [Worker clientId=connect-localhost:8083, groupId=kafka-connect] Starting connectors and tasks using config offset 4 (org.apache.kafka.connect.runtime.distributed.DistributedHerder)
[2025-02-23 16:52:22,439] INFO [Worker clientId=connect-localhost:8083, groupId=kafka-connect] Starting task pg-connector-0 (org.apache.kafka.connect.runtime.distributed.DistributedHerder)
[2025-02-23 16:52:22,441] INFO Creating task pg-connector-0 (org.apache.kafka.connect.runtime.Worker)
[2025-02-23 16:52:22,442] INFO ConnectorConfig values: 
	config.action.reload = restart
	connector.class = io.debezium.connector.postgresql.PostgresConnector
	errors.log.enable = false
	errors.log.include.messages = false
	errors.retry.delay.max.ms = 60000
	errors.retry.timeout = 0
	errors.tolerance = none
	header.converter = null
	key.converter = null
	name = pg-connector
	predicates = []
	tasks.max = 1
	tasks.max.enforce = true
	transforms = [unwrap]
	value.converter = null
 (org.apache.kafka.connect.runtime.ConnectorConfig)
[2025-02-23 16:52:22,442] INFO EnrichedConnectorConfig values: 
	config.action.reload = restart
	connector.class = io.debezium.connector.postgresql.PostgresConnector
	errors.log.enable = false
	errors.log.include.messages = false
	errors.retry.delay.max.ms = 60000
	errors.retry.timeout = 0
	errors.tolerance = none
	header.converter = null
	key.converter = null
	name = pg-connector
	predicates = []
	tasks.max = 1
	tasks.max.enforce = true
	transforms = [unwrap]
	transforms.unwrap.add.fields = []
	transforms.unwrap.add.fields.prefix = __
	transforms.unwrap.add.headers = []
	transforms.unwrap.add.headers.prefix = __
	transforms.unwrap.delete.handling.mode = rewrite
	transforms.unwrap.drop.fields.from.key = false
	transforms.unwrap.drop.fields.header.name = null
	transforms.unwrap.drop.fields.keep.schema.compatible = true
	transforms.unwrap.drop.tombstones = false
	transforms.unwrap.negate = false
	transforms.unwrap.predicate = null
	transforms.unwrap.route.by.field = 
	transforms.unwrap.type = class io.debezium.transforms.ExtractNewRecordState
	value.converter = null
 (org.apache.kafka.connect.runtime.ConnectorConfig$EnrichedConnectorConfig)
[2025-02-23 16:52:22,445] INFO TaskConfig values: 
	task.class = class io.debezium.connector.postgresql.PostgresConnectorTask
 (org.apache.kafka.connect.runtime.TaskConfig)
[2025-02-23 16:52:22,447] INFO Instantiated task pg-connector-0 with version 3.0.6.Final of type io.debezium.connector.postgresql.PostgresConnectorTask (org.apache.kafka.connect.runtime.Worker)
[2025-02-23 16:52:22,447] INFO JsonConverterConfig values: 
	converter.type = key
	decimal.format = BASE64
	replace.null.with.default = true
	schemas.cache.size = 1000
	schemas.enable = true
 (org.apache.kafka.connect.json.JsonConverterConfig)
[2025-02-23 16:52:22,448] INFO Set up the key converter class org.apache.kafka.connect.json.JsonConverter for task pg-connector-0 using the worker config (org.apache.kafka.connect.runtime.Worker)
[2025-02-23 16:52:22,448] INFO JsonConverterConfig values: 
	converter.type = value
	decimal.format = BASE64
	replace.null.with.default = true
	schemas.cache.size = 1000
	schemas.enable = true
 (org.apache.kafka.connect.json.JsonConverterConfig)
[2025-02-23 16:52:22,448] INFO Set up the value converter class org.apache.kafka.connect.json.JsonConverter for task pg-connector-0 using the worker config (org.apache.kafka.connect.runtime.Worker)
[2025-02-23 16:52:22,448] INFO Set up the header converter class org.apache.kafka.connect.storage.SimpleHeaderConverter for task pg-connector-0 using the worker config (org.apache.kafka.connect.runtime.Worker)
[2025-02-23 16:52:22,451] WARN The deleted record handling configs "drop.tombstones" and "delete.handling.mode" have been deprecated, please use "delete.tombstone.handling.mode" instead. (io.debezium.transforms.AbstractExtractNewRecordState)
[2025-02-23 16:52:22,452] INFO Initializing: org.apache.kafka.connect.runtime.TransformationChain{io.debezium.transforms.ExtractNewRecordState} (org.apache.kafka.connect.runtime.Worker)
[2025-02-23 16:52:22,452] INFO SourceConnectorConfig values: 
	config.action.reload = restart
	connector.class = io.debezium.connector.postgresql.PostgresConnector
	errors.log.enable = false
	errors.log.include.messages = false
	errors.retry.delay.max.ms = 60000
	errors.retry.timeout = 0
	errors.tolerance = none
	exactly.once.support = requested
	header.converter = null
	key.converter = null
	name = pg-connector
	offsets.storage.topic = null
	predicates = []
	tasks.max = 1
	tasks.max.enforce = true
	topic.creation.groups = []
	transaction.boundary = poll
	transaction.boundary.interval.ms = null
	transforms = [unwrap]
	value.converter = null
 (org.apache.kafka.connect.runtime.SourceConnectorConfig)
[2025-02-23 16:52:22,452] INFO EnrichedConnectorConfig values: 
	config.action.reload = restart
	connector.class = io.debezium.connector.postgresql.PostgresConnector
	errors.log.enable = false
	errors.log.include.messages = false
	errors.retry.delay.max.ms = 60000
	errors.retry.timeout = 0
	errors.tolerance = none
	exactly.once.support = requested
	header.converter = null
	key.converter = null
	name = pg-connector
	offsets.storage.topic = null
	predicates = []
	tasks.max = 1
	tasks.max.enforce = true
	topic.creation.groups = []
	transaction.boundary = poll
	transaction.boundary.interval.ms = null
	transforms = [unwrap]
	transforms.unwrap.add.fields = []
	transforms.unwrap.add.fields.prefix = __
	transforms.unwrap.add.headers = []
	transforms.unwrap.add.headers.prefix = __
	transforms.unwrap.delete.handling.mode = rewrite
	transforms.unwrap.drop.fields.from.key = false
	transforms.unwrap.drop.fields.header.name = null
	transforms.unwrap.drop.fields.keep.schema.compatible = true
	transforms.unwrap.drop.tombstones = false
	transforms.unwrap.negate = false
	transforms.unwrap.predicate = null
	transforms.unwrap.route.by.field = 
	transforms.unwrap.type = class io.debezium.transforms.ExtractNewRecordState
	value.converter = null
 (org.apache.kafka.connect.runtime.ConnectorConfig$EnrichedConnectorConfig)
[2025-02-23 16:52:22,452] INFO EnrichedSourceConnectorConfig values: 
	config.action.reload = restart
	connector.class = io.debezium.connector.postgresql.PostgresConnector
	errors.log.enable = false
	errors.log.include.messages = false
	errors.retry.delay.max.ms = 60000
	errors.retry.timeout = 0
	errors.tolerance = none
	exactly.once.support = requested
	header.converter = null
	key.converter = null
	name = pg-connector
	offsets.storage.topic = null
	predicates = []
	tasks.max = 1
	tasks.max.enforce = true
	topic.creation.default.exclude = []
	topic.creation.default.include = [.*]
	topic.creation.default.partitions = -1
	topic.creation.default.replication.factor = -1
	topic.creation.groups = []
	transaction.boundary = poll
	transaction.boundary.interval.ms = null
	transforms = [unwrap]
	value.converter = null
 (org.apache.kafka.connect.runtime.SourceConnectorConfig$EnrichedSourceConnectorConfig)
[2025-02-23 16:52:22,452] INFO EnrichedConnectorConfig values: 
	config.action.reload = restart
	connector.class = io.debezium.connector.postgresql.PostgresConnector
	errors.log.enable = false
	errors.log.include.messages = false
	errors.retry.delay.max.ms = 60000
	errors.retry.timeout = 0
	errors.tolerance = none
	exactly.once.support = requested
	header.converter = null
	key.converter = null
	name = pg-connector
	offsets.storage.topic = null
	predicates = []
	tasks.max = 1
	tasks.max.enforce = true
	topic.creation.default.exclude = []
	topic.creation.default.include = [.*]
	topic.creation.default.partitions = -1
	topic.creation.default.replication.factor = -1
	topic.creation.groups = []
	transaction.boundary = poll
	transaction.boundary.interval.ms = null
	transforms = [unwrap]
	transforms.unwrap.add.fields = []
	transforms.unwrap.add.fields.prefix = __
	transforms.unwrap.add.headers = []
	transforms.unwrap.add.headers.prefix = __
	transforms.unwrap.delete.handling.mode = rewrite
	transforms.unwrap.drop.fields.from.key = false
	transforms.unwrap.drop.fields.header.name = null
	transforms.unwrap.drop.fields.keep.schema.compatible = true
	transforms.unwrap.drop.tombstones = false
	transforms.unwrap.negate = false
	transforms.unwrap.predicate = null
	transforms.unwrap.route.by.field = 
	transforms.unwrap.type = class io.debezium.transforms.ExtractNewRecordState
	value.converter = null
 (org.apache.kafka.connect.runtime.ConnectorConfig$EnrichedConnectorConfig)
[2025-02-23 16:52:22,453] INFO ProducerConfig values: 
	acks = -1
	auto.include.jmx.reporter = true
	batch.size = 16384
	bootstrap.servers = [kafka-0:9092]
	buffer.memory = 33554432
	client.dns.lookup = use_all_dns_ips
	client.id = connector-producer-pg-connector-0
	compression.gzip.level = -1
	compression.lz4.level = 9
	compression.type = none
	compression.zstd.level = 3
	connections.max.idle.ms = 540000
	delivery.timeout.ms = 2147483647
	enable.idempotence = false
	enable.metrics.push = true
	interceptor.classes = []
	key.serializer = class org.apache.kafka.common.serialization.ByteArraySerializer
	linger.ms = 0
	max.block.ms = 9223372036854775807
	max.in.flight.requests.per.connection = 1
	max.request.size = 1048576
	metadata.max.age.ms = 300000
	metadata.max.idle.ms = 300000
	metadata.recovery.strategy = none
	metric.reporters = []
	metrics.num.samples = 2
	metrics.recording.level = INFO
	metrics.sample.window.ms = 30000
	partitioner.adaptive.partitioning.enable = true
	partitioner.availability.timeout.ms = 0
	partitioner.class = null
	partitioner.ignore.keys = false
	receive.buffer.bytes = 32768
	reconnect.backoff.max.ms = 1000
	reconnect.backoff.ms = 50
	request.timeout.ms = 30000
	retries = 2147483647
	retry.backoff.max.ms = 1000
	retry.backoff.ms = 100
	sasl.client.callback.handler.class = null
	sasl.jaas.config = null
	sasl.kerberos.kinit.cmd = /usr/bin/kinit
	sasl.kerberos.min.time.before.relogin = 60000
	sasl.kerberos.service.name = null
	sasl.kerberos.ticket.renew.jitter = 0.05
	sasl.kerberos.ticket.renew.window.factor = 0.8
	sasl.login.callback.handler.class = null
	sasl.login.class = null
	sasl.login.connect.timeout.ms = null
	sasl.login.read.timeout.ms = null
	sasl.login.refresh.buffer.seconds = 300
	sasl.login.refresh.min.period.seconds = 60
	sasl.login.refresh.window.factor = 0.8
	sasl.login.refresh.window.jitter = 0.05
	sasl.login.retry.backoff.max.ms = 10000
	sasl.login.retry.backoff.ms = 100
	sasl.mechanism = GSSAPI
	sasl.oauthbearer.clock.skew.seconds = 30
	sasl.oauthbearer.expected.audience = null
	sasl.oauthbearer.expected.issuer = null
	sasl.oauthbearer.header.urlencode = false
	sasl.oauthbearer.jwks.endpoint.refresh.ms = 3600000
	sasl.oauthbearer.jwks.endpoint.retry.backoff.max.ms = 10000
	sasl.oauthbearer.jwks.endpoint.retry.backoff.ms = 100
	sasl.oauthbearer.jwks.endpoint.url = null
	sasl.oauthbearer.scope.claim.name = scope
	sasl.oauthbearer.sub.claim.name = sub
	sasl.oauthbearer.token.endpoint.url = null
	security.protocol = PLAINTEXT
	security.providers = null
	send.buffer.bytes = 131072
	socket.connection.setup.timeout.max.ms = 30000
	socket.connection.setup.timeout.ms = 10000
	ssl.cipher.suites = null
	ssl.enabled.protocols = [TLSv1.2, TLSv1.3]
	ssl.endpoint.identification.algorithm = https
	ssl.engine.factory.class = null
	ssl.key.password = null
	ssl.keymanager.algorithm = SunX509
	ssl.keystore.certificate.chain = null
	ssl.keystore.key = null
	ssl.keystore.location = null
	ssl.keystore.password = null
	ssl.keystore.type = JKS
	ssl.protocol = TLSv1.3
	ssl.provider = null
	ssl.secure.random.implementation = null
	ssl.trustmanager.algorithm = PKIX
	ssl.truststore.certificates = null
	ssl.truststore.location = null
	ssl.truststore.password = null
	ssl.truststore.type = JKS
	transaction.timeout.ms = 60000
	transactional.id = null
	value.serializer = class org.apache.kafka.common.serialization.ByteArraySerializer
 (org.apache.kafka.clients.producer.ProducerConfig)
[2025-02-23 16:52:22,453] INFO initializing Kafka metrics collector (org.apache.kafka.common.telemetry.internals.KafkaMetricsCollector)
[2025-02-23 16:52:22,455] INFO These configurations '[metrics.context.connect.kafka.cluster.id, metrics.context.connect.group.id]' were supplied but are not used yet. (org.apache.kafka.clients.producer.ProducerConfig)
[2025-02-23 16:52:22,455] INFO Kafka version: 7.9.0-ccs (org.apache.kafka.common.utils.AppInfoParser)
[2025-02-23 16:52:22,455] INFO Kafka commitId: ebe6df624d6bc758c37aba7053cf868c1e532ccd (org.apache.kafka.common.utils.AppInfoParser)
[2025-02-23 16:52:22,455] INFO Kafka startTimeMs: 1740329542455 (org.apache.kafka.common.utils.AppInfoParser)
[2025-02-23 16:52:22,455] INFO AdminClientConfig values: 
	auto.include.jmx.reporter = true
	bootstrap.controllers = []
	bootstrap.servers = [kafka-0:9092]
	client.dns.lookup = use_all_dns_ips
	client.id = connector-adminclient-pg-connector-0
	connections.max.idle.ms = 300000
	default.api.timeout.ms = 60000
	enable.metrics.push = true
	metadata.max.age.ms = 300000
	metadata.recovery.strategy = none
	metric.reporters = []
	metrics.num.samples = 2
	metrics.recording.level = INFO
	metrics.sample.window.ms = 30000
	receive.buffer.bytes = 65536
	reconnect.backoff.max.ms = 1000
	reconnect.backoff.ms = 50
	request.timeout.ms = 30000
	retries = 2147483647
	retry.backoff.max.ms = 1000
	retry.backoff.ms = 100
	sasl.client.callback.handler.class = null
	sasl.jaas.config = null
	sasl.kerberos.kinit.cmd = /usr/bin/kinit
	sasl.kerberos.min.time.before.relogin = 60000
	sasl.kerberos.service.name = null
	sasl.kerberos.ticket.renew.jitter = 0.05
	sasl.kerberos.ticket.renew.window.factor = 0.8
	sasl.login.callback.handler.class = null
	sasl.login.class = null
	sasl.login.connect.timeout.ms = null
	sasl.login.read.timeout.ms = null
	sasl.login.refresh.buffer.seconds = 300
	sasl.login.refresh.min.period.seconds = 60
	sasl.login.refresh.window.factor = 0.8
	sasl.login.refresh.window.jitter = 0.05
	sasl.login.retry.backoff.max.ms = 10000
	sasl.login.retry.backoff.ms = 100
	sasl.mechanism = GSSAPI
	sasl.oauthbearer.clock.skew.seconds = 30
	sasl.oauthbearer.expected.audience = null
	sasl.oauthbearer.expected.issuer = null
	sasl.oauthbearer.header.urlencode = false
	sasl.oauthbearer.jwks.endpoint.refresh.ms = 3600000
	sasl.oauthbearer.jwks.endpoint.retry.backoff.max.ms = 10000
	sasl.oauthbearer.jwks.endpoint.retry.backoff.ms = 100
	sasl.oauthbearer.jwks.endpoint.url = null
	sasl.oauthbearer.scope.claim.name = scope
	sasl.oauthbearer.sub.claim.name = sub
	sasl.oauthbearer.token.endpoint.url = null
	security.protocol = PLAINTEXT
	security.providers = null
	send.buffer.bytes = 131072
	socket.connection.setup.timeout.max.ms = 30000
	socket.connection.setup.timeout.ms = 10000
	ssl.cipher.suites = null
	ssl.enabled.protocols = [TLSv1.2, TLSv1.3]
	ssl.endpoint.identification.algorithm = https
	ssl.engine.factory.class = null
	ssl.key.password = null
	ssl.keymanager.algorithm = SunX509
	ssl.keystore.certificate.chain = null
	ssl.keystore.key = null
	ssl.keystore.location = null
	ssl.keystore.password = null
	ssl.keystore.type = JKS
	ssl.protocol = TLSv1.3
	ssl.provider = null
	ssl.secure.random.implementation = null
	ssl.trustmanager.algorithm = PKIX
	ssl.truststore.certificates = null
	ssl.truststore.location = null
	ssl.truststore.password = null
	ssl.truststore.type = JKS
 (org.apache.kafka.clients.admin.AdminClientConfig)
[2025-02-23 16:52:22,456] INFO These configurations '[config.storage.topic, metrics.context.connect.group.id, rest.advertised.host.name, group.id, status.storage.topic, plugin.path, internal.key.converter.schemas.enable, rest.port, config.storage.replication.factor, internal.key.converter, value.converter.schema.registry.url, metrics.context.connect.kafka.cluster.id, status.storage.replication.factor, internal.value.converter.schemas.enable, internal.value.converter, offset.storage.replication.factor, offset.storage.topic, value.converter, key.converter, key.converter.schema.registry.url]' were supplied but are not used yet. (org.apache.kafka.clients.admin.AdminClientConfig)
[2025-02-23 16:52:22,456] INFO Kafka version: 7.9.0-ccs (org.apache.kafka.common.utils.AppInfoParser)
[2025-02-23 16:52:22,456] INFO Kafka commitId: ebe6df624d6bc758c37aba7053cf868c1e532ccd (org.apache.kafka.common.utils.AppInfoParser)
[2025-02-23 16:52:22,456] INFO Kafka startTimeMs: 1740329542456 (org.apache.kafka.common.utils.AppInfoParser)
[2025-02-23 16:52:22,458] INFO [Producer clientId=connector-producer-pg-connector-0] Cluster ID: practicum (org.apache.kafka.clients.Metadata)
[2025-02-23 16:52:22,463] INFO [Worker clientId=connect-localhost:8083, groupId=kafka-connect] Finished starting connectors and tasks (org.apache.kafka.connect.runtime.distributed.DistributedHerder)
[2025-02-23 16:52:22,464] INFO Starting PostgresConnectorTask with configuration:
   connector.class = io.debezium.connector.postgresql.PostgresConnector
   database.user = postgres-user
   database.dbname = customers
   transforms.unwrap.delete.handling.mode = rewrite
   topic.creation.default.partitions = -1
   transforms = unwrap
   database.server.name = customers
   database.port = 5432
   table.whitelist = public.customers
   topic.creation.enable = true
   topic.prefix = customers
   task.class = io.debezium.connector.postgresql.PostgresConnectorTask
   database.hostname = postgres
   database.password = ********
   transforms.unwrap.drop.tombstones = false
   topic.creation.default.replication.factor = -1
   name = pg-connector
   transforms.unwrap.type = io.debezium.transforms.ExtractNewRecordState
   skipped.operations = none
 (io.debezium.connector.common.BaseSourceTask)
[2025-02-23 16:52:22,465] INFO Loading the custom source info struct maker plugin: io.debezium.connector.postgresql.PostgresSourceInfoStructMaker (io.debezium.config.CommonConnectorConfig)
[2025-02-23 16:52:22,465] INFO Loading the custom topic naming strategy plugin: io.debezium.schema.SchemaTopicNamingStrategy (io.debezium.config.CommonConnectorConfig)
[2025-02-23 16:52:22,473] INFO Connection gracefully closed (io.debezium.jdbc.JdbcConnection)
[2025-02-23 16:52:22,495] WARN Type [oid:13529, name:_pg_user_mappings] is already mapped (io.debezium.connector.postgresql.TypeRegistry)
[2025-02-23 16:52:22,495] WARN Type [oid:13203, name:cardinal_number] is already mapped (io.debezium.connector.postgresql.TypeRegistry)
[2025-02-23 16:52:22,495] WARN Type [oid:13206, name:character_data] is already mapped (io.debezium.connector.postgresql.TypeRegistry)
[2025-02-23 16:52:22,495] WARN Type [oid:13208, name:sql_identifier] is already mapped (io.debezium.connector.postgresql.TypeRegistry)
[2025-02-23 16:52:22,495] WARN Type [oid:13214, name:time_stamp] is already mapped (io.debezium.connector.postgresql.TypeRegistry)
[2025-02-23 16:52:22,495] WARN Type [oid:13216, name:yes_or_no] is already mapped (io.debezium.connector.postgresql.TypeRegistry)
[2025-02-23 16:52:22,514] INFO No previous offsets found (io.debezium.connector.common.BaseSourceTask)
[2025-02-23 16:52:22,530] WARN Type [oid:13529, name:_pg_user_mappings] is already mapped (io.debezium.connector.postgresql.TypeRegistry)
[2025-02-23 16:52:22,530] WARN Type [oid:13203, name:cardinal_number] is already mapped (io.debezium.connector.postgresql.TypeRegistry)
[2025-02-23 16:52:22,530] WARN Type [oid:13206, name:character_data] is already mapped (io.debezium.connector.postgresql.TypeRegistry)
[2025-02-23 16:52:22,530] WARN Type [oid:13208, name:sql_identifier] is already mapped (io.debezium.connector.postgresql.TypeRegistry)
[2025-02-23 16:52:22,530] WARN Type [oid:13214, name:time_stamp] is already mapped (io.debezium.connector.postgresql.TypeRegistry)
[2025-02-23 16:52:22,530] WARN Type [oid:13216, name:yes_or_no] is already mapped (io.debezium.connector.postgresql.TypeRegistry)
[2025-02-23 16:52:22,539] INFO Connector started for the first time. (io.debezium.connector.common.BaseSourceTask)
[2025-02-23 16:52:22,539] INFO No previous offset found (io.debezium.connector.postgresql.PostgresConnectorTask)
[2025-02-23 16:52:22,543] INFO user 'postgres-user' connected to database 'customers' on PostgreSQL 16.4 (Debian 16.4-1.pgdg110+2) on x86_64-pc-linux-gnu, compiled by gcc (Debian 10.2.1-6) 10.2.1 20210110, 64-bit with roles:
	role 'pg_read_all_settings' [superuser: false, replication: false, inherit: true, create role: false, create db: false, can log in: false]
	role 'pg_database_owner' [superuser: false, replication: false, inherit: true, create role: false, create db: false, can log in: false]
	role 'pg_stat_scan_tables' [superuser: false, replication: false, inherit: true, create role: false, create db: false, can log in: false]
	role 'pg_checkpoint' [superuser: false, replication: false, inherit: true, create role: false, create db: false, can log in: false]
	role 'pg_write_server_files' [superuser: false, replication: false, inherit: true, create role: false, create db: false, can log in: false]
	role 'pg_use_reserved_connections' [superuser: false, replication: false, inherit: true, create role: false, create db: false, can log in: false]
	role 'postgres-user' [superuser: true, replication: true, inherit: true, create role: true, create db: true, can log in: true]
	role 'pg_read_all_data' [superuser: false, replication: false, inherit: true, create role: false, create db: false, can log in: false]
	role 'pg_write_all_data' [superuser: false, replication: false, inherit: true, create role: false, create db: false, can log in: false]
	role 'pg_monitor' [superuser: false, replication: false, inherit: true, create role: false, create db: false, can log in: false]
	role 'pg_read_server_files' [superuser: false, replication: false, inherit: true, create role: false, create db: false, can log in: false]
	role 'pg_create_subscription' [superuser: false, replication: false, inherit: true, create role: false, create db: false, can log in: false]
	role 'pg_execute_server_program' [superuser: false, replication: false, inherit: true, create role: false, create db: false, can log in: false]
	role 'pg_read_all_stats' [superuser: false, replication: false, inherit: true, create role: false, create db: false, can log in: false]
	role 'pg_signal_backend' [superuser: false, replication: false, inherit: true, create role: false, create db: false, can log in: false] (io.debezium.connector.postgresql.PostgresConnectorTask)
[2025-02-23 16:52:22,547] INFO Obtained valid replication slot ReplicationSlot [active=false, latestFlushedLsn=null, catalogXmin=null] (io.debezium.connector.postgresql.connection.PostgresConnection)
[2025-02-23 16:52:22,558] INFO Creating replication slot with command CREATE_REPLICATION_SLOT "debezium"  LOGICAL decoderbufs  (io.debezium.connector.postgresql.connection.PostgresReplicationConnection)
[2025-02-23 16:52:22,607] INFO Requested thread factory for component PostgresConnector, id = customers named = SignalProcessor (io.debezium.util.Threads)
[2025-02-23 16:52:22,615] INFO Requested thread factory for component PostgresConnector, id = customers named = change-event-source-coordinator (io.debezium.util.Threads)
[2025-02-23 16:52:22,615] INFO Requested thread factory for component PostgresConnector, id = customers named = blocking-snapshot (io.debezium.util.Threads)
[2025-02-23 16:52:22,618] INFO Creating thread debezium-postgresconnector-customers-change-event-source-coordinator (io.debezium.util.Threads)
[2025-02-23 16:52:22,618] INFO WorkerSourceTask{id=pg-connector-0} Source task finished initialization and start (org.apache.kafka.connect.runtime.AbstractWorkerSourceTask)
[2025-02-23 16:52:22,621] INFO Metrics registered (io.debezium.pipeline.ChangeEventSourceCoordinator)
[2025-02-23 16:52:22,621] INFO Context created (io.debezium.pipeline.ChangeEventSourceCoordinator)
[2025-02-23 16:52:22,624] INFO According to the connector configuration data will be snapshotted (io.debezium.connector.postgresql.PostgresSnapshotChangeEventSource)
[2025-02-23 16:52:22,625] INFO Snapshot step 1 - Preparing (io.debezium.relational.RelationalSnapshotChangeEventSource)
[2025-02-23 16:52:22,626] INFO Setting isolation level (io.debezium.connector.postgresql.PostgresSnapshotChangeEventSource)
[2025-02-23 16:52:22,626] INFO Opening transaction with statement SET TRANSACTION ISOLATION LEVEL REPEATABLE READ; 
SET TRANSACTION SNAPSHOT '00000006-00000002-1'; (io.debezium.connector.postgresql.PostgresSnapshotChangeEventSource)
[2025-02-23 16:52:22,666] INFO Snapshot step 2 - Determining captured tables (io.debezium.relational.RelationalSnapshotChangeEventSource)
[2025-02-23 16:52:22,668] INFO Adding table public.users to the list of capture schema tables (io.debezium.relational.RelationalSnapshotChangeEventSource)
[2025-02-23 16:52:22,669] INFO Created connection pool with 1 threads (io.debezium.relational.RelationalSnapshotChangeEventSource)
[2025-02-23 16:52:22,669] INFO Snapshot step 3 - Locking captured tables [public.users] (io.debezium.relational.RelationalSnapshotChangeEventSource)
[2025-02-23 16:52:22,671] INFO Snapshot step 4 - Determining snapshot offset (io.debezium.relational.RelationalSnapshotChangeEventSource)
[2025-02-23 16:52:22,671] INFO Creating initial offset context (io.debezium.connector.postgresql.PostgresOffsetContext)
[2025-02-23 16:52:22,672] INFO Read xlogStart at 'LSN{0/1A3D910}' from transaction '745' (io.debezium.connector.postgresql.PostgresOffsetContext)
[2025-02-23 16:52:22,674] INFO Read xlogStart at 'LSN{0/1A3D910}' from transaction '745' (io.debezium.connector.postgresql.PostgresSnapshotChangeEventSource)
[2025-02-23 16:52:22,674] INFO Snapshot step 5 - Reading structure of captured tables (io.debezium.relational.RelationalSnapshotChangeEventSource)
[2025-02-23 16:52:22,674] INFO Reading structure of schema 'public' of catalog 'customers' (io.debezium.connector.postgresql.PostgresSnapshotChangeEventSource)
[2025-02-23 16:52:22,696] INFO Snapshot step 6 - Persisting schema history (io.debezium.relational.RelationalSnapshotChangeEventSource)
[2025-02-23 16:52:22,696] INFO Snapshot step 7 - Snapshotting data (io.debezium.relational.RelationalSnapshotChangeEventSource)
[2025-02-23 16:52:22,696] INFO Creating snapshot worker pool with 1 worker thread(s) (io.debezium.relational.RelationalSnapshotChangeEventSource)
[2025-02-23 16:52:22,698] INFO For table 'public.users' using select statement: 'SELECT "id", "name", "private_info" FROM "public"."users"' (io.debezium.relational.RelationalSnapshotChangeEventSource)
[2025-02-23 16:52:22,700] INFO Exporting data from table 'public.users' (1 of 1 tables) (io.debezium.relational.RelationalSnapshotChangeEventSource)
[2025-02-23 16:52:22,703] INFO 	 Finished exporting 0 records for table 'public.users' (1 of 1 tables); total duration '00:00:00.003' (io.debezium.relational.RelationalSnapshotChangeEventSource)
[2025-02-23 16:52:22,704] INFO Snapshot - Final stage (io.debezium.pipeline.source.AbstractSnapshotChangeEventSource)
[2025-02-23 16:52:22,704] INFO Snapshot completed (io.debezium.pipeline.source.AbstractSnapshotChangeEventSource)
[2025-02-23 16:52:22,710] INFO Snapshot ended with SnapshotResult [status=COMPLETED, offset=PostgresOffsetContext [sourceInfoSchema=Schema{io.debezium.connector.postgresql.Source:STRUCT}, sourceInfo=source_info[server='customers'db='customers', lsn=LSN{0/1A3D910}, txId=745, timestamp=2025-02-23T16:52:22.674Z, snapshot=FALSE, schema=, table=], lastSnapshotRecord=true, lastCompletelyProcessedLsn=null, lastCommitLsn=null, streamingStoppingLsn=null, transactionContext=TransactionContext [currentTransactionId=null, perTableEventCount={}, totalEventCount=0], incrementalSnapshotContext=IncrementalSnapshotContext [windowOpened=false, chunkEndPosition=null, dataCollectionsToSnapshot=[], lastEventKeySent=null, maximumKey=null]]] (io.debezium.pipeline.ChangeEventSourceCoordinator)
[2025-02-23 16:52:22,712] INFO Connected metrics set to 'true' (io.debezium.pipeline.ChangeEventSourceCoordinator)
[2025-02-23 16:52:22,730] INFO REPLICA IDENTITY for 'public.users' is 'DEFAULT'; UPDATE and DELETE events will contain previous values only for PK columns (io.debezium.connector.postgresql.PostgresSchema)
[2025-02-23 16:52:22,734] INFO SignalProcessor started. Scheduling it every 5000ms (io.debezium.pipeline.signal.SignalProcessor)
[2025-02-23 16:52:22,734] INFO Creating thread debezium-postgresconnector-customers-SignalProcessor (io.debezium.util.Threads)
[2025-02-23 16:52:22,734] INFO Starting streaming (io.debezium.pipeline.ChangeEventSourceCoordinator)
[2025-02-23 16:52:22,734] INFO Retrieved latest position from stored offset 'LSN{0/1A3D910}' (io.debezium.connector.postgresql.PostgresStreamingChangeEventSource)
[2025-02-23 16:52:22,735] INFO Looking for WAL restart position for last commit LSN 'null' and last change LSN 'LSN{0/1A3D910}' (io.debezium.connector.postgresql.connection.WalPositionLocator)
[2025-02-23 16:52:22,743] INFO Obtained valid replication slot ReplicationSlot [active=false, latestFlushedLsn=LSN{0/1A3D910}, catalogXmin=745] (io.debezium.connector.postgresql.connection.PostgresConnection)
[2025-02-23 16:52:22,743] INFO Connection gracefully closed (io.debezium.jdbc.JdbcConnection)
[2025-02-23 16:52:22,757] INFO Requested thread factory for component PostgresConnector, id = customers named = keep-alive (io.debezium.util.Threads)
[2025-02-23 16:52:22,757] INFO Creating thread debezium-postgresconnector-customers-keep-alive (io.debezium.util.Threads)
[2025-02-23 16:52:22,774] INFO REPLICA IDENTITY for 'public.users' is 'DEFAULT'; UPDATE and DELETE events will contain previous values only for PK columns (io.debezium.connector.postgresql.PostgresSchema)
[2025-02-23 16:52:22,775] INFO Processing messages (io.debezium.connector.postgresql.PostgresStreamingChangeEventSource)
```

Лог при добавлении записи в таблицу

```
[2025-02-23 16:53:22,463] INFO WorkerSourceTask{id=pg-connector-0} Committing offsets for 1 acknowledged messages (org.apache.kafka.connect.runtime.WorkerSourceTask)
[2025-02-23 16:53:22,479] INFO Found previous partition offset PostgresPartition [sourcePartition={server=customers}]: {lsn_proc=27538008, messageType=INSERT, lsn_commit=27538008, lsn=27538008, txId=746, ts_usec=1740329598961991} (io.debezium.connector.common.BaseSourceTask)
```