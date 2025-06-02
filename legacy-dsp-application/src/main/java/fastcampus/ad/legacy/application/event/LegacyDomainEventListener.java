package fastcampus.ad.legacy.application.event;

import fastcampus.ad.legacy.domain.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class LegacyDomainEventListener {

    private final StreamBridge streamBridge;
    private static final String OUTPUT_BINDING = "legacy-rabbit-out";

    @TransactionalEventListener//DB에 데이터가 들어간 이후 발행을 위해 commit된 이후로 발행되는 transactional리스너 사용
    public void handleEvent(DomainEvent event) {
        streamBridge.send(OUTPUT_BINDING, LegacyDomainMessage.from(event));
    }
}
