package com.chupakhin;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

public class PrometheusHttpServer {

    private static PrometheusHttpServer instance;
    private String baseUrl;


    private PrometheusHttpServer() {}


    private final ConcurrentHashMap<String, String> metrics = new ConcurrentHashMap<>();


    public static PrometheusHttpServer getInstance(String url, int port) throws Exception {
        if (instance == null) {
            instance = new PrometheusHttpServer();
            instance.baseUrl = url;
            instance.start(port);
        }
        return instance;
    }


    public void start(int port) throws Exception {
        Server server = new Server(port);
        ServletContextHandler handler = new ServletContextHandler();
        handler.addServlet(new ServletHolder(new MetricsServlet(metrics, baseUrl)), "/metrics");
        server.setHandler(handler);
        server.start();
        server.join();
    }


    public void addMetric(String name, String data) {
        metrics.put(name, data);
    }


    private static class MetricsServlet extends HttpServlet {
        private final ConcurrentHashMap<String, String> metrics;
        private final String baseUrl;


        public MetricsServlet(ConcurrentHashMap<String, String> metrics, String baseUrl) {
            this.metrics = metrics;
            this.baseUrl = baseUrl;
        }


        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setContentType("text/plain");

            StringBuilder response = new StringBuilder("# Base URL: " + baseUrl + "\n");
            metrics.values().forEach(response::append);

            resp.getWriter().write(response.toString());
        }
    }
}
