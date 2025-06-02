package fastcampus.ad.migration.gradual.application.legacyad.user;

import fastcampus.ad.migration.gradual.application.LegacyConverter;
import fastcampus.ad.migration.gradual.application.LegacyMigrationService;
import fastcampus.ad.migration.gradual.domain.legacyad.user.LegacyUser;
import fastcampus.ad.migration.gradual.domain.legacyad.user.LegacyUserRepository;
import fastcampus.ad.migration.gradual.domain.recentad.user.RecentUser;
import fastcampus.ad.migration.gradual.domain.recentad.user.RecentUserRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

@Service
public class LegacyUserMigrationService extends LegacyMigrationService<LegacyUser, RecentUser> {

    public LegacyUserMigrationService(LegacyUserRepository legacyRepository,
                                      LegacyUserConverter converter,
                                      RecentUserRepository recentRepository) {
        super(legacyRepository, converter, recentRepository);
    }
}
