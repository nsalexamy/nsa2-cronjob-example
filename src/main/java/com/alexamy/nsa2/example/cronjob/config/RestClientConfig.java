package com.alexamy.nsa2.example.cronjob.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestClient;

@Configuration(proxyBeanMethods = false)
@Slf4j
//@AllArgsConstructor
public class RestClientConfig { // extends RestClientBuilderConfigurer {



    private final ClientHttpRequestInterceptor requestInterceptor;
//    private final OAuth2AuthorizedClientManager auth2AuthorizedClientManager;

    public RestClientConfig(ClientHttpRequestInterceptor requestInterceptor) {
        this.requestInterceptor = requestInterceptor;
//        this.auth2AuthorizedClientManager = auth2AuthorizedClientManager;
    }

//    @Bean
//    RestClient.Builder authServerRestClientBuilder() {
//        return RestClient.builder();
//    }



//    @Override
//    public RestClient.Builder configure(RestClient.Builder builder) {
//        return
//                super.configure(builder)
////                        .requestInterceptor(requestInterceptor)
////                        .requestInterceptor(buildInterceptor())
//                ;
//    }

    @Bean
    public RestClient.Builder securityAdminRestClientBuilder() {
        return RestClient.builder().requestInterceptor(requestInterceptor);
    }

    @Bean
    RestClient securityAdminRestClient(RestClient.Builder builder, HttpMessageConverters messageConverters) {

        RestClient restClient = builder.baseUrl("http://nsa2-securityadmin:8084")
//                .messageConverters((httpMessageConverters -> {
//                    var converters = messageConverters.getConverters();
//                    converters.add(new FormHttpMessageConverter());
//                    converters.add(new OAuth2AccessTokenResponseHttpMessageConverter()) ;
//                }))
                .build();

//        RestClientClientCredentialsTokenResponseClient accessTokenResponseClient =
//                new RestClientClientCredentialsTokenResponseClient();
//        accessTokenResponseClient.setRestClient(restClient);

        return restClient;

    }



}
