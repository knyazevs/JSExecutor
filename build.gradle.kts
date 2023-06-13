import java.net.URI

plugins {
    kotlin("multiplatform") version "1.8.21"
    kotlin("plugin.serialization") version "1.8.21"
    `maven-publish`
}

group = "s.knyazev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = URI("https://maven.pkg.github.com/octocat/hello-world")
            credentials {
                username = System.getenv("GITHUB_ACTOR")
                password = System.getenv("GITHUB_TOKEN")
            }
        }
    }
}

kotlin {
    jvm {
        jvmToolchain(11)
        withJava()
        testRuns["test"].executionTask.configure {
            useJUnitPlatform()
        }
    }
    js(IR) {
        useCommonJs()
        generateTypeScriptDefinitions()
        binaries.library()
        binaries.executable()
        browser {
            testTask {
                useKarma() {
                    useChrome()
                    //useFirefox()
                    //useSafari()
                }
            }
        }
    }


    sourceSets {
        val commonMain by getting
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation("org.graalvm.js:js:22.0.0")
                implementation("org.graalvm.js:js-scriptengine:22.0.0")
                implementation("com.google.code.gson:gson:2.10.1")
                implementation("org.mozilla:rhino:1.7.14")
            }
        }
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
    }
}

tasks.named("jsBrowserProductionLibraryPrepare").configure { dependsOn("jsProductionExecutableCompileSync") }
tasks.named("jsBrowserProductionWebpack").configure { dependsOn("jsProductionLibraryCompileSync") }
