package fastcampus.ad.legacy.domain.user;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class LegacyUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public LegacyUser(String name, LocalDateTime createdAt) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = createdAt;
        this.deletedAt = null;
    }

    public static LegacyUser of(String name){
        return new LegacyUser(name, LocalDateTime.now());
    }

    public void updateName(String newName) {
        name = newName;
        updatedAt = LocalDateTime.now();
    }

    public void delete() {
        deletedAt = LocalDateTime.now();
    }
}
