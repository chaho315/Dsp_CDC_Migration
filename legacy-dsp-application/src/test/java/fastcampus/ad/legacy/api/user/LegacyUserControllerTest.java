package fastcampus.ad.legacy.api.user;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class LegacyUserControllerTest {

    RestTemplate restTemplate = new RestTemplate();

    Random random = new Random();

    @Test
    void create_many_user(){
        for(int i = 0; i < 1000; i++){
            String name = "user" + (random.nextInt(1000) + 1);
            var resp = restTemplate.postForEntity("http://localhost:8080/user/v1", new LegacyUserCreateRequest(name), LegacyUserResp.class);
            if(resp.getStatusCode().isError()){
                System.out.println("error : "+resp.getStatusCode());
            }
            if(i%100 == 0){
                System.out.println("progress: "+i);
            }
        }
    }

}