import Versions
import org.gradle.testing.jacoco.tasks.JacocoReport
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// ─────────────────────────────────────────────────────────────
// 📦 Repositorios
// ─────────────────────────────────────────────────────────────
repositories {
    mavenCentral()
    maven("https://repo1.maven.org/maven2/")
    maven("https://jitpack.io")
}

// ─────────────────────────────────────────────────────────────
// 📦 Plugins
// ─────────────────────────────────────────────────────────────
plugins {
    id("jacoco")
    id("checkstyle")
    id("org.springframework.boot") version Versions.springBoot
    id("io.spring.dependency-management") version Versions.dependencyManagement
    kotlin("jvm") version Versions.kotlin
    kotlin("plugin.spring") version Versions.kotlinSpring
}

// ─────────────────────────────────────────────────────────────
// 🏷️ Project metadata
// ─────────────────────────────────────────────────────────────
group = "com.marcoslozina.alertapp"
version = "1.0.0-RELEASE"

// ─────────────────────────────────────────────────────────────
// ⚙️ Java & Kotlin compatibility
// ─────────────────────────────────────────────────────────────
java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
    }
}

// ─────────────────────────────────────────────────────────────
// 📦 Dependency Management (Spring BOM)
// ─────────────────────────────────────────────────────────────
dependencyManagement {
    imports {
        mavenBom("org.springframework.boot:spring-boot-dependencies:${Versions.springBoot}")
    }
}

// ─────────────────────────────────────────────────────────────
// 📚 Dependencies
// ─────────────────────────────────────────────────────────────
dependencies {
    // 🌐 Core
    implementation(Dependencies.Spring.bootWebflux)
    implementation(Dependencies.Spring.bootSecurity)
    implementation(Dependencies.Spring.bootOauth2)

    // 🧬 Lombok
    compileOnly(Dependencies.Build.lombok)
    annotationProcessor(Dependencies.Build.lombok)
    testCompileOnly(Dependencies.Build.lombok)
    testAnnotationProcessor(Dependencies.Build.lombok)

    // 📊 Observabilidad, Docs y Validaciones
    implementation(Dependencies.Observability.micrometerCore)
    implementation(Dependencies.Observability.micrometerPrometheus)
    implementation(Dependencies.Observability.springBootActuator)
    implementation(Dependencies.OpenAPI.springdocWebflux)
    implementation(Dependencies.Validation.jakartaValidation)
    implementation(Dependencies.Validation.hibernateValidator)
    implementation(Dependencies.Validation.jakartaEl)

    // 📝 Logging
    implementation(Dependencies.Logging.logstashLogback)

    // 🧪 Testing
    testImplementation(Dependencies.Spring.bootTest)
    testImplementation(Dependencies.Test.junitApi)
    testImplementation(Dependencies.Test.junitEngine)
    testImplementation(Dependencies.Test.archunit)
    testImplementation(Dependencies.Test.reactorTest)
    testImplementation(Dependencies.Test.springSecurityTest)
    testImplementation(Dependencies.Test.mockWebServer)
    testImplementation(Dependencies.Test.jakartaServletApi)
}

// ─────────────────────────────────────────────────────────────
// ✅ Checkstyle Configuration
// ─────────────────────────────────────────────────────────────
checkstyle {
    toolVersion = Versions.checkstyleVersion
    configFile = file("src/main/java/com/marcoslozina/investalerts/config/checkstyle/checkstyle.xml")
}

// ─────────────────────────────────────────────────────────────
// 🧪 Test & Coverage
// ─────────────────────────────────────────────────────────────
tasks.test {
    useJUnitPlatform()
    finalizedBy("jacocoTestReport")
}

tasks.named<JacocoReport>("jacocoTestReport") {
    dependsOn(tasks.test)

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    classDirectories.setFrom(
        fileTree("build/classes/java/main") {
            exclude("**/config/**", "**/dto/**")
        }
    )
    sourceDirectories.setFrom(files("src/main/java"))
    executionData.setFrom(fileTree("build") {
        include("jacoco/test.exec")
    })
}

// ─────────────────────────────────────────────────────────────
// ⚙️ Spring Boot executable JAR
// ─────────────────────────────────────────────────────────────
tasks.named<Jar>("jar") {
    enabled = false
}

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = true
    archiveFileName.set("app.jar")
}
