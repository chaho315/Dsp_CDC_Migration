package fastcampus.ad.migration.application;

public interface MigrationService {
    //id를 받아 마이그레이션을 진행한 후 성공여부를 알려주는 interface
    boolean migrate(Long id);
}
