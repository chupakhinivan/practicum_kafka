package com.chupakhin.productfilter.processor;

import com.chupakhin.dto.Product;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.ValueAndTimestamp;

import java.util.List;

@Slf4j
public class FilterAdultProductProcessor implements Processor<String, Product, String, Product> {

    private final String adultProductsStoreName;

    private ProcessorContext<String, Product> context;
    private KeyValueStore<String, ValueAndTimestamp<List<String>>> adultProductsStore;

    public FilterAdultProductProcessor(String adultProductsStoreName) {
        this.adultProductsStoreName = adultProductsStoreName;
    }

    @Override
    public void init(ProcessorContext<String, Product> context) {
        this.context = context;
        this.adultProductsStore = context.getStateStore(adultProductsStoreName);
    }

    @Override
    public void process(Record<String, Product> productRecord) {
        Product product = productRecord.value();
        ValueAndTimestamp<List<String>> listValueAndTimestamp = adultProductsStore.get(product.getName());
        if (listValueAndTimestamp == null) {
            context.forward(productRecord);
        } else {
            // Информируем о блокировки сообщения, не прошедшего фильтрации по отправителю
            log.info("Товар '{}' заблокирован!", product.getName());
        }
    }

    @Override
    public void close() {
        Processor.super.close();
    }
}
