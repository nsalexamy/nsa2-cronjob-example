package com.alexamy.nsa2.example.cronjob.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;

@Configuration(proxyBeanMethods = false)
public class OtelConfig {

    @Bean
    public Tracer tracer() {
        return GlobalOpenTelemetry
            .getTracerProvider()
            .tracerBuilder("nsa2-cronjob-example-tracer")
            .build();
    }

    // @Bean("helloWorldJobSpan")
    // public Span helloWorldJobSpan(Tracer tracer) {
    //     return tracer
    //         .spanBuilder("hello-world-job")
    //         .setSpanKind(SpanKind.INTERNAL)
    //         .startSpan();
    // }   

    @Bean
    public SpanBuilder helloWorldJoSpanBuilder(Tracer tracer) {
        return tracer
            .spanBuilder("hello-world-job")
            .setSpanKind(SpanKind.INTERNAL);
    }

}
