pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "DailyEase Assistant"
include(":app")
include(":common")
include(":common:ui")
include(":common:util")
include(":core")
include(":core:coroutines")
include(":core:datastore")
include(":features")
include(":features:onboarding")
include(":features:authentication")
include(":data")
include(":domain")
