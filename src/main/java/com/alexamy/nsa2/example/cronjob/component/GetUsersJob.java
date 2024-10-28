package com.alexamy.nsa2.example.cronjob.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;


@Component
@Slf4j
public class GetUsersJob {
    private final RestClient restClient;

    public GetUsersJob(@Qualifier("securityAdminRestClient") RestClient restClient) {


        this.restClient = restClient;
    }

    public void execute() {
        try {
            List<User> users = restClient.get()
                    .uri("/users")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<User>>() {
                    });


//            log.info("=====> users: {}", users);

            users.forEach(user -> {
                log.info("=====> user: {}", user);
            });

        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }
}
