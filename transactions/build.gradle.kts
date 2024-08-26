import java.time.LocalDate
import java.time.format.DateTimeFormatter

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.spring)
    alias(libs.plugins.spring.boot)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ktlint)
}

group = "io.vinicius.banking.transactions"
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
    implementation(libs.jackson.jaxb)
    implementation(libs.jackson.kotlin)
    implementation(libs.javax.xml)
    implementation(libs.kotlin.logging)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib)
    implementation(libs.spring.data)
    implementation(libs.spring.grpc.server)
    implementation(libs.spring.oauth2)
    implementation(libs.spring.validation)
    implementation(libs.spring.web)

    annotationProcessor(libs.spring.configuration)

    developmentOnly(libs.spring.devtools)

    runtimeOnly(libs.postgres)

    testImplementation(libs.spring.test)
    testImplementation(libs.spring.webflux)
}

tasks.test {
    useJUnitPlatform()
}

tasks.register<Exec>("docker") {
    val calver = LocalDate.now().format(DateTimeFormatter.ofPattern("uu.M.d"))
    workingDir(".")
    executable("docker")
    args("build", "-t", "vegidio/banking-transactions", ".", "--build-arg", "VERSION=$calver")
}