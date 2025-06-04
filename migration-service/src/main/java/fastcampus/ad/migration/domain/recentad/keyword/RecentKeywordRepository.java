package fastcampus.ad.migration.domain.recentad.keyword;

import fastcampus.ad.migration.domain.recentad.keyword.RecentKeyword;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecentKeywordRepository extends CrudRepository<RecentKeyword, Long> {
}
