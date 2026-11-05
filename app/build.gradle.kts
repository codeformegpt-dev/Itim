plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.itim.app"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.itim.app"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments["room.schemaLocation"] = "$projectDir/schemas"
            }
        }
    }

    buildTypes {
        release {
            optimization {
                enable = true
                packageScope = setOf("androidx.**", "kotlin.**", "kotlinx.**")
            }
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

val roomVersion = "2.8.5"

dependencies {
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.1.5")
    implementation(libs.activity.ktx)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)
    implementation(libs.room.common)
    implementation(libs.room.runtime)

    implementation("androidx.room:room-guava:$roomVersion")
    implementation("com.google.guava:guava:33.8.0-android")

    testImplementation(libs.junit)
    testImplementation(libs.robolectric)
    testImplementation("androidx.test:core:1.7.0")
    testImplementation("androidx.arch.core:core-testing:2.2.0")

    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.ext.junit)
    annotationProcessor(libs.room.compiler)
}