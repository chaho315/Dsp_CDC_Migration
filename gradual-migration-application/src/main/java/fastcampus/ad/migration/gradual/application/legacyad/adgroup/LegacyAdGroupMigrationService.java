package fastcampus.ad.migration.gradual.application.legacyad.adgroup;

import fastcampus.ad.migration.gradual.application.LegacyConverter;
import fastcampus.ad.migration.gradual.application.LegacyMigrationService;
import fastcampus.ad.migration.gradual.application.legacyad.user.LegacyUserConverter;
import fastcampus.ad.migration.gradual.domain.legacyad.adgroup.LegacyAdGroup;
import fastcampus.ad.migration.gradual.domain.legacyad.adgroup.LegacyAdGroupRepository;
import fastcampus.ad.migration.gradual.domain.legacyad.user.LegacyUser;
import fastcampus.ad.migration.gradual.domain.legacyad.user.LegacyUserRepository;
import fastcampus.ad.migration.gradual.domain.recentad.campaign.RecentCampaign;
import fastcampus.ad.migration.gradual.domain.recentad.campaign.RecentCampaignRepository;
import fastcampus.ad.migration.gradual.domain.recentad.user.RecentUser;
import fastcampus.ad.migration.gradual.domain.recentad.user.RecentUserRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class LegacyAdGroupMigrationService extends LegacyMigrationService<LegacyAdGroup, RecentCampaign> {


    public LegacyAdGroupMigrationService(LegacyAdGroupRepository legacyRepository,
                                         LegacyAdGroupConverter converter,
                                         RecentCampaignRepository recentRepository) {
        super(legacyRepository, converter, recentRepository);
    }
}
