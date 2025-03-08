// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.firebase) apply false
    alias(libs.plugins.composeP) apply false
    alias(libs.plugins.kover) apply false
}

subprojects {
    apply(plugin = "org.jetbrains.kotlinx.kover")

    if (file("build.gradle.kts").exists()) {
        val detektScript = rootProject.file("gradle/detekt.gradle")
        val ktlintScript = rootProject.file("gradle/ktlint.gradle")

        if (detektScript.exists()) {
            apply(from = detektScript)
        }

        if (ktlintScript.exists()) {
            apply(from = ktlintScript)
        }
    }
}
true // Needed to make the Suppress annotation work for the plugins block