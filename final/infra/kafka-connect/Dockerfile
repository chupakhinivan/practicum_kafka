FROM confluentinc/cp-kafka-connect:7.7.1
# Скопировать JMX Exporter в докер образ
COPY jmx_prometheus_javaagent-0.15.0.jar /opt/
# Скопировать настройки JMX Exporter в докер образ
COPY kafka-connect.yml /opt/
RUN #chmod 644 /opt/jmx_prometheus_javaagent-0.15.0.jar
RUN #ls -l /opt/
