package fastcampus.ad.migration.domain.migration.user;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class MigrationUserStatusTest {

    @Test
    void RETRIED_상태는_next_호출하면_에러(){
        assertThatThrownBy(MigrationUserStatus.RETRIED::next)
                .isInstanceOf(RetriedNeedPrevStatusForNextException.class);
    }

}