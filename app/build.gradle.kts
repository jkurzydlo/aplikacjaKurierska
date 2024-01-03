plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {

    packagingOptions {
        resources.excludes.add("META-INF/*")
    }

    namespace = "jkkb.apps.aplikacjakurierska"
    compileSdk = 31

    defaultConfig {
        applicationId = "jkkb.apps.aplikacjakurierska"
        minSdk = 26
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.3.0")
    implementation("com.google.android.material:material:1.5.0-alpha03")
    implementation("androidx.constraintlayout:constraintlayout:1.1.3")
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("androidx.biometric:biometric:1.1.0-alpha02")
    implementation ("com.google.android.gms:play-services-auth:20.7.0")
    implementation("com.google.firebase:firebase-firestore:24.9.1")
    implementation ("com.google.android.gms:play-services-maps:17.0.1")
    //implementation("com.android.support:multidex:1.0.3")
    // https://mvnrepository.com/artifact/jakarta.mail/jakarta.mail-api
// https://mvnrepository.com/artifact/com.sun.mail/jakarta.mail
    implementation("com.sun.mail:jakarta.mail:2.0.1")



    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")


}