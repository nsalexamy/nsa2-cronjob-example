package com.alexamy.nsa2.example.cronjob;

import com.alexamy.nsa2.example.cronjob.component.HelloWorldJob;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
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
    private final SpanBuilder helloWorldJoSpanBuilder;

    public static void main(String[] args) {
        SpringApplication.run(Nsa2CronjobExampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
         
        Span span = helloWorldJoSpanBuilder.startSpan();
        try (Scope scope = span.makeCurrent()) {
            helloWorldJob.execute();
        } finally {
            span.end();
        }
    }

    // @Override
    // public void run(String... args) throws Exception {
    //     Tracer tracer = GlobalOpenTelemetry
    //         .getTracerProvider()
    //         .tracerBuilder("nsa2-cronjob-example-tracer")
    //         .build();

    //     Span span = tracer
    //         .spanBuilder("hello-world-job")
    //         .setSpanKind(SpanKind.INTERNAL)
    //         .startSpan();    
        
    //     try (Scope scope = span.makeCurrent()) {
    //         helloWorldJob.execute();
    //     } finally {
    //         span.end();
    //     }
    // }
}
