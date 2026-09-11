import com.android.build.api.dsl.ApplicationExtension
import com.example.convention.ExtensionType
import com.example.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import com.example.convention.configureKotlinAndroid
import com.example.convention.configureBuildTypes


//EXAMPLE: use XML
//plugin applicable to android applications modules(no compose)
class AndroidApplicationConventionPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        //We do this to not always refer to target
        target.run {
            //What makes an android application?
                //Something every "" "" should have
            //Every Gradle file has
            pluginManager.run {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            //To get access to android
            extensions.configure<ApplicationExtension>{
                defaultConfig{
                    //Before this -> ProjectExt
                    applicationId = libs.findVersion("projectApplicationId").get().toString()
                    targetSdk = libs.findVersion("projectTargetSdkVersion").get().toString().toInt()

                    versionCode = libs.findVersion("projectVersionCode").get().toString().toInt()
                    versionName = libs.findVersion("projectVersionName").get().toString()
                }

                configureKotlinAndroid(this)

                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.APPLICATION
                )
            }

        }
    }
}