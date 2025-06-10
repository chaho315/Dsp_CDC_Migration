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
        () -> assertThat(user.getUpdatedAt()).isAfterOrEqualTo(before).isBeforeOrEqualTo(after),
                () -> assertThat(user.getPrevStatus()).isEqualTo(MigrationUserStatus.AGREED)
        );
    }

    @Test
    void 재시도하면_상태를_RETRIED로_바꾸고_이전_상태를_저장(){
        MigrationUser user = MigrationUser.agreed(1L);
        user.progressMigation();
        LocalDateTime before = LocalDateTime.now();
        user.retry();
        LocalDateTime after = LocalDateTime.now();

        assertAll(() -> assertThat(user.getStatus()).isEqualTo(MigrationUserStatus.RETRIED),
                () -> assertThat(user.getPrevStatus()).isEqualTo(MigrationUserStatus.USER_FINISHED),
                () -> assertThat(user.getUpdatedAt()).isAfterOrEqualTo(before).isBeforeOrEqualTo(after));
    }

    @Test
    void 재시도하고_다음_도메인_마이그레이션_진행할때는_prevStatus의_다음_상태로_변경(){
        MigrationUser user = MigrationUser.agreed(1L);
        user.progressMigation(); //user-finished

        user.retry();//retried, prev: user-finished

        user.progressMigation();// prev.next -> adgroup-finished, prev: user-finished

        assertAll(() -> assertThat(user.getStatus()).isEqualTo(MigrationUserStatus.ADGROUP_FINISHED),
                () -> assertThat(user.getPrevStatus()).isEqualTo(MigrationUserStatus.USER_FINISHED));
    }

    @Test
    void 이미_재시도한_경우_업데이트_시간만_변경(){
        MigrationUser user = MigrationUser.agreed(1L);
        user.progressMigation();

        user.retry();

        LocalDateTime before = LocalDateTime.now();
        user.retry();
        LocalDateTime after = LocalDateTime.now();

        assertAll(() -> assertThat(user.getStatus()).isEqualTo(MigrationUserStatus.RETRIED),
                () -> assertThat(user.getPrevStatus()).isEqualTo(MigrationUserStatus.USER_FINISHED),
                () -> assertThat(user.getUpdatedAt()).isAfterOrEqualTo(before).isBeforeOrEqualTo(after));
    }

}