import com.android.build.api.dsl.ApplicationExtension
import com.example.convention.configureAndroidCompose
//import com.plcoding.convention.configureAndroidCompose
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType


class AndroidApplicationComposeConventionPlugin: Plugin<Project> {

    override fun apply(target: Project) {
        target.run {

            //apply all the configuration from the previous Plugin
            pluginManager.apply("runique.android.application")


            val extension = extensions.getByType<ApplicationExtension>()

            //without this we cant have an Application module without compose
            configureAndroidCompose(extension)
        }
    }
}