package fastcampus.ad.migration.internal.api.user;

import fastcampus.ad.migration.application.user.MigrationUserResult;
import fastcampus.ad.migration.application.user.MigrationUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/migration/v1/user")
public class MigrationUserController {
    private final MigrationUserService service;

    @PostMapping("/{userId}/agree")
    public MigrationUserResp agree(@PathVariable Long userId){
        MigrationUserResult result = service.agree(userId);
        return new MigrationUserResp(result.id(), result.status(), result.agreedAt(), result.updatedAt());
    }

    @GetMapping("/{userId}")
    public MigrationUserResp find(@PathVariable Long userId){
        MigrationUserResult result = service.findById(userId);
        return new MigrationUserResp(result.id(), result.status(), result.agreedAt(), result.updatedAt());
    }
}
