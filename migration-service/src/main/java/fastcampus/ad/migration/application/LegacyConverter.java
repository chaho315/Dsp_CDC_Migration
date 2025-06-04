package fastcampus.ad.migration.application;

import fastcampus.ad.migration.domain.legacyad.DeletableEntity;
import fastcampus.ad.migration.domain.recentad.MigratedEntity;
//제네릭 타입을 통한 구현
public interface LegacyConverter<Legacy extends DeletableEntity, Recent extends MigratedEntity>{

    Recent convert(Legacy legacy);
}
