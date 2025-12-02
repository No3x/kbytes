plugins {
    kotlin("jvm") version "2.2.0"
    `java-library`
    `maven-publish`
}

group = "de.no3x"
version = "1.0.0"

dependencies {
    val junitVersion = "5.14.1"
    implementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("com.github.No3x:junit-locale-extension:1.0.0")
    testImplementation("com.willowtreeapps.assertk:assertk:0.28.1")
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-engine")
    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter-params")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

java {
    withSourcesJar()
    withJavadocJar()
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifactId = "kbytes"

            pom {
                name.set("kbytes")
                description.set("Small Kotlin DSL for byte and file size calculations with units.")
                url.set("https://github.com/No3x/kbytes")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                scm {
                    url.set("https://github.com/No3x/kbytes")
                    connection.set("scm:git:https://github.com/No3x/kbytes.git")
                    developerConnection.set("scm:git:ssh://git@github.com/No3x/kbytes.git")
                }
            }
        }
    }
}

kotlin {
    jvmToolchain(21)
}
