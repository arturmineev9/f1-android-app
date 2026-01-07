pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "F1 App"
include(":app")

include(":core:common")
include(":core:database")
include(":core:network")
include(":core:navigation")
include(":core:ui")

include(":feature:auth:api")
include(":feature:auth:impl")

include(":feature:drivers:api")
include(":feature:drivers:impl")

include(":feature:races:api")
include(":feature:races:impl")

include(":feature:standings:api")
include(":feature:standings:impl")

include(":umbrella")
