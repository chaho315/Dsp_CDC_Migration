package fastcampus.ad.migration.application.legacyad.keyword;

import fastcampus.ad.migration.application.LegacyMigrationService;
import fastcampus.ad.migration.application.ParentPageLegacyMigrationService;
import fastcampus.ad.migration.domain.legacyad.LegacyPageableRepository;
import fastcampus.ad.migration.domain.legacyad.adgroup.LegacyAdGroup;
import fastcampus.ad.migration.domain.legacyad.adgroup.LegacyAdGroupRepository;
import fastcampus.ad.migration.domain.legacyad.keyword.LegacyKeyword;
import fastcampus.ad.migration.domain.legacyad.keyword.LegacyKeywordRepository;
import fastcampus.ad.migration.domain.migration.PageMigrationRepository;
import fastcampus.ad.migration.domain.migration.keyword.KeywordPageMigration;
import fastcampus.ad.migration.domain.migration.keyword.KeywordPageMigrationRepository;
import fastcampus.ad.migration.domain.recentad.keyword.RecentKeyword;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

import static fastcampus.ad.migration.application.ParentPageLegacyMigrationService.PARENT_PAGE_SIZE;

@Slf4j
@Service
public class ParentPageLegacyKeywordMigrationService extends
        ParentPageLegacyMigrationService<KeywordPageMigration, LegacyKeyword, RecentKeyword, LegacyAdGroup> {


  public ParentPageLegacyKeywordMigrationService(KeywordPageMigrationRepository pageMigrationRepository, LegacyKeywordRepository legacyPageableRepository, LegacyKeywordMigrationService legacyMigrationService, LegacyAdGroupRepository parentRepository) {
    super(pageMigrationRepository, legacyPageableRepository, legacyMigrationService, parentRepository);
  }

  @Override
  protected KeywordPageMigration firstPageMigration(Long userId, boolean isSuccess, Page<LegacyKeyword> legacyKeywords) {
    return KeywordPageMigration.first(isSuccess,userId, PARENT_PAGE_SIZE, legacyKeywords.getTotalElements());
  }

  @Override
  protected List<LegacyKeyword> findAllByParentIdIn(List<Long> legacyParentIds) {
    return ((LegacyKeywordRepository) legacyPageableRepository).findAllByAdGroupIdInAndDeletedAtIsNull(legacyParentIds);
  }
}
