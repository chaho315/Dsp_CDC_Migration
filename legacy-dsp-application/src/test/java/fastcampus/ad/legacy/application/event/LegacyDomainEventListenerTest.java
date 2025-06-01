package fastcampus.ad.legacy.application.event;

import fastcampus.ad.legacy.application.adgroup.LegacyAdGroupCreateCommand;
import fastcampus.ad.legacy.application.adgroup.LegacyAdGroupResult;
import fastcampus.ad.legacy.application.adgroup.LegacyAdGroupService;
import fastcampus.ad.legacy.application.campaign.LegacyCampaignCreateCommand;
import fastcampus.ad.legacy.application.campaign.LegacyCampaignResult;
import fastcampus.ad.legacy.application.campaign.LegacyCampaignService;
import fastcampus.ad.legacy.application.keyword.LegacyKeywordCreateCommand;
import fastcampus.ad.legacy.application.keyword.LegacyKeywordResult;
import fastcampus.ad.legacy.application.keyword.LegacyKeywordService;
import fastcampus.ad.legacy.application.user.LegacyUserResult;
import fastcampus.ad.legacy.application.user.LegacyUserService;
import fastcampus.ad.legacy.domain.DomainEvent;
import fastcampus.ad.legacy.domain.adgroup.event.*;
import fastcampus.ad.legacy.domain.campaign.LegacyCampaign;
import fastcampus.ad.legacy.domain.campaign.event.*;
import fastcampus.ad.legacy.domain.keyword.LegacyKeyword;
import fastcampus.ad.legacy.domain.keyword.event.LegacyKeywordCreatedEvent;
import fastcampus.ad.legacy.domain.keyword.event.LegacyKeywordDeletedEvent;
import fastcampus.ad.legacy.domain.keyword.event.LegacyKeywordEvent;
import fastcampus.ad.legacy.domain.user.event.LegacyUserCreatedEvent;
import fastcampus.ad.legacy.domain.user.event.LegacyUserDeletedEvent;
import fastcampus.ad.legacy.domain.user.event.LegacyUserEvent;
import fastcampus.ad.legacy.domain.user.event.LegacyUserNameUpdatedEvent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import fastcampus.ad.legacy.application.user.LegacyUserCreateCommand;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
class LegacyDomainEventListenerTest {
    @Autowired
    LegacyUserService userService;
    @Autowired
    LegacyCampaignService campaignService;
    @Autowired
    LegacyAdGroupService adGroupService;
    @Autowired
    LegacyKeywordService keywordService;

    @MockBean
    LegacyDomainEventListener eventListener;

    @Test
    void userEvents(){
        LegacyUserResult result = userService.create(new LegacyUserCreateCommand("사용자"));
        userService.updateName(result.id(), "사용자2");
        userService.delete(result.id());
        assertAll(
        () -> verify(eventListener, times(3)).handleEvent(any(LegacyUserEvent.class)),
        () -> verify(eventListener, times(1)).handleEvent(any(LegacyUserNameUpdatedEvent.class)),
        () -> verify(eventListener, times(3)).handleEvent(any(DomainEvent.class)),
        () -> verify(eventListener, times(1)).handleEvent(any(LegacyUserDeletedEvent.class)),
        () -> verify(eventListener, times(1)).handleEvent(any(LegacyUserCreatedEvent.class))
                );
    }

    @Test
    void campaignEvents(){
        LegacyCampaignResult result = campaignService.create(new LegacyCampaignCreateCommand("campaign",1L,100L));
        campaignService.updateBudget(result.id(), 200L);
        campaignService.updateName(result.id(), "newCampaign");
        campaignService.delete(result.id());

        assertAll(
                () -> verify(eventListener, times(4)).handleEvent(any(DomainEvent.class)),
                () -> verify(eventListener, times(4)).handleEvent(any(LegacyCampaignEvent.class)),
                () -> verify(eventListener, times(1)).handleEvent(any(LegacyCampaignCreatedEvent.class)),
                () -> verify(eventListener, times(1)).handleEvent(any(LegacyCampaignNameUpdatedEvent.class)),
                () -> verify(eventListener, times(1)).handleEvent(any(LegacyCampaignBudgetUpdatedEvent.class)),
                () -> verify(eventListener, times(1)).handleEvent(any(LegacyCampaignDeletedEvent.class))
        );
    }

    @Test
    void adGroupsEvents(){
        LegacyAdGroupResult result = adGroupService.create(new LegacyAdGroupCreateCommand("adGroup",1L,100L,"http://www.fastcampus.com"));
        adGroupService.updateName(result.id(), "newAdGroup");
        adGroupService.updateLinkUrl(result.id(), "http://www.fast.com");
        adGroupService.delete(result.id());

        assertAll(
                () -> verify(eventListener, times(4)).handleEvent(any(DomainEvent.class)),
                () -> verify(eventListener, times(4)).handleEvent(any(LegacyAdGroupEvent.class)),
                () -> verify(eventListener, times(1)).handleEvent(any(LegacyAdGroupCreatedEvent.class)),
                () -> verify(eventListener, times(1)).handleEvent(any(LegacyAdGroupNameUpdatedEvent.class)),
                () -> verify(eventListener, times(1)).handleEvent(any(LegacyAdGroupLinkUrlUpdatedEvent.class)),
                () -> verify(eventListener, times(1)).handleEvent(any(LegacyAdGroupDeletedEvent.class))
        );
    }

    @Test
    void keywordEvents(){
        LegacyKeywordResult result = keywordService.create(new LegacyKeywordCreateCommand("keyword",1L,1L));
        keywordService.delete(result.id());

        assertAll(
                () -> verify(eventListener, times(2)).handleEvent(any(DomainEvent.class)),
                () -> verify(eventListener, times(2)).handleEvent(any(LegacyKeywordEvent.class)),
                () -> verify(eventListener, times(1)).handleEvent(any(LegacyKeywordCreatedEvent.class)),
                () -> verify(eventListener, times(1)).handleEvent(any(LegacyKeywordDeletedEvent.class))
        );
    }
}