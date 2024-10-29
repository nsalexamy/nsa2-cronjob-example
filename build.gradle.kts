//import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    java
//    id("org.springframework.boot") version "3.3.4"
    id("org.springframework.boot") version "3.4.0-RC1"
    id("io.spring.dependency-management") version "1.1.6"
}


group = "com.alexamy.nsa2"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
}

dependencyManagement {
    imports {
        mavenBom("io.opentelemetry.instrumentation:opentelemetry-instrumentation-bom:2.9.0")
    }
}


dependencies {
    implementation("io.opentelemetry.instrumentation:opentelemetry-spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter")
//    implementation("org.springframework.boot:spring-boot-starter-oauth2-client:3.4.0-RC1")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
//    implementation("org.springframework.security:spring-security-config:6.4.0-RC1")
//    implementation("org.springframework.security:spring-security-core:6.4.0-RC1")
//    implementation("org.springframework.security:spring-security-oauth2-client:6.4.0-RC1")
//    implementation("org.springframework.security:spring-security-oauth2-jose:6.4.0-RC1")

//    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework:spring-web")
//    implementation("org.springframework.boot:spring-boot-starter-actuator")
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

//    implementation("org.springframework.boot:spring-boot-starter-jdbc")
//    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

//tasks.named<BootRun>("bootRun") {
//    mainClass.set("com.alexamy.nsa2.example.cronjob.Nsa2CronjobExampleApplication")
//    jvmArgs = listOf("-Dotel.java.global-autoconfigure.enabled=true")
//}