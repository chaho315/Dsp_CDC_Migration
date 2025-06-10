package fastcampus.ad.migration.application.monitoring;

import fastcampus.ad.migration.domain.migration.PageMigrations;
import fastcampus.ad.migration.domain.migration.user.MigrationUserStatus;
import fastcampus.ad.migration.domain.migration.user.MigrationUsers;

import java.util.Map;

public record MigrationMonitorMetrics(Map<MigrationUserStatus, Long> statusStatistics, Long adGroupMigratedCount, Long adGroupTotalCount, Long keywordMigratedCount, Long keywordTotalCount) {
    public static MigrationMonitorMetrics from(MigrationUsers migrationUsers, PageMigrations adGroupPageMigrations, PageMigrations keywordPageMigrations) {
        return new MigrationMonitorMetrics(migrationUsers.statusStatistics(), adGroupPageMigrations.migratedCount(), adGroupPageMigrations.totalCount(), keywordPageMigrations.migratedCount(), keywordPageMigrations.totalCount());
    }
}
