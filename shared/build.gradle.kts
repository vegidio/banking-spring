import com.google.protobuf.gradle.id

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.protobuf.plugin)
    alias(libs.plugins.spring)
    alias(libs.plugins.spring.boot)
}

group = "io.vinicius.banking.shared"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.spring.oauth2)

    // gRPC
    api(libs.grpc.kotlin)
    api(libs.grpc.protobuf)
    api(libs.protobuf.kotlin)

    testImplementation(kotlin("test"))
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

kotlin {
    jvmToolchain(21)
}