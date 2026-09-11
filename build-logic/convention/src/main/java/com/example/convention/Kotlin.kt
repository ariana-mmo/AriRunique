package com.example.convention

import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.internal.cxx.cmake.TargetDataItem
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

//A fun to override (min sdk)
internal fun Project.configureKotlinAndroid(
    //parameters (does not matter)
    commonExtension: CommonExtension<*, *, *, *, *>
){
    //apply == run
    commonExtension.apply {
        compileSdk = libs.findVersion("projectCompileSdkVersion").get().toString().toInt()

        defaultConfig.minSdk = libs.findVersion("projectMinSdkVersion").get().toString().toInt()

        //pq acá si menciona al block, es pq no lo hizo antes?
        compileOptions {
            isCoreLibraryDesugaringEnabled = true
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }

    configureKotlin()

    dependencies{
        "coreLibraryDesugaring"(libs.findLibrary("desugar.jdk.libs").get())
    }
}


//Useful 4 both (android, jvm), but for jvm we configure source and target comp.
internal fun Project.configureKotlinJvm() {
    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    configureKotlin()
}

//Kotlin version (setting jvmTarget to the right version)
private fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()
        }
    }
}