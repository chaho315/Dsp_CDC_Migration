package fastcampus.ad.migration.domain.migration.keyword;

import fastcampus.ad.migration.domain.migration.PageMigrationRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeywordMigrationRepository extends PageMigrationRepository<KeywordPageMigration> {
}
