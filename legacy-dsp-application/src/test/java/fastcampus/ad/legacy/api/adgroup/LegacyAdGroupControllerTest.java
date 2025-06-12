package fastcampus.ad.legacy.api.adgroup;

import fastcampus.ad.legacy.api.campaign.LegacyCampaignCreateRequest;
import fastcampus.ad.legacy.api.campaign.LegacyCampaignResp;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class LegacyAdGroupControllerTest {

    RestTemplate restTemplate = new RestTemplate();

    Random random = new Random();

    @Test
    void create_adgroup_by_thread() throws InterruptedException {
        try(ExecutorService executorService = Executors.newFixedThreadPool(1000)){
            AtomicInteger count = new AtomicInteger(0);
            int totalCount = 1000000;
            CountDownLatch latch = new CountDownLatch(totalCount);
            for(int i = 0; i < totalCount; i++){
                executorService.execute(() ->{
                    create_many_adgroup(count);
                    latch.countDown();
                });
            }
            latch.await();
        }
    }

    void create_many_adgroup(AtomicInteger count){
            try {
                String name = "adgroup" + (random.nextInt(1000) + 1);
                Long campaignId = random.nextInt(1000) + 1L;
                String linkUrl = "http://www.fast.com/" + name;
                var resp = restTemplate.postForEntity("http://localhost:8080/campaign/v1"
                        , new LegacyAdGroupCreateRequest(name, campaignId, linkUrl)
                        , LegacyAdGroupResp.class);
                if (resp.getStatusCode().isError()) {
                    System.out.println("error : " + resp.getStatusCode());
                }
                if (count.incrementAndGet() % 100 == 0) {
                    System.out.println("progress: " + count);
                }
            }catch (Exception e){
                create_many_adgroup(count);
            }
    }
}