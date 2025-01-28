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
include(":data")
include(":domain")
include(":common")
include(":common:ui")
include(":common:util")
include(":core")
include(":core:coroutines")
include(":core:datastore")
include(":features")
include(":features:authentication")
include(":features:home")
include(":features:onboarding")
include(":features:finance_manager")
