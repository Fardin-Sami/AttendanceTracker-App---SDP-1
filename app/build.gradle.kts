plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.attendancetracker"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.attendancetracker"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // --- CORRECTION STARTS HERE ---

    // JUnit 5 (Jupiter) for Unit Testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.2")

    // For Android Instrumented Tests
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
}

tasks.withType<org.gradle.api.tasks.testing.Test> {
    useJUnitPlatform()
}