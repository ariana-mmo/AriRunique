package com.plcoding.runique

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.plcoding.auth.presentation.intro.IntroScreenRoot
import com.plcoding.auth.presentation.login.LoginScreenRoot
import com.plcoding.auth.presentation.register.RegisterScreenRoot

@Composable
fun NavigationRoot(
    //Duda:Diferencia entre el nav y navhost
    navController: NavHostController
){
    //show the place you are at
    NavHost(
        navController = navController,
        startDestination = "auth"
    ) {
        authGraph(navController)
        runGraph(navController)
    }

}

//graph for separate
//function that extends navhost

//builder contruye el mapa, navigator te lleva por el mapa
private fun NavGraphBuilder.authGraph(navController: NavHostController){
    navigation(
        startDestination = "intro",
        route = "auth"
    ) {
        //this navigation route shows this composable
        composable(route = "intro"){
            IntroScreenRoot(
                onSignUpClick = {
                    navController.navigate("register")
                },
                onSignInClick = {
                    navController.navigate("login")
                }
            )
        }
        composable(route = "register") {
            RegisterScreenRoot(
                onSignInClick = {
                    //because we dont want another screen
                    navController.navigate("login"){
                        popUpTo("register"){
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                },
                onSuccessfulRegistration = {
                    navController.navigate("login")
                }
            )

        }
        composable(route = "login"){
            LoginScreenRoot(
                onLoginSuccess = {
                    navController.navigate("run"){
                        popUpTo("auth"){
                            inclusive = true
                        }
                    }
                },
                onSignUpClick = {
                    navController.navigate("register"){
                        popUpTo("login"){
                            inclusive = true
                            saveState = true
                        }
                        restoreState = true
                    }
                }

            )
        }
    }

}

private fun NavGraphBuilder.runGraph(navController: NavHostController){
    navigation(
        startDestination = "run_overview",
        route = "run"
    ) {
        composable(route = "run_overview"){
            Text(text =  "Run overview!")
        }
    }
}