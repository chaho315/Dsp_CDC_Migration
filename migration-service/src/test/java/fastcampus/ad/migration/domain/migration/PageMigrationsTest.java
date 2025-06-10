package fastcampus.ad.migration.domain.migration;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PageMigrationsTest {

    PageMigrations pageMigrations = PageMigrations.of(List.of(new PageMigrationTestable(1L, 4, 10, 100L),
            new PageMigrationTestable(2L, 3, 10, 100L),
            new PageMigrationTestable(3L, 0, 10, 0L)
    ));

    @Test
    void migratedCount(){

        Long result = pageMigrations.migratedCount();

        assertThat(result).isEqualTo(90);
    }

    @Test
    void totalCount(){
        Long result = pageMigrations.totalCount();

        assertThat(result).isEqualTo(200);
    }

}