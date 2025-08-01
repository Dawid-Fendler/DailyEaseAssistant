plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hilt)
    alias(libs.plugins.composeP)
    alias(libs.plugins.kotlin.serialization)
    kotlin("kapt")
}

android {
    namespace = "pl.dawidfendler.finance_manager"
    compileSdk = 35

    defaultConfig {
        minSdk = 30

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    implementation(project(":common:ui"))
    implementation(project(":common:util"))
    implementation(project(":core:coroutines"))
    implementation(project(":core:datastore"))
    implementation(project(":core:date"))
    implementation(project(":domain"))
    testImplementation(project(":core:date"))

    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.material3)
    implementation(libs.navigation.compose)
    implementation(libs.viewmodel.lifecycle)
    implementation(libs.hilt.navigation.compose)
    implementation(libs.hilt.android)
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.ui.preview)
    implementation(libs.data.store)
    implementation(libs.kotlinx.serialization.json)

    kapt(libs.hilt.compiler)

    testImplementation(libs.junit)
    testImplementation(libs.truth.test)
    testImplementation(libs.mockk.test)
    testImplementation(libs.turbine.test)
    testImplementation(libs.kotlinx.coroutines.test)
}