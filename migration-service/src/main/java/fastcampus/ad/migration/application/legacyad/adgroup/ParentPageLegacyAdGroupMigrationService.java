package fastcampus.ad.migration.application.legacyad.adgroup;

import fastcampus.ad.migration.application.LegacyMigrationService;
import fastcampus.ad.migration.application.ParentPageLegacyMigrationService;
import fastcampus.ad.migration.domain.legacyad.adgroup.LegacyAdGroup;
import fastcampus.ad.migration.domain.legacyad.adgroup.LegacyAdGroupRepository;
import fastcampus.ad.migration.domain.legacyad.campaign.LegacyCampaign;
import fastcampus.ad.migration.domain.legacyad.campaign.LegacyCampaignRepository;
import fastcampus.ad.migration.domain.migration.PageMigrationRepository;
import fastcampus.ad.migration.domain.migration.adgroup.AdGroupPageMigration;
import fastcampus.ad.migration.domain.migration.adgroup.AdGroupPageMigrationRepository;
import fastcampus.ad.migration.domain.recentad.campaign.RecentCampaign;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParentPageLegacyAdGroupMigrationService extends ParentPageLegacyMigrationService<AdGroupPageMigration, LegacyAdGroup, RecentCampaign, LegacyCampaign> {

    public ParentPageLegacyAdGroupMigrationService(AdGroupPageMigrationRepository pageMigrationRepository, LegacyAdGroupRepository legacyPageableRepository, LegacyAdGroupMigrationService legacyMigrationService, LegacyCampaignRepository parentRepository) {
        super(pageMigrationRepository, legacyPageableRepository, legacyMigrationService, parentRepository);
    }

    @Override
    protected AdGroupPageMigration firstPageMigration(Long userId, boolean isSuccess, Page<LegacyAdGroup> legacyAdGroups) {
        return AdGroupPageMigration.first(isSuccess, userId, PARENT_PAGE_SIZE, legacyAdGroups.getTotalElements());
    }

    @Override
    protected List<LegacyAdGroup> findAllByParentIdIn(List<Long> legacyParentIds) {
        return ((LegacyAdGroupRepository)legacyPageableRepository).findAllByCampaignIdInAndDeletedAtIsNull(legacyParentIds);
    }

}
