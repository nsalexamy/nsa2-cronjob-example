package com.alexamy.nsa2.example.cronjob;

import com.alexamy.nsa2.example.cronjob.component.GetUsersJob;
import com.alexamy.nsa2.example.cronjob.component.HelloWorldJob;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class Nsa2CronjobExampleApplication implements CommandLineRunner {
    private final HelloWorldJob helloWorldJob;
    private final GetUsersJob getUsersJob;
    private final SpanBuilder helloWorldJoSpanBuilder;

    private final ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(Nsa2CronjobExampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        log.info("===> run");

        Span span = helloWorldJoSpanBuilder.startSpan();
        @Nullable
        ExitCodeGenerator exitCodeGenerator = null;

        try (Scope scope = span.makeCurrent()) {
//            helloWorldJob.execute();
            getUsersJob.execute();
            exitCodeGenerator = () -> 0;
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
            exitCodeGenerator = () -> 1;
        } finally {
            span.end();
            final int exitCode = SpringApplication.exit(applicationContext, exitCodeGenerator);

            System.exit(exitCode);
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
