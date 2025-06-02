package fastcampus.ad.migration.gradual.application.legacyad.keyword;

import fastcampus.ad.migration.gradual.application.LegacyMigrationService;
import fastcampus.ad.migration.gradual.application.legacyad.user.LegacyUserConverter;
import fastcampus.ad.migration.gradual.domain.legacyad.keyword.LegacyKeyword;
import fastcampus.ad.migration.gradual.domain.legacyad.keyword.LegacyKeywordRepository;
import fastcampus.ad.migration.gradual.domain.legacyad.user.LegacyUser;
import fastcampus.ad.migration.gradual.domain.legacyad.user.LegacyUserRepository;
import fastcampus.ad.migration.gradual.domain.recentad.keyword.RecentKeyword;
import fastcampus.ad.migration.gradual.domain.recentad.keyword.RecentKeywordRepository;
import fastcampus.ad.migration.gradual.domain.recentad.user.RecentUser;
import fastcampus.ad.migration.gradual.domain.recentad.user.RecentUserRepository;
import org.springframework.stereotype.Service;

@Service
public class LegacyKeywordMigrationService extends LegacyMigrationService<LegacyKeyword, RecentKeyword> {

    public LegacyKeywordMigrationService(LegacyKeywordRepository legacyRepository,
                                         LegacyKeywordConverter converter,
                                         RecentKeywordRepository recentRepository) {
        super(legacyRepository, converter, recentRepository);
    }
}
