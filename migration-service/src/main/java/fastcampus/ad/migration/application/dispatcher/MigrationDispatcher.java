package fastcampus.ad.migration.application.dispatcher;

import fastcampus.ad.migration.application.MigrationService;
import fastcampus.ad.migration.application.legacyad.adgroup.LegacyAdGroupMigrationService;
import fastcampus.ad.migration.application.legacyad.campaign.LegacyCampaignMigrationService;
import fastcampus.ad.migration.application.legacyad.keyword.LegacyKeywordMigrationService;
import fastcampus.ad.migration.application.legacyad.user.LegacyUserMigrationService;
import fastcampus.ad.migration.application.user.MigrationUserService;
import fastcampus.ad.migration.domain.AggregateType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MigrationDispatcher {
    //도메인별로 마이그레이션을 하기위한 분기처리 로직 구현
    private final MigrationUserService migrationUserService;
    private final LegacyUserMigrationService userMigrationService;
    private final LegacyCampaignMigrationService campaignMigrationService;
    private final LegacyAdGroupMigrationService adGroupMigrationService;
    private final LegacyKeywordMigrationService keywordMigrationService;


    public boolean dispatch(Long userId,Long aggregatedId, AggregateType aggregateType) {
        if(isDisagreed(userId)){
            return false;
        }
        return migrate(userId, aggregatedId, aggregateType);
    }

    private boolean isDisagreed(Long userId) {
        return migrationUserService.isDisagreed(userId);
    }

    private boolean migrate(Long userId, Long aggregatedId, AggregateType aggregateType) {
        MigrationService service = switch (aggregateType){
            case USER -> userMigrationService;
            case CAMPAIGN -> campaignMigrationService;
            case ADGROUP -> adGroupMigrationService;
            case KEYWORD -> keywordMigrationService;
        };
        boolean result = service.migrate(aggregatedId);
        logMigrationResult(userId, aggregatedId, aggregateType, result);
        return result;
    }

    private void logMigrationResult(Long userId, Long aggregatedId, AggregateType aggregateType, boolean result) {
        if(result){
            log.info("{}", LegacyMigrationLog.success(userId, aggregateType, aggregatedId));
        }else{
            log.error("{}", LegacyMigrationLog.fail(userId, aggregateType, aggregatedId));
        }
    }
}
