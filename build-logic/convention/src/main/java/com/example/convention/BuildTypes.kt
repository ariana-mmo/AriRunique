package com.example.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

//fun objective: as flexible as possible
internal fun Project.configureBuildTypes(
    //specific type of gradle config to use
    commonExtension: CommonExtension<*, *, *, *, *>,
    extensionType: ExtensionType

) {
    commonExtension.run {
        //Make sure we use buildconfig
            //explicity enable that build as a feature
        buildFeatures {
            //this defines this config now for build types
            buildConfig = true
        }
        //Load from local properties
        val apiKey = gradleLocalProperties(rootDir).getProperty("API_KEY")
        //If not it will only work for application modules (n°32)
            //gradle (:core:presentation:desingsystem) --> android { :LibraryExtension
            //Objective: Flexible
        when (extensionType) {
            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(apiKey)
                        }
                        release {
                            configureReleaseBuildType(commonExtension, apiKey)
                        }
                    }
                }
            }

            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension>{
                    buildTypes{
                        debug{
                            configureDebugBuildType(apiKey)
                        }
                        release{
                            configureReleaseBuildType(commonExtension, apiKey)
                        }
                    }
                }
            }
        }
    }
}

//Little utility function (to avoid dup.)
//private (only needed inside of this file)

private fun BuildType.configureDebugBuildType(apiKey: String){
    //Here we'll set one type of config: buildConfig
        //constants that are compiled into the build during compile
        //they are usually API keys or base URls
        //Api key: no git repository -> no code
        //Base url: points to some kind of "test environment", local server
    buildConfigField("String", "API KEY", "\"$apiKey\"")
    buildConfigField("String", "BASE_URL", "\"http://10.0.2.2:8080\"")
}

private fun BuildType.configureReleaseBuildType(
    commonExtension: CommonExtension<*, *, *, *, *>,
    apiKey: String){
    //
    buildConfigField("String", "API KEY", "\"$apiKey\"")
    buildConfigField("String", "BASE_URL", "\"http://10.0.2.2:8080\"")

    isMinifyEnabled = true
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro"
    )
}