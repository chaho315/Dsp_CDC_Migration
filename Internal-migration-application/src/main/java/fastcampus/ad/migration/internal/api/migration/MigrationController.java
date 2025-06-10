package fastcampus.ad.migration.internal.api.migration;

import fastcampus.ad.migration.application.dispatcher.MigrationDispatcher;
import fastcampus.ad.migration.application.monitoring.MigrationMonitor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/migration/v1")
public class MigrationController {
    private final MigrationDispatcher dispatcher;
    private final MigrationMonitor migrationMonitor;

    @PutMapping("/retry")
    public MigrationRetryResp retry(@RequestBody MigrationRetryRequest req){
        boolean result = dispatcher.dispatch(req.userId(), req.aggregateId(), req.aggregateType());
        return new MigrationRetryResp(result);
    }

    @GetMapping("/progress")
    public MigrationProgressResp measure(){
        return MigrationProgressResp.from(migrationMonitor.measure());
    }
}
