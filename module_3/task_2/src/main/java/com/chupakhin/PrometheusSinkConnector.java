package com.chupakhin;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.sink.SinkConnector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.chupakhin.Constants.*;


public class PrometheusSinkConnector extends SinkConnector {

    private String prometheusUrl;
    private String prometheusPort;

    @Override
    public String version() {
        return "1.0.0";
    }

    @Override
    public void start(Map<String, String> props) {
        prometheusUrl = props.get(PROMETHEUS_URL_CONFIG);
        String port = props.get(PROMETHEUS_PORT_CONFIG);
        prometheusPort = port != null ? port : PROMETHEUS_PORT_DEFAULT;
    }

    @Override
    public Class<? extends Task> taskClass() {
        return PrometheusSinkTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        List<Map<String, String>> configs = new ArrayList<>();
        for (int i = 0; i < maxTasks; i++) {
            configs.add(Map.of(
                    PROMETHEUS_URL_CONFIG, prometheusUrl,
                    PROMETHEUS_PORT_CONFIG, prometheusPort
            ));
        }
        return configs;
    }

    @Override
    public void stop() {
        // Завершение работы, если требуется
    }

    @Override
    public ConfigDef config() {
        return new ConfigDef()
                .define(PROMETHEUS_URL_CONFIG, ConfigDef.Type.STRING, ConfigDef.Importance.HIGH, "Prometheus listener URL")
                .define(PROMETHEUS_PORT_CONFIG, ConfigDef.Type.INT, 8080, ConfigDef.Importance.HIGH, "Prometheus listener port");
    }

}
