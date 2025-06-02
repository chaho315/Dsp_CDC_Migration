package fastcampus.ad.migration.gradual.application;

import fastcampus.ad.migration.gradual.domain.legacyad.DeletableEntity;
import fastcampus.ad.migration.gradual.domain.recentad.MigratedEntity;
//제네릭 타입을 통한 구현
public interface LegacyConverter<Legacy extends DeletableEntity, Recent extends MigratedEntity>{

    Recent convert(Legacy legacy);
}
