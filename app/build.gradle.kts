apply plugin: 'com.android.application'

apply plugin: 'kotlin-android'

apply plugin: 'kotlin-kapt'


    android {
        compileSdkVersion 33
        defaultConfig {
            applicationId 'com.example.proyecto_notas'
            multiDexEnabled true
            minSdkVersion 19
            targetSdkVersion 33
            vectorDrawables.useSupportLibrary = true
            versionCode 1
            versionName "1.0"
            testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
        }

        buildTypes {
            release {
                minifyEnabled false
                proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
            }
        }
        productFlavors {
        }
        buildFeatures {
            dataBinding true
        }
    }

    dependencies {
        implementation 'androidx.multidex:multidex:2.0.1'
        implementation fileTree(include: ['*.jar'], dir: 'libs')
        implementation "org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version"
        implementation "org.jetbrains.kotlin:kotlin-reflect:$kotlin_version"
        implementation "androidx.appcompat:appcompat:$supportlibVersion"
        implementation 'androidx.constraintlayout:constraintlayout:2.0.4'
        implementation 'androidx.legacy:legacy-support-v4:1.0.0'
        implementation "com.google.android.material:material:1.2.1"
        implementation "androidx.navigation:navigation-fragment-ktx:$navigationVersion"
        implementation "androidx.navigation:navigation-ui-ktx:$navigationVersion"
        implementation "com.google.android.material:material:$version"
        implementation 'com.jakewharton:butterknife:10.0.0'
        annotationProcessor 'com.jakewharton:butterknife-compiler:10.0.0'
        implementation 'androidx.fragment:fragment-ktx:1.6.0-alpha03'

        //crop image library


        implementation "androidx.room:room-runtime:2.4.1"
        kapt "androidx.room:room-compiler:2.4.1"
        implementation "androidx.room:room-ktx:2.4.1"
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.9")
        implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1"
        implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"
        implementation "androidx.lifecycle:lifecycle-common-java8:2.3.1"
        implementation 'com.google.dagger:dagger:2.11'
        annotationProcessor 'com.google.dagger:dagger-compiler:2.11'
        implementation 'com.google.dagger:dagger-android:2.35.1' // If you're using classes in dagger.android
        implementation 'com.google.dagger:dagger-android-support:2.11' // if you use the support libraries
        annotationProcessor 'com.google.dagger:dagger-android-processor:2.11'
        implementation "com.google.dagger:hilt-android:2.44"
        kapt "com.google.dagger:hilt-compiler:2.44"
    }

}
kapt {
    correctErrorTypes = true
}