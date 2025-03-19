plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    kotlin("kapt")
    alias(libs.plugins.googleDaggerHilt)
}

android {
    namespace = "com.nahadayka"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.nahadayka"
        minSdk = 26
        targetSdk = 35
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.jetpack.navigation)

    // Hilt
    implementation(libs.google.dagger.hilt)
    kapt(libs.google.dagger.hilt.compiler)
    implementation(libs.androidx.hilt.navigation)
    kapt(libs.androidx.hilt.compiler)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel)
    implementation(libs.androidx.lifecycle.livedata)

    // Room
    implementation(libs.androidx.room.database)
    implementation(libs.androidx.room.ktx)
    kapt(libs.androidx.room.compiler)

    // Retrofit & OkHttp
    implementation(libs.squareup.retrofit.retrofit)
    implementation(libs.squareup.retrofit.converter)
    implementation(libs.squareup.okhttp3.okhttp)
    implementation(libs.squareup.okhttp3.logging)

    // JWT
    implementation(libs.json.jwt)
    // Worker
    implementation(libs.androidx.hilt.worker)
}