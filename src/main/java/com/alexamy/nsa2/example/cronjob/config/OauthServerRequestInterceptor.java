package com.alexamy.nsa2.example.cronjob.config;

import jakarta.annotation.Nullable;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
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
public class OauthServerRequestInterceptor implements ClientHttpRequestInterceptor {

    private final OAuth2AccessTokenResponseClient<OAuth2ClientCredentialsGrantRequest> accessTokenResponseClient;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private OAuth2AccessToken accessToken;

    @Value("${NSA2_OAUTH_CLIENT_REGISTRATION_ID:${NSA2_OAUTH_CLIENT_ID:nsa2-cronjob-example}}")
    private String clientRegistrationId;

    public OauthServerRequestInterceptor(
            OAuth2AccessTokenResponseClient<OAuth2ClientCredentialsGrantRequest> accessTokenResponseClient,
            ClientRegistrationRepository clientRegistrationRepository) {
        this.accessTokenResponseClient = accessTokenResponseClient;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @PostConstruct
    void validateBean() {
        log.info("===> clientRegistrationRepository class: {}", clientRegistrationRepository.getClass());
    }


    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        log.debug("========> httpRequest class: {}", request.getClass());

        final OAuth2AccessToken accessToken = getAccessToken();

        if(accessToken == null) {
            log.error("=====> accessToken is null");
            return execution.execute(request, body);
        }

        log.debug("token value: {}", accessToken.getTokenValue());
        request.getHeaders().setBearerAuth(accessToken.getTokenValue());

        return execution.execute(request, body);
    }

    private @Nullable OAuth2AccessToken getAccessToken() {
        if(! isValid(accessToken)) {
            resetAccessToken();
        }
        return accessToken;
    }

    private boolean isValid(@Nullable OAuth2AccessToken token) {
        return token != null && token.getExpiresAt() != null
                && token.getExpiresAt().isBefore(Instant.now());
    }

    private void resetAccessToken() {
        this.accessToken = null;

        ClientRegistration clientRegistration =
                this.clientRegistrationRepository.findByRegistrationId(clientRegistrationId);

        log.debug("===> clientRegistration: {}", clientRegistration);

        OAuth2ClientCredentialsGrantRequest grantRequest =
                new OAuth2ClientCredentialsGrantRequest(clientRegistration);

        OAuth2AccessTokenResponse tokenResponse = accessTokenResponseClient.getTokenResponse(grantRequest);

        OAuth2AccessToken accessToken = tokenResponse.getAccessToken();
        log.debug("===> accessToken: {}", accessToken);

        this.accessToken = accessToken;
    }
}
