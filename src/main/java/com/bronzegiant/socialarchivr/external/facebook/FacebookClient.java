package com.bronzegiant.socialarchivr.external.facebook;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;
import java.util.logging.Logger;

@Service
public class FacebookClient {
	
	private FacebookApiProperties props;

    private final WebClient webClient;
    
    private static final Logger LOGGER = Logger.getLogger(FacebookClient.class.getName());

    public FacebookClient(WebClient.Builder webClientBuilder, FacebookApiProperties fbProps) {
    	this.props = fbProps;
        this.webClient = webClientBuilder
                .baseUrl(props.getBaseUrl() + "/" + props.getServiceVersion())
                .build();
    }

    public FacebookPostResponse getRecentPosts(String accessToken, String pageId) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/{pageId}/posts")
                        .queryParam("limit", "50")
                        .queryParam("access_token", accessToken)
                        .build(pageId))
                .retrieve()
                .bodyToMono(FacebookPostResponse.class)
                .block(); // Synchronous call
    }
    
    public boolean isFacebookTokenValid(String userAccessToken) {
        String appAccessToken = props.getAppId() + "|" + props.getAppSecret();
        LOGGER.info("app serviceVersion= " + props.getServiceVersion());
        LOGGER.info("app baseUrl= " + props.getBaseUrl());
        LOGGER.info("app access token= " + appAccessToken);
        LOGGER.info("USER access token= " + userAccessToken);
        String url = String.format(
            "https://graph.facebook.com/debug_token?input_token=%s&access_token=%s",
            userAccessToken, appAccessToken
        );

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        Map<String, Object> data = (Map<String, Object>) response.get("data");
        LOGGER.info("response data=" + data);
        return data != null && Boolean.TRUE.equals(data.get("is_valid"));
    }
    
}
