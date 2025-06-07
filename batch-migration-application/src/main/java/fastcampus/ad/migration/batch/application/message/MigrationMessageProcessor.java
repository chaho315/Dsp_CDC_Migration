package fastcampus.ad.migration.batch.application.message;

import fastcampus.ad.migration.application.user.MigrationUserService;
import fastcampus.ad.migration.application.user.StartMigrationFailedException;
import fastcampus.ad.migration.domain.AggregateType;
import fastcampus.ad.migration.domain.migration.user.MigrationUser;
import fastcampus.ad.migration.domain.migration.user.MigrationUserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MigrationMessageProcessor {
    private final MigrationUserService migrationUserService;

    public void progressMigration(MigrationUserStatus status, Long userId){
        switch (status){
            case AGREED -> getStartMigration(userId);
            case USER_FINISHED -> dispatch(userId, AggregateType.ADGROUP);
            case ADGROUP_FINISHED -> dispatch(userId, AggregateType.KEYWORD);
            case KEYWORD_FINISHED -> migrationUserService.progressMigration(userId);
        }
    }

    private void getStartMigration(Long userId){
        try {
             migrationUserService.startMigration(userId);
        } catch (StartMigrationFailedException e) {
            log.error("start migration failed", e);
        }
    }

    public void progressPageMigration(Long userId, AggregateType aggregateType, boolean isFinished) {
        if(isFinished){
            migrationUserService.progressMigration(userId);
        }else{
            dispatch(userId, aggregateType);
        }
    }

    private void dispatch(Long userId, AggregateType aggregateType) {

    }
}
