package com.alexamy.nsa2.example.cronjob.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientPropertiesMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2ClientCredentialsGrantRequest;
import org.springframework.security.oauth2.client.endpoint.RestClientClientCredentialsTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;
import java.util.*;
import java.util.function.Function;

@Configuration(proxyBeanMethods = false)
//@EnableWebSecurity
@EnableConfigurationProperties(OAuth2ClientProperties.class)
@Slf4j
public class Oauth2ClientConfig {
    @Value("${app.auth.post-logout-redirect}")
    private String postLogoutRedirect;

    @Value("${SESSION_COOKIE_NAME:JSESSIONID}")
    private String sessionCookieName;

    @Bean
    JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    // @format-off
//    @Bean
//    public SecurityFilterChain oauth2ClientSecurityFilterChain(HttpSecurity http) throws Exception {
//
//        http
//                .cors(cors->cors.disable())
//                .csrf(csrf->csrf.disable())
//                .authorizeHttpRequests(authorize ->
//                    authorize.requestMatchers("/user", "/user/test").permitAll()
//                            .anyRequest().authenticated()
//                )
//                .oauth2Client(Customizer.withDefaults())
//        ;
//
//        return http.build();
//    }
    // @format-on

//    @Bean
//    public OAuth2AccessTokenResponseClient<OAuth2ClientCredentialsGrantRequest> accessTokenResponseClient() {
//        var accessTokenResponseClient = new RestClientClientCredentialsTokenResponseClient();
//
//        accessTokenResponseClient.addHeadersConverter((grantRequest) -> {
//            ClientRegistration clientRegistration = grantRequest.getClientRegistration();
//            HttpHeaders headers = new HttpHeaders();
//            if(clientRegistration.getRegistrationId().equals("nsa2-cronjob-example")) {
//                headers.set(HttpHeaders.USER_AGENT, "my-user-agent");
//            }
//            return headers;
//        });
//
//        return accessTokenResponseClient;
//    }


    //  org.springframework.boot.autoconfigure.security.oauth2.client.servlet.OAuth2ClientRegistrationRepositoryConfiguration
    @Bean
    @ConditionalOnMissingBean(ClientRegistrationRepository.class)
    InMemoryClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties properties) {
        List<ClientRegistration> registrations = new ArrayList<>(
                new OAuth2ClientPropertiesMapper(properties).asClientRegistrations().values());
        return new InMemoryClientRegistrationRepository(registrations);
    }

//    @Bean OAuth2AuthorizedClientManager authorizedClientManager(
//            ClientRegistrationRepository clientRegistrationRepository,
//            OAuth2AuthorizedClientRepository authorizedClientRepository
//    ) {
//        log.info("=====> clientRegistrationRepository:{}, authorizedClientRepository:{} ",
//                clientRegistrationRepository,
//                authorizedClientRepository);
//
//        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("nsa2-cronjob-example");
//        log.info("clientRegistration: {}", clientRegistration);
//
//        OAuth2AuthorizedClientProvider authorizedClientProvider =
//                OAuth2AuthorizedClientProviderBuilder.builder()
//                        .clientCredentials()
////                        .password(configurer -> Customizer.withDefaults())
//                        .build();
//
//        DefaultOAuth2AuthorizedClientManager authorizedClientManager =
//                new DefaultOAuth2AuthorizedClientManager(clientRegistrationRepository, authorizedClientRepository);
//
//        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
//        authorizedClientManager.setContextAttributesMapper(contextAttributesMapper());
//
//
//        return authorizedClientManager;
//    }
//
//    private Function<OAuth2AuthorizeRequest, Map<String, Object>> contextAttributesMapper() {
//        return authorizeRequest -> {
//            Map<String, Object> contextAttributes = new HashMap<>();
//
//            contextAttributes.put(OAuth2AuthorizationContext.USERNAME_ATTRIBUTE_NAME, "nsa2admin");
//            contextAttributes.put(OAuth2AuthorizationContext.PASSWORD_ATTRIBUTE_NAME, "password");
//            return contextAttributes;
//        };
//    }

}
