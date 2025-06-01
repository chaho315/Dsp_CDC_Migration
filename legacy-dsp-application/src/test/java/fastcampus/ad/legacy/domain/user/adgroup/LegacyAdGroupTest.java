package fastcampus.ad.legacy.domain.user.adgroup;

import fastcampus.ad.legacy.domain.user.campaign.LegacyCampaign;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LegacyAdGroupTest {
    LegacyAdGroup adGroup = LegacyAdGroup.of("adGroup", 1L, 1L, "http://fastcampus.com");

    @Test
    void updateLinkUrl() {
        LocalDateTime before = LocalDateTime.now();
        adGroup.updateLinkUrl("http://fast.com");
        LocalDateTime after = LocalDateTime.now();

        assertAll(
                () -> assertThat(adGroup.getLinkUrl()).isEqualTo("http://fast.com"),
                () -> assertThat(adGroup.getUpdatedAt())
                        .isAfterOrEqualTo(before)
                        .isBeforeOrEqualTo(after)
        );
    }

}