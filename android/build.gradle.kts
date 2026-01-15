plugins {  
    kotlin("multiplatform") version "1.6.10"  
    id("com.android.application") version "7.0.0"  
    id("org.jetbrains.kotlin.plugin.parcelize") version "1.6.10"  
}  
  
kotlin {  
    android()  
    ios()  
    sourceSets {  
        val commonMain by getting  
        val commonTest by getting  
        val androidMain by getting  
        val androidTest by getting  
        val iosMain by getting  
        val iosTest by getting  
    }  
}  
  
android {  
    compileSdk = 31  
    defaultConfig {  
        applicationId = "coffeeshop.app"  
        minSdk = 21  
        targetSdk = 31  
        versionCode = 1  
        versionName = "1.0"  
    }  
    buildFeatures {  
        compose = true  
    }  
    composeOptions {  
        kotlinCompilerExtensionVersion = "1.1.0"  
    }  
}  
  
dependencies {  
    implementation("androidx.compose.ui:ui:1.1.0")  
    implementation("androidx.compose.material:material:1.1.0")  
    implementation("androidx.compose.ui:ui-tooling-preview:1.1.0")  
    implementation("androidx.activity:activity-compose:1.3.1")  
}  
