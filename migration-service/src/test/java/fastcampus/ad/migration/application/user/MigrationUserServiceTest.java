package fastcampus.ad.migration.application.user;

import fastcampus.ad.migration.application.legacyad.user.LegacyUserMigrationService;
import fastcampus.ad.migration.domain.migration.user.MigrationUser;
import fastcampus.ad.migration.domain.migration.user.MigrationUserRepository;
import fastcampus.ad.migration.domain.migration.user.MigrationUserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class MigrationUserServiceTest {
    @Mock
    MigrationUserRepository repository;

    @Mock
    LegacyUserMigrationService legacyUserMigrationService;

    @InjectMocks
    MigrationUserService service;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void 사용자가_마이그레이션에_동의함(){
        when(repository.findById(1L)).thenReturn(Optional.empty());
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        LocalDateTime before = LocalDateTime.now();
        MigrationUserResult result = service.agree(1L);
        LocalDateTime after = LocalDateTime.now();

        assertAll(() -> assertThat(result.id()).isEqualTo(1L),
                () -> assertThat(result.status()).isEqualTo(MigrationUserStatus.AGREED),
                () -> assertThat(result.agreedAt())
                        .isAfter(before)
                        .isBefore(after),
                () -> assertThat(result.updatedAt())
                        .isAfter(before)
                        .isBefore(after)
        );
    }

    @Test
    void 이미_마이그레이션에_동의했으면_에러(){
        when(repository.findById(1L)).thenReturn(Optional.of(MigrationUser.agreed(1L)));

        assertThatThrownBy(() -> service.agree(1L))
                .isInstanceOf(AlreadyAgreedException.class);
    }

    @Test
    void 존재하지_않으면_에러(){
        when(repository.findById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.agree(1L))
                .isInstanceOf(AlreadyAgreedException.class);
    }

    @Test
    void 동의하지_않았으면_true(){
        when(repository.findById(1L)).thenReturn(Optional.empty());
        boolean result = service.isDisagreed(1L);
        assertThat(result).isTrue();
    }

    @Test
    void 동의했으면_false(){
        when(repository.findById(1L)).thenReturn(Optional.of(MigrationUser.agreed(1L)));
        boolean result = service.isDisagreed(1L);
        assertThat(result).isFalse();
    }

    @Test
    void 마이그레이션_시작하고_사용자_마이그레이션_성공하면_사용자_상태_업데이트() throws StartMigrationFailedException {
        when(repository.findById(1L)).thenReturn(Optional.of(MigrationUser.agreed(1L)));
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        when(legacyUserMigrationService.migrate(anyLong())).thenReturn(true);

        MigrationUserResult user = service.startMigration(1L);

        assertThat(user.status()).isEqualTo(MigrationUserStatus.USER_FINISHED);
    }

    @Test
    void 마이그레이션_시작하고_사용자_마이그레이션이_실패하면_에러(){
        when(legacyUserMigrationService.migrate(anyLong())).thenReturn(false);
        assertThatThrownBy(() -> service.startMigration(1L)).isInstanceOf(StartMigrationFailedException.class);
    }

    @Test
    void 마이그레이션_진행하면_사용자_상태_업데이트(){
        MigrationUser user = MigrationUser.agreed(1L);
        user.progressMigation();
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        when(repository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        MigrationUserResult result = service.progressMigration(1L);

        assertThat(result.status()).isEqualTo(MigrationUserStatus.ADGROUP_FINISHED);
    }
}