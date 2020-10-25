plugins {
    id("java")
    id("org.jetbrains.kotlin.jvm") version "1.3.72"
    application
}

repositories {
    jcenter()
    mavenCentral()
    maven("https://dl.bintray.com/mipt-npm/scientifik")
}

dependencies {
    api("kscience.kmath:kmath-core:0.1.4")
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
    mainClassName = "Convolutional.neural.network.without.frameworks.in.kotlin.AppKt"
}


tasks {
    test {
        useJUnitPlatform()
    }
}
