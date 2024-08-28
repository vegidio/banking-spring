import java.time.LocalDate
import java.time.format.DateTimeFormatter

plugins {
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.spring)
    alias(libs.plugins.spring.boot)
}

group = "io.vinicius.banking.api"
version = "1.0.0"

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(":shared"))

    // Needed for Nimbus EC Key Pair generation
    implementation(libs.bouncycastle.bcpkix)
    implementation(libs.bouncycastle.bcprov)

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.reactor)
    implementation(libs.jackson.jaxb)
    implementation(libs.jackson.kotlin)
    implementation(libs.javax.xml)
    implementation(libs.kotlin.logging)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib)
    implementation(libs.spring.data)
    implementation(libs.spring.graphql)
    implementation(libs.spring.grpc.client)
    implementation(libs.spring.oauth2)
    implementation(libs.spring.validation)
    implementation(libs.spring.web)

    // OpenAPI
    implementation(libs.springdoc.common)
    implementation(libs.springdoc.web)

    annotationProcessor(libs.spring.configuration)

    developmentOnly(libs.spring.devtools)

    runtimeOnly(libs.postgres)

    testImplementation(libs.spring.graphql.test)
    testImplementation(libs.spring.test)
    testImplementation(libs.spring.webflux)
}

// Detekt
detekt {
    config.setFrom("$rootDir/../detekt.yml")
    source.setFrom("$rootDir/src/main/kotlin")
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Exec>("docker") {
    val calver = LocalDate.now().format(DateTimeFormatter.ofPattern("uu.M.d"))
    workingDir(".")
    executable("docker")
    args("build", "-t", "vegidio/banking-api-gateway", ".", "--build-arg", "VERSION=$calver")
}