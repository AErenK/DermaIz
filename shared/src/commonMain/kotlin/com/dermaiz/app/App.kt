package com.dermaiz.app

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dermaiz.app.ui.screens.*

@Composable
fun App() {
    MaterialTheme {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "image_selection") {

            // 1. Aşama: Fotoğraf Seçimi (Hasta Akışı)
            composable("image_selection") {
                ImageSelectionScreen(
                    onImageSelected = { imageBytes ->
                        // Fotoğraf seçilince form ekranına geç
                        navController.navigate("symptom_form")
                    }
                )
            }

            // 2. Aşama: Belirti Formu (Hasta Akışı)
            composable("symptom_form") {
                SymptomFormScreen(
                    onSubmit = { duration, hasBleeding, changes ->
                        // Form gönderilince demo olarak direkt doktor paneline geçiyoruz
                        navController.navigate("doctor_evaluation")
                    }
                )
            }

            // 3. Aşama: Doktor Değerlendirmesi (Doktor Akışı)
            composable("doctor_evaluation") {
                DoctorEvaluationScreen(
                    onPrioritySelected = { priority ->
                        // Doktor onaylayınca yapılacak işlem (şimdilik başa dön)
                        navController.navigate("image_selection") {
                            popUpTo(0) // Geçmişi temizle
                        }
                    }
                )
            }
        }
    }
}