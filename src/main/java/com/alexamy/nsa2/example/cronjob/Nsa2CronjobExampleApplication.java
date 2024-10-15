package com.alexamy.nsa2.example.cronjob;

import com.alexamy.nsa2.example.cronjob.component.HelloWorldJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class Nsa2CronjobExampleApplication implements CommandLineRunner {
    private final HelloWorldJob helloWorldJob;

    public static void main(String[] args) {
        SpringApplication.run(Nsa2CronjobExampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        helloWorldJob.execute();
    }
}
