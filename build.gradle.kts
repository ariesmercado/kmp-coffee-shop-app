plugins {
    // Kotlin Multiplatform
    kotlin("multiplatform") version "1.9.21" apply false
    kotlin("android") version "1.9.21" apply false
    
    // Android
    id("com.android.application") version "8.1.4" apply false
    id("com.android.library") version "8.1.4" apply false
    
    // Compose
    id("org.jetbrains.compose") version "1.5.11" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
