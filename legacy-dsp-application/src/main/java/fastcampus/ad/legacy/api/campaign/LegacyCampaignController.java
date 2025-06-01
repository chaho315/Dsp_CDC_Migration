package fastcampus.ad.legacy.api.campaign;

import fastcampus.ad.legacy.application.campaign.LegacyCampaignCreateCommand;
import fastcampus.ad.legacy.application.campaign.LegacyCampaignService;

import fastcampus.ad.legacy.application.user.LegacyUserResult;
import fastcampus.ad.legacy.application.user.LegacyUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/campaign/v1")
public class LegacyCampaignController {
    private final LegacyCampaignService service;
    private final LegacyUserService userService;

    @PostMapping("")
    public LegacyCampaignResp create(@RequestBody LegacyCampaignCreateRequest req) {
        LegacyUserResult user = userService.find(req.userId());
        return LegacyCampaignResp.from(service.create(
                new LegacyCampaignCreateCommand(req.name(), user.id(), req.budget())));
    }

    @GetMapping("/{id}")
    public LegacyCampaignResp find(@PathVariable("id") Long id) {
        return LegacyCampaignResp.from(service.find(id));
    }

    @PatchMapping("/name")
    public LegacyCampaignResp updateName(@RequestBody LegacyCampaignUpdateNameRequest req) {
        return LegacyCampaignResp.from(service.updateName(req.id(), req.name()));
    }

    @PatchMapping("/budget")
    public LegacyCampaignResp updateBudget(@RequestBody LegacyCampaignUpdateBudgetRequest req) {
        return LegacyCampaignResp.from(service.updateBudget(req.id(), req.budget()));
    }

    @DeleteMapping("/{id}")
    public LegacyCampaignResp delete(@PathVariable("id") Long id) {
        return LegacyCampaignResp.from(service.delete(id));
    }
}