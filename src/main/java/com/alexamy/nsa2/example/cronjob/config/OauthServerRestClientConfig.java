package com.alexamy.nsa2.example.cronjob.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2ClientCredentialsGrantRequest;
import org.springframework.security.oauth2.client.endpoint.RestClientClientCredentialsTokenResponseClient;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.web.client.RestClient;

@Configuration(proxyBeanMethods = false)
public class OauthServerRestClientConfig {
    public static final String OAUTH_SERVER_REST_CLIENT = "oauthServerRestClient";

    @Value("${spring.security.oauth2.client.provider.spring.issuer-uri}")
    private String issuerUri;

    /**
     * Create a RestClient bean for the OAuth server
     * @return RestClient
     *
     * @see org.springframework.security.oauth2.client.endpoint.AbstractRestClientOAuth2AccessTokenResponseClient
     */
    @Bean(OAUTH_SERVER_REST_CLIENT)
    public RestClient oauthServerRestClient() {
        return RestClient.builder()
                .baseUrl(issuerUri)
                .messageConverters((messageConverters) -> {
                    messageConverters.clear();
                    messageConverters.add(new FormHttpMessageConverter());
                    messageConverters.add(new OAuth2AccessTokenResponseHttpMessageConverter());
                })
                .defaultStatusHandler(new OAuth2ErrorResponseErrorHandler())
                .build();
    }

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2ClientCredentialsGrantRequest> accessTokenResponseClient(
            @Qualifier(OAUTH_SERVER_REST_CLIENT) RestClient oauthServerRestClient) {

       var tokenResponseClient = new RestClientClientCredentialsTokenResponseClient();

       tokenResponseClient.setRestClient(oauthServerRestClient);

       return tokenResponseClient;
    }
}
