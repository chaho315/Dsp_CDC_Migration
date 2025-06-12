package fastcampus.ad.legacy.api.keyword;


import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class LegacyKeywordControllerTest {
    RestTemplate restTemplate = new RestTemplate();

    Random random = new Random();

    @Test
    void create_by_step() throws InterruptedException {
        for (int i=0; i < 20; i++){
            create_keyword_by_thread(i);
        }
    }

    void create_keyword_by_thread(int step) throws InterruptedException {
        try(ExecutorService executorService = Executors.newFixedThreadPool(1000)){
            AtomicInteger count = new AtomicInteger(0);
            int totalCount = 50000000;
            CountDownLatch latch = new CountDownLatch(totalCount);
            for(int i = 0; i < totalCount; i++){
                executorService.execute(() ->{
                    create_many_keyword(count, step);
                    latch.countDown();
                });
            }
            latch.await();
        }
    }

    void create_many_keyword(AtomicInteger count, int step) {
        try {
            String text = "keyword" + (random.nextInt(1000) + 1);
            Long adGroupId = random.nextInt(1000) + 1L;
            String linkUrl = "http://www.fast.com/" + text;
            var resp = restTemplate.postForEntity("http://localhost:8080/campaign/v1"
                    , new LegacyKeywordCreateRequest(text, adGroupId)
                    , LegacyKeywordResp.class);
            if (resp.getStatusCode().isError()) {
                System.out.println("error : " + resp.getStatusCode());
            }
            if (count.incrementAndGet() % 100 == 0) {
                System.out.println("["+step+"] progress: " + count + "at " + LocalDateTime.now());
            }
        }catch (Exception e){
            create_many_keyword(count, step);
        }
    }
}