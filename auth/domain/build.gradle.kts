plugins {
    alias(libs.plugins.runique.jvm.library)
}

dependencies {
    implementation(project(":auth:data"))
    implementation(projects.core.domain)
}