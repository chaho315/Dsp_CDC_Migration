package fastcampus.ad.migration.application;

import fastcampus.ad.migration.domain.legacyad.DeletableEntity;
import fastcampus.ad.migration.domain.legacyad.LegacyPageableRepository;
import fastcampus.ad.migration.domain.migration.PageMigration;
import fastcampus.ad.migration.domain.migration.PageMigrationRepository;
import fastcampus.ad.migration.domain.recentad.MigratedEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public abstract class ParentPageLegacyMigrationService<P extends PageMigration<P>, Legacy extends DeletableEntity, Recent extends MigratedEntity, LegacyParent extends DeletableEntity> extends PageLegacyMigrationService<P, Legacy, Recent> {

    protected final LegacyPageableRepository<LegacyParent> parentRepository;

    public final static Integer PARENT_PAGE_SIZE = 10;
    public ParentPageLegacyMigrationService(PageMigrationRepository<P> pageMigrationRepository,
                                            LegacyPageableRepository<Legacy> legacyPageableRepository,
                                            LegacyMigrationService<Legacy, Recent> legacyMigrationService,
                                            LegacyPageableRepository<LegacyParent> parentRepository) {
        super(pageMigrationRepository, legacyPageableRepository, legacyMigrationService);
        this.parentRepository = parentRepository;
    }

    @Override
    protected Page<Legacy> findPage(Long userId, Integer pageNumber){
        Page<LegacyParent> legacyParentsPage = parentRepository.findAllByUserIdAndDeletedAtIsNullOrderById(userId, PageRequest.of(pageNumber, PARENT_PAGE_SIZE));
        List<Long> legacyParentIds = legacyParentsPage.stream()
                .map(DeletableEntity::getId)
                .toList();
        List<Legacy> legacies = findAllByParentIdIn(legacyParentIds);
        return new PageImpl<>(legacies, PageRequest.of(pageNumber, PARENT_PAGE_SIZE), legacyParentsPage.getTotalPages());
    }

    protected abstract List<Legacy> findAllByParentIdIn(List<Long> legacyParentIds) ;
}
