import org.springframework.boot.gradle.tasks.run.BootRun

// Dependency versions
val lombokVersion = "1.18.36"

plugins {
    java
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
}

group = "com.borowa5b"
version = "1.0.0"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    // SPRING
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-web")

    // SWAGGER DOCUMENTATION
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")

    // LOMBOK
    compileOnly("org.projectlombok:lombok:${lombokVersion}")
    annotationProcessor("org.projectlombok:lombok:${lombokVersion}")

    // EXCEPTIONS HANDLING
    implementation("org.zalando:problem:0.27.1")
    implementation("org.zalando:jackson-datatype-problem:0.27.1")

    // TESTS
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// Runs application with 'local' profile on dev machine (uses application-local.properties).
tasks.named<BootRun>("bootRun") {
    if (System.getProperty("spring.profiles.active") == null) {
        systemProperty("spring.profiles.active", "local")
    }
}