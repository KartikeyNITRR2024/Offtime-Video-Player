package com.offtime.videoplayer.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.offtime.videoplayer.utils.AllMicroservicesData;

import java.util.Map;

@Component
public class StatusCheckerScheduler {
	
	private static final Logger logger = LoggerFactory.getLogger(StatusCheckerScheduler.class);
	
	@Autowired
	private AllMicroservicesData allMicroservicesData;
	
	private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 30000)
    public void checkStatus() throws InterruptedException {
//    	while(allMicroservicesData.current == null)
//    	{
//    		Thread.sleep(200);
//    	}
    	String domain = allMicroservicesData.current.getType() == 1 ? allMicroservicesData.current.getMicroserviceUrl() : allMicroservicesData.current.getMicroserviceLocalUrl();
    	String url = String.format("%sapi/statuschecker/%d", domain, allMicroservicesData.current.getMicroserviceUniqueId());
        try {
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
            Boolean status = null;
            if (response != null) {
                status = (Boolean) response.get("data");
            } 
            logger.info("Status checker response: {}", status);
        } catch (Exception e) {
        	logger.info("Error calling API: " + e.getMessage());
        }
    }
}
