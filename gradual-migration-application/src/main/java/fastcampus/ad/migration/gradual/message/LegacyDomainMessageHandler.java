package fastcampus.ad.migration.gradual.message;

import fastcampus.ad.migration.application.dispatcher.MigrationDispatcher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
public class LegacyDomainMessageHandler {

    private final MigrationDispatcher migrationDispatcher;
    @Bean
    public Consumer<LegacyDomainMessage> legacyConsumer(){
        return message -> migrationDispatcher.dispatch(message.aggregateId(), message.aggregateType());
    }
}
