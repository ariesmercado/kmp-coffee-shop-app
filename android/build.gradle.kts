plugins {  
    id("com.android.application")
    kotlin("android")
}  
  
android {  
    compileSdk = 33
    namespace = "coffeeshop.app"
    defaultConfig {  
        applicationId = "coffeeshop.app"  
        minSdk = 21  
        targetSdk = 33  
        versionCode = 1  
        versionName = "1.0"  
    }  
    buildFeatures {  
        compose = true  
    }  
    composeOptions {  
        kotlinCompilerExtensionVersion = "1.4.0"  
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}  
  
dependencies {
    implementation(project(":shared"))
    implementation("androidx.compose.ui:ui:1.4.0")  
    implementation("androidx.compose.material:material:1.4.0")  
    implementation("androidx.compose.ui:ui-tooling-preview:1.4.0")  
    implementation("androidx.activity:activity-compose:1.7.0")
    implementation("androidx.core:core-ktx:1.9.0")
    
    // CameraX for barcode scanning
    implementation("androidx.camera:camera-camera2:1.2.3")
    implementation("androidx.camera:camera-lifecycle:1.2.3")
    implementation("androidx.camera:camera-view:1.2.3")
    
    // ML Kit Barcode Scanning
    implementation("com.google.mlkit:barcode-scanning:17.1.0")
    
    // Permissions handling
    implementation("com.google.accompanist:accompanist-permissions:0.30.1")
}  
