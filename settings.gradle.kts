plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "banking-spring"
include("shared")
include("api-gateway")
include("transactions")
include("fraud-detection")
