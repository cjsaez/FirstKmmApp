plugins {
    id("com.android.application")
    kotlin("android")
}

android {
    namespace = "com.example.firstkmmapp.android"
    compileSdk = 33
    defaultConfig {
        applicationId = "com.example.firstkmmapp.android"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.4"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.4.0")
    implementation("androidx.compose.ui:ui-tooling:1.4.0")
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.0")
    implementation("androidx.compose.foundation:foundation:1.4.0")
    implementation("androidx.compose.material:material:1.4.0")
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation("io.ktor:ktor-client-okhttp:2.2.4")

    implementation("io.insert-koin:koin-androidx-compose:3.4.3")
    implementation("io.insert-koin:koin-android:3.4.0")

    //SwipeRefresh
    implementation("com.google.accompanist:accompanist-swiperefresh:0.27.0")
}