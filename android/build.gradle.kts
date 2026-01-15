plugins {  
    id("com.android.application") version "7.0.0"  
    kotlin("android") version "1.6.10"
}  
  
android {  
    compileSdk = 31
    namespace = "coffeeshop.app"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}  
  
dependencies {
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.1.0")  
    implementation("androidx.compose.material:material:1.1.0")  
    implementation("androidx.compose.ui:ui-tooling-preview:1.1.0")  
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.core:core-ktx:1.7.0")
}  
