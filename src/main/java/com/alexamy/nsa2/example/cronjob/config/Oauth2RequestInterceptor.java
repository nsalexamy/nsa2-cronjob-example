package com.alexamy.nsa2.example.cronjob.config;

import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2ClientCredentialsGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Instant;

@Component
//@AllArgsConstructor
@Slf4j
public class Oauth2RequestInterceptor implements ClientHttpRequestInterceptor {

//    private final RestClient oauthServerRestClient;
    private final OAuth2AccessTokenResponseClient<OAuth2ClientCredentialsGrantRequest> accessTokenResponseClient;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private OAuth2AccessToken accessToken;

    public Oauth2RequestInterceptor(
//            @Qualifier(OAuthServerRestClientConfig.OAUTH_SERVER_REST_CLIENT) RestClient oauthServerRestClient,
            OAuth2AccessTokenResponseClient<OAuth2ClientCredentialsGrantRequest> accessTokenResponseClient,
            ClientRegistrationRepository clientRegistrationRepository) {
//        this.oauthServerRestClient = oauthServerRestClient;
        this.accessTokenResponseClient = accessTokenResponseClient;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @PostConstruct
    void validateBean() {
//        log.info("===> oauthServerRestClient: {}", oauthServerRestClient);
        log.info("===> clientRegistrationRepository class: {}", clientRegistrationRepository.getClass());
    }


    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        log.info("========> httpRequest class: {}", request.getClass());

        final OAuth2AccessToken accessToken = getAccessToken();

        if(accessToken == null) {
            log.error("=====> accessToken is null");
            return execution.execute(request, body);
        }

        log.info("token value: {}", accessToken.getTokenValue());
        request.getHeaders().setBearerAuth(accessToken.getTokenValue());

        return execution.execute(request, body);
    }

    private @Nullable OAuth2AccessToken getAccessToken() {
        if(! isTokenValid(accessToken)) {
            resetAccessToken();
        }
        return accessToken;
    }

    private static boolean isTokenValid(@Nullable OAuth2AccessToken token) {
        return token != null && token.getExpiresAt() != null
                && token.getExpiresAt().isBefore(Instant.now());
    }

    private void resetAccessToken() {
        this.accessToken = null;

        ClientRegistration clientRegistration = this.clientRegistrationRepository.findByRegistrationId("nsa2-cronjob-example");

        log.info("===> clientRegistration: {}", clientRegistration);

        OAuth2ClientCredentialsGrantRequest grantRequest =
                new OAuth2ClientCredentialsGrantRequest(clientRegistration);

        OAuth2AccessTokenResponse tokenResponse = accessTokenResponseClient.getTokenResponse(grantRequest);

        OAuth2AccessToken accessToken = tokenResponse.getAccessToken();
        log.info("===> accessToken: {}", accessToken);

        this.accessToken = accessToken;

    }
}
