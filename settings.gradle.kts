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

rootProject.name = "Effective Mobile Task"

include(":app")
include(":core:ui")
include(":core:base")
include(":core:data")
include(":core:domain")
include(":core:navigation")
include(":feature:auth:api")
include(":feature:auth:impl")
include(":feature:courses:api")
include(":feature:courses:impl")
include(":feature:favorites:api")
include(":feature:favorites:impl")
include(":core:theme")
include(":feature:account")
include(":feature:details:impl")
