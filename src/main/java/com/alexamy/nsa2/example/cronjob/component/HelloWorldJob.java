package com.alexamy.nsa2.example.cronjob.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class HelloWorldJob {
    public void execute() {
        log.info("Hello World! - executed at {}", new Date().toString());
    }
}
