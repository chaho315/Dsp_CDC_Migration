package fastcampus.ad.migration.gradual.application.dispatcher;

import fastcampus.ad.migration.gradual.application.MigrationService;
import fastcampus.ad.migration.gradual.application.legacyad.adgroup.LegacyAdGroupMigrationService;
import fastcampus.ad.migration.gradual.application.legacyad.campaign.LegacyCampaignMigrationService;
import fastcampus.ad.migration.gradual.application.legacyad.keyword.LegacyKeywordMigrationService;
import fastcampus.ad.migration.gradual.application.legacyad.user.LegacyUserMigrationService;
import fastcampus.ad.migration.gradual.domain.AggregateType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MigrationDispatcher {
    //도메인별로 마이그레이션을 하기위한 분기처리 로직 구현
    private final LegacyUserMigrationService userMigrationService;
    private final LegacyCampaignMigrationService campaignMigrationService;
    private final LegacyAdGroupMigrationService adGroupMigrationService;
    private final LegacyKeywordMigrationService keywordMigrationService;
    
    public boolean dispatch(Long aggregatedId, AggregateType aggregateType) {
        return migrate(aggregatedId, aggregateType);
    }

    private boolean migrate(Long aggregatedId, AggregateType aggregateType) {
        MigrationService service = switch (aggregateType){
            case USER -> userMigrationService;
            case CAMPAIGN -> campaignMigrationService;
            case ADGROUP -> adGroupMigrationService;
            case KEYWORD -> keywordMigrationService;
        };
        boolean result = service.migrate(aggregatedId);
        logMigrationResult(aggregatedId, aggregateType, result);
        return result;
    }

    private void logMigrationResult(Long aggregatedId, AggregateType aggregateType, boolean result) {
        if(result){
            log.info("{}", LegacyMigrationLog.success(aggregateType, aggregatedId));
        }else{
            log.error("{}", LegacyMigrationLog.fail(aggregateType, aggregatedId));
        }
    }
}
