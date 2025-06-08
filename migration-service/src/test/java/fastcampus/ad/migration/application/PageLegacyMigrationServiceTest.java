package fastcampus.ad.migration.application;

import fastcampus.ad.migration.application.legacyad.PageMigrationResult;
import fastcampus.ad.migration.domain.legacyad.DeletableEntity;
import fastcampus.ad.migration.domain.legacyad.LegacyPageableRepository;
import fastcampus.ad.migration.domain.migration.PageMigration;
import fastcampus.ad.migration.domain.migration.PageMigrationRepository;
import fastcampus.ad.migration.domain.migration.PageMigrationTestable;
import fastcampus.ad.migration.domain.recentad.MigratedEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static fastcampus.ad.migration.application.PageLegacyMigrationService.PAGE_SIZE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

class PageLegacyMigrationServiceTest {

    @Mock
    PageMigrationRepository<PageMigrationTestable> pageMigrationRepository;
    @Mock
    LegacyPageableRepository<DeletableEntity> legacyPageableRepository;
    @Mock
    LegacyMigrationService<DeletableEntity, MigratedEntity> legacyMigrationService;

    PageLegacyMigrationService<PageMigrationTestable, DeletableEntity, MigratedEntity> service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new PageLegacyMigrationService<>(pageMigrationRepository, legacyPageableRepository, legacyMigrationService) {
            @Override
            protected PageMigrationTestable firstPageMigration(Long userId, boolean isSuccess, Page<DeletableEntity> legacyPage) {
                if(isSuccess){
                    return new PageMigrationTestable(userId, PageMigration.INIT_PAGE_NUMBER, PAGE_SIZE, legacyPage.getTotalElements());
                }else{
                    return new PageMigrationTestable(userId, PageMigration.NOT_STARTED_PAGE_NUMBER, PAGE_SIZE, legacyPage.getTotalElements());
                }
            }
        };
    }

    @Test
    void pageMigration이_시작이고_마이그레이션이_성공하면_INIT_PAGE_NUMBER로_PAGE_MIGRATION을_저장(){
        when(pageMigrationRepository.findById(any())).thenReturn(Optional.empty());
        when(legacyPageableRepository.findAllByUserIdAndDeletedAtIsNullOrderById(any(), any())).thenReturn(new PageImpl<>(List.of(legacyTestable(1L))));
        when(legacyMigrationService.migrate(anyList())).thenReturn(true);
        when(pageMigrationRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        PageMigrationResult result = service.migrate(1L);

        assertThat(result).isEqualTo(new PageMigrationResult(1L, PageMigration.INIT_PAGE_NUMBER, 1, 1L, true));
    }

    @Test
    void pageMigration이_시작이고_마이그레이션이_실패하면_NOT_STARTED_PAGE_NUMBER로_PAGE_MIGRATION을_저장(){
        when(pageMigrationRepository.findById(any())).thenReturn(Optional.empty());
        when(legacyPageableRepository.findAllByUserIdAndDeletedAtIsNullOrderById(any(), any())).thenReturn(new PageImpl<>(List.of(legacyTestable(1L))));
        when(legacyMigrationService.migrate(anyList())).thenReturn(false);
        when(pageMigrationRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        PageMigrationResult result = service.migrate(1L);

        assertThat(result).isEqualTo(new PageMigrationResult(1L, PageMigration.NOT_STARTED_PAGE_NUMBER, 1, 1L, false));
    }

    @Test
    void pageMigration_두번째_페이지이고_마이그레이션이_성공하면_다음_페이지로_저장(){
        when(pageMigrationRepository.findById(any())).thenReturn(Optional.of(new PageMigrationTestable(1L, PageMigration.INIT_PAGE_NUMBER, PAGE_SIZE, PAGE_SIZE * 3L)));
        when(legacyPageableRepository.findAllByUserIdAndDeletedAtIsNullOrderById(any(), any())).thenReturn(new PageImpl<>(List.of(legacyTestable(1L)), PageRequest.of(PageMigration.INIT_PAGE_NUMBER + PageMigration.PAGE_INCREMENT, PAGE_SIZE), PAGE_SIZE * 3L));
        when(legacyMigrationService.migrate(anyList())).thenReturn(true);
        when(pageMigrationRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        PageMigrationResult result = service.migrate(1L);

        assertThat(result).isEqualTo(new PageMigrationResult(1L, PageMigration.INIT_PAGE_NUMBER + PageMigration.PAGE_INCREMENT, 3, PAGE_SIZE * 3L, true));
    }

    @Test
    void pageMigration_두번째_페이지이고_마이그레이션이_실패하면_기존_페이지로_저장(){
        when(pageMigrationRepository.findById(any())).thenReturn(Optional.of(new PageMigrationTestable(1L, PageMigration.INIT_PAGE_NUMBER, PAGE_SIZE, PAGE_SIZE * 3L)));
        when(legacyPageableRepository.findAllByUserIdAndDeletedAtIsNullOrderById(any(), any())).thenReturn(new PageImpl<>(List.of(legacyTestable(1L)), PageRequest.of(PageMigration.INIT_PAGE_NUMBER + PageMigration.PAGE_INCREMENT, PAGE_SIZE), PAGE_SIZE * 3L));
        when(legacyMigrationService.migrate(anyList())).thenReturn(false);
        when(pageMigrationRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        PageMigrationResult result = service.migrate(1L);

        assertThat(result).isEqualTo(new PageMigrationResult(1L, PageMigration.INIT_PAGE_NUMBER, 3, PAGE_SIZE * 3L, false));
    }

    private DeletableEntity legacyTestable(Long id) {
        return new DeletableEntity() {
            @Override
            public LocalDateTime getDeletedAt() {
                return null;
            }

            @Override
            public Long getId() {
                return id;
            }
        };
    }
}

