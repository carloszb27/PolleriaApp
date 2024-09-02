plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.polleriaappandroid"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.polleriaappandroid"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

val room_version = "2.5.0"

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.6.1")

    implementation("com.github.bumptech.glide:glide:4.12.0")
    implementation("com.google.firebase:firebase-database:21.0.0")

    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")

    implementation("com.firebaseui:firebase-ui-database:8.0.2")

    implementation("com.firebaseui:firebase-ui-auth:8.0.2")

    implementation(platform("com.google.firebase:firebase-bom:32.3.1"))

    implementation("com.google.firebase:firebase-analytics-ktx")


    implementation("androidx.room:room-runtime:$room_version")
    implementation("com.google.firebase:firebase-auth-ktx:23.0.0")
    implementation("com.google.firebase:firebase-storage:21.0.0")
    implementation("com.google.firebase:firebase-auth:23.0.0")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    kapt("androidx.room:room-compiler:$room_version")


    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}