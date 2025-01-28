package com.chupakhin.module_2.processor;

import com.chupakhin.module_2.dto.UserMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.ValueAndTimestamp;

import java.util.List;

@Slf4j
public class FilterBlockedUserProcessor implements Processor<String, UserMessage, String, UserMessage> {

    private final String blockedUsersStoreName;

    private ProcessorContext<String, UserMessage> context;
    private KeyValueStore<String, ValueAndTimestamp<List<String>>> blockedUsersStore;

    public FilterBlockedUserProcessor(String blockedUsersStoreName) {
        this.blockedUsersStoreName = blockedUsersStoreName;
    }

    @Override
    public void init(ProcessorContext<String, UserMessage> context) {
        this.context = context;
        this.blockedUsersStore = context.getStateStore(blockedUsersStoreName);
    }

    @Override
    public void process(Record<String, UserMessage> userMessageRecord) {
        UserMessage userMessage = userMessageRecord.value();
        ValueAndTimestamp<List<String>> listValueAndTimestamp = blockedUsersStore.get(userMessage.getRecipientUser());
        if (listValueAndTimestamp == null || !listValueAndTimestamp.value().contains(userMessage.getSenderUser())) {
            context.forward(userMessageRecord);
        } else {
            // Информируем о блокировки сообщения, не прошедшего фильтрации по отправителю
            log.info("Сообщение от '{}' для '{}' заблокировано!", userMessage.getSenderUser(), userMessage.getRecipientUser());
        }
    }

    @Override
    public void close() {
        Processor.super.close();
    }
}
