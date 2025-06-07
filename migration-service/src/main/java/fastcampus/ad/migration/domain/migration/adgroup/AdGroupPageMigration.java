package fastcampus.ad.migration.domain.migration.adgroup;

import fastcampus.ad.migration.domain.AggregateType;
import fastcampus.ad.migration.domain.migration.PageMigration;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class AdGroupPageMigration extends PageMigration<AdGroupPageMigration> {
    public AdGroupPageMigration(Long id, Integer pageNumber, Integer pageSize, Long totalCount) {
        super(id, pageNumber, pageSize, totalCount);
    }

    @Override
    protected AggregateType aggregateType() {
        return AggregateType.ADGROUP;
    }
}
