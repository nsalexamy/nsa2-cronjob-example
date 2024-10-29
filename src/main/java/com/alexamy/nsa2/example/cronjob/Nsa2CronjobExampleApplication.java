package com.alexamy.nsa2.example.cronjob;

import com.alexamy.nsa2.example.cronjob.component.GetUsersJob;
import com.alexamy.nsa2.example.cronjob.component.HelloWorldJob;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.context.Scope;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class Nsa2CronjobExampleApplication implements CommandLineRunner {
    private final HelloWorldJob helloWorldJob;
    private final GetUsersJob getUsersJob;
    @Qualifier("helloWorldJobSpanBuilder")
    private final SpanBuilder helloWorldJobSpanBuilder;
    @Qualifier("getUsersJobSpanBuilder")
    private final SpanBuilder getUsersJobSpanBuilder;

//    private final ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(Nsa2CronjobExampleApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        log.info("===> run");

        Span span = helloWorldJobSpanBuilder.startSpan();
//        @Nullable
//        ExitCodeGenerator exitCodeGenerator = null;

        log.info("===> run - span: {}", span);

        try (Scope scope = span.makeCurrent()) {
            helloWorldJob.execute();
//            getUsersJob.execute();
//            exitCodeGenerator = () -> 0;
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
//            exitCodeGenerator = () -> 1;
        } finally {
            span.end();
//            final int exitCode = SpringApplication.exit(applicationContext, exitCodeGenerator);

//            System.exit(exitCode);
        }

//        getUsersJob.execute();

        Span span2 = getUsersJobSpanBuilder.startSpan();
        try (Scope scope = span2.makeCurrent()) {
            getUsersJob.execute();
        } catch(Exception ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            span2.end();
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
