package com.dermaiz.app.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dermaiz.app.ui.screens.LoginScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        // 1. Giriş ve Rol Seçim Ekranı
        composable("login") {
            LoginScreen(
                onNavigateToPatient = { navController.navigate("patient_home") },
                onNavigateToDoctor = { navController.navigate("doctor_dashboard") }
            )
        }

        // 2. Hasta Ana Ekranı (Geçici Yer Tutucu)
        composable("patient_home") {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Hasta Ana Ekranı - Lezyon Kayıtları Buraya Gelecek")
            }
        }

        // 3. Doktor Paneli (Geçici Yer Tutucu)
        composable("doctor_dashboard") {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Doktor Paneli - Bekleyen Vakalar Buraya Gelecek")
            }
        }
    }
}