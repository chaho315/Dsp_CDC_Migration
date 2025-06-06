package fastcampus.ad.migration.internal.application.event;

import fastcampus.ad.migration.domain.migration.user.MigrationAgreedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class MigrationUserEventListener {

    private static final String OUTPUT_BINDING = "migration-user-out";
    private final StreamBridge streamBridge;

    @TransactionalEventListener
    public void handleAgreedEvent(MigrationAgreedEvent event){
        streamBridge.send(OUTPUT_BINDING, MessageBuilder.createMessage(MigrationUserMessage.from(event), new MessageHeaders(Map.of("partitionKey", event.getUserId()))));
    }
}
