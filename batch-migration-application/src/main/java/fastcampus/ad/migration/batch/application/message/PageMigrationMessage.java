package fastcampus.ad.migration.batch.application.message;

import fastcampus.ad.migration.domain.AggregateType;
import fastcampus.ad.migration.domain.migration.PageMigrationEvent;

public record PageMigrationMessage(Long userId, AggregateType aggregateType, boolean isFinished) {
    public static PageMigrationMessage from(PageMigrationEvent event) {
        return new PageMigrationMessage(event.userId(), event.aggregateType(), event.isFinished());
    }
}
