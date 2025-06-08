package fastcampus.ad.migration.application.legacyad.keyword;

import fastcampus.ad.migration.application.LegacyMigrationService;
import fastcampus.ad.migration.application.PageLegacyMigrationService;
import fastcampus.ad.migration.domain.legacyad.keyword.LegacyKeyword;
import fastcampus.ad.migration.domain.legacyad.keyword.LegacyKeywordRepository;
import fastcampus.ad.migration.domain.migration.PageMigrationRepository;
import fastcampus.ad.migration.domain.migration.keyword.KeywordPageMigration;
import fastcampus.ad.migration.domain.migration.keyword.KeywordPageMigrationRepository;
import fastcampus.ad.migration.domain.recentad.keyword.RecentKeyword;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class PageLegacyKeywordMigrationService extends PageLegacyMigrationService<KeywordPageMigration, LegacyKeyword, RecentKeyword> {
    public PageLegacyKeywordMigrationService(KeywordPageMigrationRepository pageMigrationRepository, LegacyKeywordRepository legacyKeywordRepository, LegacyKeywordMigrationService legacyKeywordMigrationService) {
        super(pageMigrationRepository, legacyKeywordRepository, legacyKeywordMigrationService);
    }

    @Override
    protected KeywordPageMigration firstPageMigration(Long userId, boolean isSuccess, Page<LegacyKeyword> legacyKeywords) {
        return KeywordPageMigration.first(isSuccess, userId, PAGE_SIZE, legacyKeywords.getTotalElements());
    }
}
