package fastcampus.ad.migration.application.user;

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

    @Transactional
    public MigrationUserResult agree(Long userId){
        repository.findById(userId).ifPresent(user -> {
            throw new AlreadyAgreedException(String.format("User [ID: %d] already agreed", user.getId()));
        });
        return MigrationUserResult.from(repository.save(MigrationUser.agreed(userId)));
    }

    public MigrationUserResult findById(Long userId){
        return MigrationUserResult.from(repository.findById(userId).orElseThrow(EntityNotFoundException::new));
    }

    public boolean isDisagreed(Long userId) {
        return repository.findById(userId).isEmpty();
    }
}
