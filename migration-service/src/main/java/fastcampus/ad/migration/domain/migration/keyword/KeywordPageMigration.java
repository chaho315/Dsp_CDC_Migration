package fastcampus.ad.migration.domain.migration.keyword;

import fastcampus.ad.migration.domain.AggregateType;
import fastcampus.ad.migration.domain.migration.PageMigration;
import fastcampus.ad.migration.domain.migration.adgroup.AdGroupPageMigration;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class KeywordPageMigration extends PageMigration<KeywordPageMigration> {
    public KeywordPageMigration(Long id, Integer pageNumber, Integer pageSize, Long totalCount) {
        super(id, pageNumber, pageSize, totalCount);
    }

    public static KeywordPageMigration first(boolean isSuccess, Long userId, Integer pageSize, Long totcalCount) {
        if(isSuccess){
            return new KeywordPageMigration(userId, PageMigration.INIT_PAGE_NUMBER, pageSize, totcalCount);
        }else{
            return new KeywordPageMigration(userId, PageMigration.NOT_STARTED_PAGE_NUMBER, pageSize, totcalCount);
        }
    }

    @Override
    protected AggregateType aggregateType() {
        return AggregateType.KEYWORD;
    }
}
