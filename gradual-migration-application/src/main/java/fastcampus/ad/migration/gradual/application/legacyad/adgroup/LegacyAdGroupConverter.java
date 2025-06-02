package fastcampus.ad.migration.gradual.application.legacyad.adgroup;

import fastcampus.ad.migration.gradual.application.LegacyConverter;
import fastcampus.ad.migration.gradual.domain.legacyad.adgroup.LegacyAdGroup;
import fastcampus.ad.migration.gradual.domain.legacyad.campaign.LegacyCampaign;
import fastcampus.ad.migration.gradual.domain.legacyad.campaign.LegacyCampaignRepository;
import fastcampus.ad.migration.gradual.domain.legacyad.user.LegacyUser;
import fastcampus.ad.migration.gradual.domain.recentad.campaign.RecentCampaign;
import fastcampus.ad.migration.gradual.domain.recentad.user.RecentUser;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LegacyAdGroupConverter implements LegacyConverter<LegacyAdGroup, RecentCampaign> {

    private final LegacyCampaignRepository legacyCampaignRepository;

    @Override
    public RecentCampaign convert(LegacyAdGroup legacyAdGroup) {
        LegacyCampaign campaign = legacyCampaignRepository.findById(legacyAdGroup.getCampaignId()).orElseThrow(EntityNotFoundException::new);
        return RecentCampaign.migrated(legacyAdGroup.getId(),
                campaign.getName()+"_"+legacyAdGroup.getName(),
                legacyAdGroup.getUserId(),
                campaign.getBudget(),
                legacyAdGroup.getLinkUrl(),
                legacyAdGroup.getCreatedAt(),
                legacyAdGroup.getUpdatedAt(),
                legacyAdGroup.getDeletedAt());
    }
}
