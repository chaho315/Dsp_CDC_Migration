package fastcampus.ad.migration.application.legacyad.adgroup;

import fastcampus.ad.migration.application.LegacyMigrationService;
import fastcampus.ad.migration.application.PageLegacyMigrationService;
import fastcampus.ad.migration.domain.legacyad.adgroup.LegacyAdGroup;
import fastcampus.ad.migration.domain.legacyad.adgroup.LegacyAdGroupRepository;
import fastcampus.ad.migration.domain.migration.PageMigrationRepository;
import fastcampus.ad.migration.domain.migration.adgroup.AdGroupPageMigration;
import fastcampus.ad.migration.domain.migration.adgroup.AdGroupPageMigrationRepository;
import fastcampus.ad.migration.domain.recentad.campaign.RecentCampaign;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class PageLegacyAdGroupMigrationService extends PageLegacyMigrationService<AdGroupPageMigration, LegacyAdGroup, RecentCampaign> {
    public PageLegacyAdGroupMigrationService(AdGroupPageMigrationRepository pageMigrationRepository, LegacyAdGroupRepository legacyAdGroupRepository, LegacyAdGroupMigrationService legacyAdGroupMigrationService) {
        super(pageMigrationRepository, legacyAdGroupRepository, legacyAdGroupMigrationService);
    }

    @Override
    protected AdGroupPageMigration firstPageMigration(Long userId, boolean isSuccess, Page<LegacyAdGroup> legacyAdGroups) {
        return AdGroupPageMigration.first(isSuccess, userId, PAGE_SIZE, legacyAdGroups.getTotalElements());
    }
}
