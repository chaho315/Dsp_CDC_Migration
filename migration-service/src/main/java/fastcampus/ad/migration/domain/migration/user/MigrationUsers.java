package fastcampus.ad.migration.domain.migration.user;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public record MigrationUsers(Iterable<MigrationUser> users) {
    public static MigrationUsers of(Iterable<MigrationUser> users) {
        return new MigrationUsers(users);
    }

    public Map<MigrationUserStatus, Long> statusStatistics() {
        return StreamSupport.stream(users.spliterator(), true).collect(Collectors.groupingBy(MigrationUser::getStatus, Collectors.counting()));
    }
}
