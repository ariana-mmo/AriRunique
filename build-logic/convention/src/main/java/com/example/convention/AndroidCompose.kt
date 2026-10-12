package com.example.convention

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *>
) {

    //gradle:app (compose)
    commonExtension.run {
        //enable everything is needed for compose
        buildFeatures{
            compose = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs
                .findVersion("composeCompiler")
                .get()
                .toString()
        }

        dependencies {
            val bom = libs.findLibrary("androidx.compose.bom").get()
            "implementation"(platform(bom))
            "androidTestImplementation"(platform(bom))
            // The Preview annotation is available at compile time via ui-tooling-preview,
            // but Android Studio's renderer needs the full tooling implementation at runtime.
            "debugImplementation"(libs.findLibrary("androidx.compose.ui.tooling").get())
        }

    }
}
