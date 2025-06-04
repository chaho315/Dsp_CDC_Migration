package fastcampus.ad.migration.application.legacyad.keyword;

import fastcampus.ad.migration.application.LegacyMigrationService;
import fastcampus.ad.migration.application.legacyad.keyword.LegacyKeywordConverter;
import fastcampus.ad.migration.domain.legacyad.keyword.LegacyKeyword;
import fastcampus.ad.migration.domain.legacyad.keyword.LegacyKeywordRepository;
import fastcampus.ad.migration.domain.recentad.keyword.RecentKeyword;
import fastcampus.ad.migration.domain.recentad.keyword.RecentKeywordRepository;
import org.springframework.stereotype.Service;

@Service
public class LegacyKeywordMigrationService extends LegacyMigrationService<LegacyKeyword, RecentKeyword> {

    public LegacyKeywordMigrationService(LegacyKeywordRepository legacyRepository,
                                         LegacyKeywordConverter converter,
                                         RecentKeywordRepository recentRepository) {
        super(legacyRepository, converter, recentRepository);
    }
}
