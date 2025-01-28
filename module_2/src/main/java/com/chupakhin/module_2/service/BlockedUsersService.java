package com.chupakhin.module_2.service;

import com.chupakhin.module_2.topology.MessageTopology;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BlockedUsersService {

    @Value("${kafka.stores.blocked-users}")
    private String blockedUsersStoreName;

    private final MessageTopology messageTopology;


    public BlockedUsersService(MessageTopology messageTopology) {
        this.messageTopology = messageTopology;
    }

    public Map<String, List<String>> getAllBlockedUsers() {
        Map<String, List<String>> blockedUsers = new HashMap<>();
        ReadOnlyKeyValueStore<String, List<String>> blockedUsersStore = messageTopology.getStreams().store(
                StoreQueryParameters.fromNameAndType(blockedUsersStoreName, QueryableStoreTypes.keyValueStore())
        );
        try (KeyValueIterator<String, List<String>> iterator = blockedUsersStore.all()) {
            while (iterator.hasNext()) {
                KeyValue<String, List<String>> keyValue = iterator.next();
                blockedUsers.put(keyValue.key, keyValue.value);
            }
        }
        return blockedUsers;
    }

    public List<String> getBlockedUsersByRecipientId(String recipientId) {
        ReadOnlyKeyValueStore<String, List<String>> blockedUsersStore = messageTopology.getStreams().store(
                StoreQueryParameters.fromNameAndType(blockedUsersStoreName, QueryableStoreTypes.keyValueStore())
        );
        return blockedUsersStore.get(recipientId);
    }
}
