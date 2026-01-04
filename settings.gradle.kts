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

include(":core:network")
include(":core:database")
include(":core:model")
include(":core:ui")
include(":core:common")

include(":feature:auth:api")
include(":feature:auth:impl")

include(":feature:races:api")

include(":feature:standings:api")
include(":feature:standings:impl")

include(":feature:profile:api")
include(":feature:profile:impl")

include(":umbrella")
include(":feature:races:impl")
include(":core:navigation")
