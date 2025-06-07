package fastcampus.ad.migration.domain.migration.keyword;

import fastcampus.ad.migration.domain.AggregateType;
import fastcampus.ad.migration.domain.migration.PageMigration;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class KeywordPageMigration extends PageMigration<KeywordPageMigration> {
    public KeywordPageMigration(Long id, Integer pageNumber, Integer pageSize, Long totalCount) {
        super(id, pageNumber, pageSize, totalCount);
    }

    @Override
    protected AggregateType aggregateType() {
        return AggregateType.KEYWORD;
    }
}
