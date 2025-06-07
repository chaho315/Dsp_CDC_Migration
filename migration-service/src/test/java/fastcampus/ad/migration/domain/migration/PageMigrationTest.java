package fastcampus.ad.migration.domain.migration;

import fastcampus.ad.migration.domain.AggregateType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PageMigrationTest {

    @ValueSource(ints = {0, 1, 7, 8})
    @ParameterizedTest
    void isNotFinished(int pageNumber) {
        PageMigration<PageMigrationTestable> pageMigration = new PageMigrationTestable(1L, pageNumber, 10, 100L);

        boolean isFinished = pageMigration.isFinished();

        assertThat(isFinished).isFalse();
    }

    @ValueSource(ints = {9, 10, 11})
    @ParameterizedTest
    void isFinished(int pageNumber) {
        PageMigration<PageMigrationTestable> pageMigration = new PageMigrationTestable(1L, pageNumber, 10, 100L);

        boolean isFinished = pageMigration.isFinished();

        assertThat(isFinished).isTrue();
    }

    @ValueSource(ints = {0, 1, 7, 8})
    @ParameterizedTest
    void nextPageNumber(int pageNumber) {
        PageMigration<PageMigrationTestable> pageMigration = new PageMigrationTestable(1L, pageNumber, 10, 100L);

        Integer nextPageNumber = pageMigration.nextPageNumber();

        assertThat(nextPageNumber).isEqualTo(pageNumber + PageMigration.PAGE_INCREMENT);
    }

    @Test
    void progressSuccess(){
        PageMigration<PageMigrationTestable> pageMigration = new PageMigrationTestable(1L, PageMigration.INIT_PAGE_NUMBER, 10, 100L);

        LocalDateTime before = LocalDateTime.now();
        pageMigration.progress(true, 110L);
        LocalDateTime after = LocalDateTime.now();

        assertAll(
                () -> assertThat(pageMigration.pageNumber).isEqualTo(PageMigration.INIT_PAGE_NUMBER + PageMigration.PAGE_INCREMENT),
                () -> assertThat(pageMigration.updatedAt).isAfter(before).isBefore(after),
                () -> assertThat(pageMigration.totalCount).isEqualTo(110L)
        );
    }

    @Test
    void progressFail(){
        PageMigration<PageMigrationTestable> pageMigration = new PageMigrationTestable(1L, PageMigration.INIT_PAGE_NUMBER, 10, 100L);

        LocalDateTime before = LocalDateTime.now();
        pageMigration.progress(false, 110L);
        LocalDateTime after = LocalDateTime.now();

        assertAll(
                () -> assertThat(pageMigration.pageNumber).isEqualTo(PageMigration.INIT_PAGE_NUMBER),
                () -> assertThat(pageMigration.updatedAt).isAfter(before).isBefore(after),
                () -> assertThat(pageMigration.totalCount).isEqualTo(110L)
        );
    }

    @Test
    void migrationSuccess여도_이미_finished라면_page_number를_증가시키지_않음(){
        PageMigration<PageMigrationTestable> pageMigration = new PageMigrationTestable(1L, 9, 10, 100L);

        LocalDateTime before = LocalDateTime.now();
        pageMigration.progress(true, 100L);
        LocalDateTime after = LocalDateTime.now();

        assertAll(
                () -> assertThat(pageMigration.pageNumber).isEqualTo(9),
                () -> assertThat(pageMigration.updatedAt).isAfter(before).isBefore(after),
                () -> assertThat(pageMigration.totalCount).isEqualTo(100L)
        );
    }

}