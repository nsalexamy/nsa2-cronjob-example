package com.alexamy.nsa2.example.cronjob.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class SecurityAdminRestClientConfig {

    public static final String SECURITY_ADMIN_REST_CLIENT_BEAN = "securityAdminRestClient";
    public static final String SECURITY_ADMIN_REST_CLIENT_BUILDER = "securityAdminRestClientBuilder";

    private final ClientHttpRequestInterceptor requestInterceptor;

    @Value("${app.services.security-admin.url}")
    private String securityAdminUrl;

    public SecurityAdminRestClientConfig(ClientHttpRequestInterceptor requestInterceptor) {
        this.requestInterceptor = requestInterceptor;
    }

    @Bean(SECURITY_ADMIN_REST_CLIENT_BUILDER)
    public RestClient.Builder securityAdminRestClientBuilder() {
        return RestClient.builder().requestInterceptor(requestInterceptor);
    }

    @Bean(SECURITY_ADMIN_REST_CLIENT_BEAN)
    RestClient securityAdminRestClient(@Qualifier(SECURITY_ADMIN_REST_CLIENT_BUILDER)
                                       RestClient.Builder builder) {

        RestClient restClient = builder.baseUrl(securityAdminUrl)
                .build();

        return restClient;
    }



}
