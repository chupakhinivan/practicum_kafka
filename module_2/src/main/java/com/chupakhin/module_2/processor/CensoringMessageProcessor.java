package com.chupakhin.module_2.processor;

import com.chupakhin.module_2.dto.UserCensoredMessage;
import com.chupakhin.module_2.dto.UserMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.processor.api.Processor;
import org.apache.kafka.streams.processor.api.ProcessorContext;
import org.apache.kafka.streams.processor.api.Record;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.state.ValueAndTimestamp;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class CensoringMessageProcessor implements Processor<String, UserMessage, String, UserCensoredMessage> {

    private final String adultWordsStoreName;

    private ProcessorContext<String, UserCensoredMessage> context;

    public CensoringMessageProcessor(String adultWordsStoreName) {
        this.adultWordsStoreName = adultWordsStoreName;
    }

    @Override
    public void init(ProcessorContext<String, UserCensoredMessage> context) {
        this.context = context;
    }

    @Override
    public void process(Record<String, UserMessage> userMessageRecord) {
        Set<String> adultWords = getAdultWordsSet();
        String key = userMessageRecord.key();
        UserMessage userMessage = userMessageRecord.value();
        boolean messageHasAdultWords = false;
        String censoredMessage;
        Set<String> messageWords = new HashSet<>(List.of(userMessage.getMessage().toLowerCase().split("\\P{L}+")));
        Set<String> containsAdultWords = new HashSet<>(adultWords);
        containsAdultWords.retainAll(messageWords);
        if(!containsAdultWords.isEmpty()){
            messageHasAdultWords = true;
            String censoredRegex = "(?ui)\\b(" + String.join("|", containsAdultWords) + ")\\b";
            censoredMessage = userMessage.getMessage().replaceAll(censoredRegex, "***");
        } else {
            censoredMessage = userMessage.getMessage();
        }

        UserCensoredMessage userCensoredMessage = new UserCensoredMessage(
                userMessage.getSenderUser(),
                userMessage.getRecipientUser(),
                censoredMessage,
                messageHasAdultWords);

        Record<String, UserCensoredMessage> censoredMessageRecord =
                new Record<>(key, userCensoredMessage, System.currentTimeMillis());

        context.forward(censoredMessageRecord);
    }

    @Override
    public void close() {
        Processor.super.close();
    }

    private Set<String> getAdultWordsSet() {
        Set<String> adultWords = new HashSet<>();
        ReadOnlyKeyValueStore<String, ValueAndTimestamp<String>> adultWordsStore = context.getStateStore(adultWordsStoreName);
        try (KeyValueIterator<String, ValueAndTimestamp<String>> iterator = adultWordsStore.all()) {
            while (iterator.hasNext()) {
                adultWords.add(iterator.next().key);
            }
        }
        return adultWords;
    }
}
