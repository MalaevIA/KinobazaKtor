plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.0"
}

group = "com.example"
version = "0.0.1"

application {
    mainClass = "com.example.ApplicationKt"

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.cio)

    implementation("org.jetbrains.exposed:exposed-core:0.41.1")  // Exposed core
    implementation("org.jetbrains.exposed:exposed-dao:0.41.1")   // Exposed DAO
    implementation("org.jetbrains.exposed:exposed-jdbc:0.41.1")  // Exposed JDBC
    implementation("org.postgresql:postgresql:42.7.3")
    implementation("com.h2database:h2:2.2.224")


    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.4")
    implementation(libs.logback.classic)
    testImplementation(libs.ktor.server.test.host)
    testImplementation(libs.kotlin.test.junit)
}
