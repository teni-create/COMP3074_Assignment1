plugins { id("com.android.application") }

android {
    namespace = "ca.gbc.comp3074.paycalculator"
    compileSdk { version = release(37) }
    defaultConfig {
        applicationId = "ca.gbc.comp3074.paycalculator"
        minSdk = 24
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies { testImplementation("junit:junit:4.13.2") }
