package fastcampus.ad.migration.application.user;

import fastcampus.ad.migration.application.legacyad.user.LegacyUserMigrationService;
import fastcampus.ad.migration.domain.migration.user.MigrationUser;
import fastcampus.ad.migration.domain.migration.user.MigrationUserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MigrationUserService {
    private final MigrationUserRepository repository;
    private final LegacyUserMigrationService legacyUserMigrationService;

    @Transactional
    public MigrationUserResult agree(Long userId){
        repository.findById(userId).ifPresent(user -> {
            throw new AlreadyAgreedException(String.format("User [ID: %d] already agreed", user.getId()));
        });
        return MigrationUserResult.from(repository.save(MigrationUser.agreed(userId)));
    }

    public MigrationUserResult findById(Long userId){
        return MigrationUserResult.from(find(userId));
    }

    private MigrationUser find(Long userId) {
        return repository.findById(userId).orElseThrow(EntityNotFoundException::new);
    }

    public boolean isDisagreed(Long userId) {
        return repository.findById(userId).isEmpty();
    }
    @Transactional
    public MigrationUser startMigration(Long userId) throws StartMigrationFailedException {
        boolean result = legacyUserMigrationService.migrate(userId);
        if(result){
            return progressMigration(userId);
        }
        throw new StartMigrationFailedException();
    }
    @Transactional
    public MigrationUser progressMigration(Long userId) {
        MigrationUser migrationUser = find(userId);
        migrationUser.progressMigation();
        return repository.save(migrationUser);
    }

}
