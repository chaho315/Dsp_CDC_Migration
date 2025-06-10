package fastcampus.ad.migration.application;

import fastcampus.ad.migration.application.legacyad.PageMigrationResult;
import fastcampus.ad.migration.domain.legacyad.DeletableEntity;
import fastcampus.ad.migration.domain.legacyad.LegacyPageableRepository;
import fastcampus.ad.migration.domain.migration.PageMigration;
import fastcampus.ad.migration.domain.migration.PageMigrationRepository;
import fastcampus.ad.migration.domain.recentad.MigratedEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public abstract class PageLegacyMigrationService<P extends PageMigration<P>, Legacy extends DeletableEntity, Recent extends MigratedEntity> {
    public final static Integer PAGE_SIZE = 1;
    protected final PageMigrationRepository<P> pageMigrationRepository;
    protected final LegacyPageableRepository<Legacy> legacyLongCrudRepository;
    protected final LegacyMigrationService<Legacy, Recent> legacyMigrationService;

    @Transactional
    public PageMigrationResult migrate(Long userId){
        return pageMigrationRepository.findById(userId)
                .map(this::migrationNextPage)
                .orElseGet(() -> startPageMigration(userId));
    }

    private PageMigrationResult migrationNextPage(P pageMigration) {
        Integer pageNumber = pageMigration.nextPageNumber();
        Page<Legacy> legacyPage = findPage(pageMigration.getId(), pageNumber);
        boolean isSuccess = migrate(legacyPage);
        pageMigration.progress(isSuccess, legacyPage.getTotalElements());
        pageMigrationRepository.save(pageMigration);
        return new PageMigrationResult(pageMigration.getId(), pageMigration.getPageNumber(), legacyPage.getTotalPages(), legacyPage.getTotalElements(), isSuccess);
    }

    private PageMigrationResult startPageMigration(Long userId) {
        Integer pageNumber = PageMigration.INIT_PAGE_NUMBER;
        Page<Legacy> legacyPage = findPage(userId, pageNumber);
        boolean isSuccess = migrate(legacyPage);
        P pageMigration = firstPageMigration(userId, isSuccess, legacyPage);
        pageMigrationRepository.save(pageMigration);
        return new PageMigrationResult(userId, pageMigration.getPageNumber(), legacyPage.getTotalPages(), pageMigration.getTotalCount(), isSuccess);
    }

    protected abstract P firstPageMigration(Long userId, boolean isSuccess, Page<Legacy> legacyPage);

    private boolean migrate(Page<Legacy> legacyPage) {
        return legacyMigrationService.migrate(legacyPage.toList());
    }

    private Page<Legacy> findPage(Long userId, Integer pageNumber) {
        return legacyLongCrudRepository.findAllByUserIdAndDeletedAtIsNullOrderById(userId, PageRequest.of(pageNumber, PAGE_SIZE));
    }
}
