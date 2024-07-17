plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hilt)
    alias(libs.plugins.firebase)
    kotlin("kapt")
}

android {
    namespace = "pl.dawidfendler.dailyeaseassistant"
    compileSdk = 34

    defaultConfig {
        applicationId = "pl.dawidfendler.dailyeaseassistant"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(":common:ui"))
    implementation(project(":common:util"))
    implementation(project(":core:coroutines"))
    implementation(project(":core:datastore"))
    implementation(project(":features:authentication"))
    implementation(project(":features:onboarding"))

    implementation(libs.data.store)
    implementation(libs.core.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.splash.screen)
    implementation(libs.hilt.android)
    implementation(libs.navigation.compose)
    implementation(libs.timber.logger)
    implementation(libs.firebase.auth.ktx)
    kapt(libs.hilt.compiler)
}
