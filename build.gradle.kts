plugins {
    kotlin("multiplatform") version "1.8.21"
    kotlin("plugin.serialization") version "1.8.21"
}

group = "s.knyazev"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
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
            }
        }
        val jvmTest by getting
        val jsMain by getting
        val jsTest by getting
    }
}
