package fastcampus.ad.migration.application.user;

import fastcampus.ad.migration.domain.migration.user.MigrationUser;
import fastcampus.ad.migration.domain.migration.user.MigrationUserStatus;

import java.time.LocalDateTime;

public record MigrationUserResult(Long id, MigrationUserStatus status,MigrationUserStatus prevStatus, LocalDateTime agreedAt, LocalDateTime updatedAt) {

    public static MigrationUserResult from(MigrationUser user){
        return new MigrationUserResult(user.getId(), user.getStatus(),user.getPrevStatus(), user.getAgreedAt(), user.getUpdatedAt());
    }
}
