plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.3.72"
    id("io.github.0ffz.github-packages") version "1.2.1"
    application
}
buildscript {
    repositories {
        mavenCentral()
        githubPackage("kyonifer")
        maven { url =uri("https://jitpack.io")  }
        maven { url = uri("https://plugins.gradle.org/m2/") }
    }
}

dependencies {
    implementation("com.kyonifer:koma-core-ejml:0.12.1")
    implementation("org.openpnp:opencv:4.3.0-2")
    // Align versions of all Kotlin components
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")

    testCompileOnly("junit:junit:4.12")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.3.1")
    testImplementation("io.mockk:mockk:1.10.2")
    testImplementation("org.assertj:assertj-core:3.17.2")
}

application {
    mainClass.set("com.dagdoni.millad.deeplearning.App")
}


tasks {
    test {
        useJUnitPlatform()
    }
}
