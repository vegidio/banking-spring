import com.google.protobuf.gradle.id
import java.time.LocalDate
import java.time.format.DateTimeFormatter

plugins {
    alias(libs.plugins.detekt)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kotlin.jpa)
    alias(libs.plugins.kotlin.spring)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.protobuf.plugin)
    alias(libs.plugins.spring)
    alias(libs.plugins.spring.boot)
}

group = "io.vinicius.banking"
version = "1.0.0"

kotlin {
    jvmToolchain(21)
}

repositories {
    mavenCentral()
}

dependencies {
    // Needed for Nimbus EC Key Pair generation
    implementation(libs.bouncycastle.bcpkix)
    implementation(libs.bouncycastle.bcprov)

    implementation(libs.jackson.jaxb)
    implementation(libs.jackson.kotlin)
    implementation(libs.javax.xml)
    implementation(libs.kotlin.logging)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.stdlib)
    implementation(libs.spring.data)
    implementation(libs.spring.graphql)
    implementation(libs.spring.oauth2)
    implementation(libs.spring.validation)
    implementation(libs.spring.web)

    // OpenAPI
    implementation(libs.springdoc.kotlin)
    implementation(libs.springdoc.security)
    implementation(libs.springdoc.ui)

    // gRPC
    implementation(libs.grpc.kotlin)
    implementation(libs.grpc.protobuf)
    implementation(libs.protobuf.kotlin)
    implementation(libs.spring.grpc.client)

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

protobuf {
    protoc {
        artifact = libs.protobuf.protoc.get().toString()
    }
    plugins {
        create("grpc").artifact = libs.grpc.gen.java.get().toString()
        create("grpckt").artifact = libs.grpc.gen.kotlin.get().toString() + ":jdk8@jar"
    }
    generateProtoTasks {
        all().forEach {
            it.plugins {
                id("grpc")
                id("grpckt")
            }
            it.builtins {
                id("kotlin")
            }
        }
    }
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