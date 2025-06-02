package fastcampus.ad.migration.gradual.application.legacyad.keyword;

import fastcampus.ad.migration.gradual.application.LegacyConverter;
import fastcampus.ad.migration.gradual.domain.legacyad.keyword.LegacyKeyword;
import fastcampus.ad.migration.gradual.domain.legacyad.user.LegacyUser;
import fastcampus.ad.migration.gradual.domain.recentad.keyword.RecentKeyword;
import fastcampus.ad.migration.gradual.domain.recentad.user.RecentUser;
import org.springframework.stereotype.Component;

@Component
public class LegacyKeywordConverter implements LegacyConverter<LegacyKeyword, RecentKeyword> {

    @Override
    public RecentKeyword convert(LegacyKeyword legacyKeyword) {
        return RecentKeyword.migrated(legacyKeyword.getId(),
                legacyKeyword.getText(),
                legacyKeyword.getAdGroupId(),
                legacyKeyword.getUserId(),
                legacyKeyword.getCreatedAt(),
                legacyKeyword.getDeletedAt());

    }
}
