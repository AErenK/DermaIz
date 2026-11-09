// AppNavigation.kt
package com.dermaiz.app.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dermaiz.app.ui.screens.LoginScreen
import com.dermaiz.app.ui.screens.RegisterScreen
import com.dermaiz.app.ui.screens.PatientHomeScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onNavigateToPatient = {
                    navController.navigate("patient_home")
                },
                onNavigateToDoctor = {
                    // navController.navigate("doctor_home")
                },
                onNavigateToRegister = {
                    navController.navigate("register")
                }
            )
        }

        composable("register") {
            RegisterScreen(
                onNavigateBackToLogin = {
                    navController.popBackStack()
                }
            )
        }

        composable("patient_home") {
            PatientHomeScreen(
                onNavigateToAnalysis = {
                    // navController.navigate("image_upload")
                },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
    }
}