package com.bronzegiant.socialarchivr.external.facebook;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@Service
@ConfigurationProperties(prefix = "facebook.api")
public class FacebookClient {
	
	private String serviceVersion ;
	private String baseUrl;
	private String appId;
	private String appSecret;

    private final WebClient webClient;


    public FacebookClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl(baseUrl + "/" + serviceVersion)
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
        String appAccessToken = appId + "|" + appSecret;
        String url = String.format(
            "https://graph.facebook.com/debug_token?input_token=%s&access_token=%s",
            userAccessToken, appAccessToken
        );

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        Map<String, Object> data = (Map<String, Object>) response.get("data");
        return data != null && Boolean.TRUE.equals(data.get("is_valid"));
    }


	public String getServiceVersion() {
		return serviceVersion;
	}

	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}

	public String getBaseUrl() {
		return baseUrl;
	}

	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
    
}
