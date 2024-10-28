package com.alexamy.nsa2.example.cronjob.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.endpoint.RestClientClientCredentialsTokenResponseClient;
import org.springframework.web.client.RestClient;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class MyRestClientConfig {

//    @Bean RestClient restClient(RestClient.Builder builder) {
//        RestClientClientCredentialsTokenResponseClient client =
//                new RestClientClientCredentialsTokenResponseClient();
//
//
//        return new RestClient();
//    }
}
