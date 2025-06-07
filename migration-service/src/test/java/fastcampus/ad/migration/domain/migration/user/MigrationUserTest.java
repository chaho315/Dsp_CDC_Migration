package fastcampus.ad.migration.domain.migration.user;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MigrationUserTest {

    @Test
    void 다음_도메인_마이그레이션_진행(){
        MigrationUser user = MigrationUser.agreed(1L);

        LocalDateTime before = LocalDateTime.now();
        user.progressMigation();
        LocalDateTime after = LocalDateTime.now();

        assertAll(() -> assertThat(user.getStatus()).isEqualTo(MigrationUserStatus.AGREED.next()),
        () -> assertThat(user.getUpdatedAt()).isAfter(before).isBefore(after),
                () -> assertThat(user.getPrevStatus()).isEqualTo(MigrationUserStatus.AGREED)
        );
    }

}