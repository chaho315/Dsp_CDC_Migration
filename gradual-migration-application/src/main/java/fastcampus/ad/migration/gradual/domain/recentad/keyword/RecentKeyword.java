package fastcampus.ad.migration.gradual.domain.recentad.keyword;


import fastcampus.ad.migration.gradual.domain.recentad.MigratedEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class RecentKeyword implements MigratedEntity {

    @Id
    private Long id;

    private String text;
    private Long campaignId;
    private Long userId;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    private LocalDateTime migratedAt;

    private RecentKeyword(Long id, String text, Long campaignId, Long userId, LocalDateTime createdAt, LocalDateTime deletedAt, LocalDateTime migratedAt) {
        this.id = id;
        this.text = text;
        this.campaignId = campaignId;
        this.userId = userId;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.migratedAt = migratedAt;
    }

    public static RecentKeyword migrated(Long id, String text, Long campaignId, Long userId, LocalDateTime createdAt, LocalDateTime deletedAt) {
        return new RecentKeyword(id, text, campaignId, userId, createdAt, deletedAt, LocalDateTime.now());
    }
}
