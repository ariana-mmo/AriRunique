package com.example.convention

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

//Reference to libs.versions
val Project.libs
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")