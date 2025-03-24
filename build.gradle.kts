import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.firebase) apply false
    alias(libs.plugins.composeP) apply false
    alias(libs.plugins.kover) apply false
    alias(libs.plugins.ktlint.plugin) apply true
}

subprojects {
    apply(plugin = "org.jetbrains.kotlinx.kover")
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    ktlint {
        version = "12.2.0"  // Use the updated version here too
        debug = true
        android = true
        outputToConsole = true
        outputColorName = "RED"
        ignoreFailures = false
        reporters {
            reporter(ReporterType.PLAIN)
            reporter(ReporterType.CHECKSTYLE)
        }
    }

    if (file("build.gradle.kts").exists()) {
        val detektScript = rootProject.file("gradle/detekt.gradle")
       // val ktlintScript = rootProject.file("gradle/ktlint.gradle")

        if (detektScript.exists()) {
            apply(from = detektScript)
        }

//        if (ktlintScript.exists()) {
//            apply(from = ktlintScript)
//        }
    }
}
true // Needed to make the Suppress annotation work for the plugins block