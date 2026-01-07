plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.detekt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt.plugin)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.firebase.performace)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "ru.itis.f1app"
    compileSdk {
        version = release(libs.versions.compileSdk.get().toInt())
    }

    configurations.all {
        resolutionStrategy {
            exclude(group = "com.intellij", module = "annotations")
        }
    }


    defaultConfig {
        applicationId = "ru.itis.f1app"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = rootProject.extra.get("versionCode") as Int
        versionName = rootProject.extra.get("versionName") as String

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
    implementation(project(":core:navigation"))
    implementation(project(":feature:auth:api"))
    implementation(project(":feature:auth:impl"))
    implementation(project(":feature:races:api"))
    implementation(project(":feature:races:impl"))
    implementation(project(":feature:standings:api"))
    implementation(project(":feature:standings:impl"))
    implementation(project(":feature:drivers:api"))
    implementation(project(":feature:drivers:impl"))
    //implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.crashlytics)
    implementation(libs.firebase.performance)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.voyager.deps)
    implementation(libs.androidx.activity.compose)
}
