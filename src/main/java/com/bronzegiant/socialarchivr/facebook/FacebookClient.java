package com.bronzegiant.socialarchivr.facebook;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Service
@ConfigurationProperties(prefix = "facebook.api")
public class FacebookClient {
	
	private String serviceVersion ;
	private String baseUrl;

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
