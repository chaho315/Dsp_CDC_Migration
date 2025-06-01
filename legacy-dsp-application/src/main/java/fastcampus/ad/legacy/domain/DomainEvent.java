package fastcampus.ad.legacy.domain;

import java.time.LocalDateTime;

public interface DomainEvent {
    AggregateType aggregateType(); //DDD에서 많이 나오는 용어로 하나의 트렌젝션이 처리될때 사용되는 용어
    Long aggregateId();
    LocalDateTime occurredOn();//이벤트가 언제 발생한지 알려주는 
    Long ownerId(); // 도메인이 누구에게 속했는가
}
