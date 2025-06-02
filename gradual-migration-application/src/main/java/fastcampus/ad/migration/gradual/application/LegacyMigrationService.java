package fastcampus.ad.migration.gradual.application;

import fastcampus.ad.migration.gradual.domain.legacyad.DeletableEntity;
import fastcampus.ad.migration.gradual.domain.recentad.MigratedEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;

//별도 bean으로 사용되지 않을거기 때문에 추상클래스로 지정
@Slf4j
public abstract class LegacyMigrationService<Legacy extends DeletableEntity, Recent extends MigratedEntity> implements MigrationService{

    protected CrudRepository<Legacy, Long> legacyRepository;
    protected LegacyConverter<Legacy, Recent> converter;
    protected CrudRepository<Recent, Long> recentRepository;

    public LegacyMigrationService(CrudRepository<Legacy, Long> legacyRepository, LegacyConverter<Legacy, Recent> converter, CrudRepository<Recent, Long> recentRepository) {
        this.legacyRepository = legacyRepository;
        this.converter = converter;
        this.recentRepository = recentRepository;
    }

    @Override
    public boolean migrate(Long id) {
        try {
            migrate(findLegacy(id));

            return true;
        }catch(RuntimeException e) {
            log.error("migration errror",e);
            return false;
        }
    }
    protected void migrate(Legacy legacy) {
        if(legacy.isDeleted()) {
            deleteRecent(legacy.getId());
        }else{
            saveRecent(convert(legacy));
        }
    }

    private void deleteRecent(Long id) {
        recentRepository.findById(id).ifPresent(recent -> recentRepository.delete(recent));
    }

    private void saveRecent(Recent recent) {
        recentRepository.save(recent);
    }
    private Recent convert(Legacy legacy) {
        return converter.convert(legacy);
    }

    private Legacy findLegacy(Long id) {
        return legacyRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
