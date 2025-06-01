package fastcampus.ad.legacy.domain.user.campaign;

import fastcampus.ad.legacy.domain.campaign.LegacyCampaign;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LegacyCampaignTest {

    LegacyCampaign campaign = LegacyCampaign.of("campaign", 1L, 100L);

    @Test
    void updateBudget(){
        LocalDateTime before = LocalDateTime.now();
        campaign.updateBudget(200L);
        LocalDateTime after = LocalDateTime.now();

        assertAll(
                () -> assertThat(campaign.getBudget()).isEqualTo(200L),
                () -> assertThat(campaign.getUpdatedAt())
                        .isAfterOrEqualTo(before)
                        .isBeforeOrEqualTo(after)
        );
    }

}