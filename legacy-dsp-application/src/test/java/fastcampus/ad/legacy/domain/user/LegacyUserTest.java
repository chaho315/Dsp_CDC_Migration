package fastcampus.ad.legacy.domain.user;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LegacyUserTest {
    LegacyUser user = LegacyUser.of("name");

    @Test
    void updateName() {
        user.updateName("newName");

        assertThat(user.getName()).isEqualTo("newName");
    }

    @Test
    void delete() {
        LocalDateTime beforeDeleteDate = LocalDateTime.now();
        user.delete();
        LocalDateTime afterDeleteDate = LocalDateTime.now();

        assertThat(user.getDeletedAt())
                .isAfterOrEqualTo(afterDeleteDate)
                .isBeforeOrEqualTo(beforeDeleteDate);
    }
}