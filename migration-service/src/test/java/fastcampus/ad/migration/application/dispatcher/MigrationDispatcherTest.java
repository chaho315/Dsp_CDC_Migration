package fastcampus.ad.migration.application.dispatcher;

import fastcampus.ad.migration.application.legacyad.adgroup.LegacyAdGroupMigrationService;
import fastcampus.ad.migration.application.legacyad.campaign.LegacyCampaignMigrationService;
import fastcampus.ad.migration.application.legacyad.keyword.LegacyKeywordMigrationService;
import fastcampus.ad.migration.application.legacyad.user.LegacyUserMigrationService;
import fastcampus.ad.migration.application.user.MigrationUserService;
import fastcampus.ad.migration.domain.AggregateType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MigrationDispatcherTest {

    @Mock
    MigrationUserService migrationUserService;
    @Mock
    LegacyUserMigrationService userMigrationService;
    @Mock
    LegacyCampaignMigrationService campaignMigrationService;
    @Mock
    LegacyAdGroupMigrationService adGroupMigrationService;
    @Mock
    LegacyKeywordMigrationService keywordMigrationService;

    @InjectMocks
    MigrationDispatcher dispatcher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void 사용자가_마이그레이션에_동의하지_않았으면_false를_리턴(boolean migrationSuccess) {
        when(migrationUserService.isDisagreed(1L)).thenReturn(false);
        when(adGroupMigrationService.migrate(1L)).thenReturn(migrationSuccess);

        boolean result = dispatcher.dispatch(1L,1L, AggregateType.ADGROUP);

        assertThat(result).isFalse();
    }

    @ValueSource(booleans = {true, false})
    @ParameterizedTest
    void 사용자가_마이그레이션에_동의했으면_마이그레이션_결과를_리턴(boolean migrationSuccess){
        when(migrationUserService.isDisagreed(1L)).thenReturn(false);
        when(adGroupMigrationService.migrate(1L)).thenReturn(migrationSuccess);

        boolean result = dispatcher.dispatch(1L, 1L, AggregateType.ADGROUP);

        assertThat(result).isEqualTo(migrationSuccess);
    }

}