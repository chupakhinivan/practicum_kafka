package com.chupakhin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;

import java.util.Collection;
import java.util.Map;

import static com.chupakhin.Constants.PROMETHEUS_PORT_CONFIG;
import static com.chupakhin.Constants.PROMETHEUS_URL_CONFIG;

public class PrometheusSinkTask extends SinkTask {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private PrometheusHttpServer httpServer;

    @Override
    public String version() {
        return "1.0.0";
    }

    @Override
    public void start(Map<String, String> props) {
        String url = props.get(PROMETHEUS_URL_CONFIG);
        int port = Integer.parseInt(props.get(PROMETHEUS_PORT_CONFIG));
        try {
            httpServer = PrometheusHttpServer.getInstance(url, port);
        } catch (Exception e) {
            throw new RuntimeException("Failed to start HTTP server", e);
        }
    }

    @Override
    public void put(Collection<SinkRecord> records) {
        for (SinkRecord record : records) {
            try {
                JsonNode root = MAPPER.readTree(record.value().toString());
                root.fields().forEachRemaining(entry -> {
                    JsonNode metric = entry.getValue();
                    String name = metric.get("Name").asText();
                    String type = metric.get("Type").asText();
                    String description = metric.get("Description").asText();
                    double value = metric.get("Value").asDouble();

                    String prometheusData = String.format(
                            "# HELP %s %s\n# TYPE %s %s\n%s %f\n",
                            name, description, name, type, name, value
                    );
                    httpServer.addMetric(name, prometheusData);
                });
            } catch (Exception e) {
                System.err.println("Error processing record: " + e.getMessage());

            }

        }
    }


    @Override
    public void stop() { }
}
