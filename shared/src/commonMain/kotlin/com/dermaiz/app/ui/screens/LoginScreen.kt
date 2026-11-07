package com.dermaiz.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.dermaiz.app.network.SupabaseHelper
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

@Serializable
data class UserProfile(
    val id: String,
    val email: String,
    val role: String
)

@Composable
fun LoginScreen(
    onNavigateToPatient: () -> Unit,
    onNavigateToDoctor: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedRole by remember { mutableStateOf("patient") } // "patient" veya "doctor"
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Dermaİz Giriş", style = MaterialTheme.typography.headlineMedium)
        Text("Cilt Lezyonu Analiz ve Triyaj Sistemi", style = MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-posta Adresi") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Şifre") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Rol Seçim Butonları (Hasta / Doktor)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { selectedRole = "patient" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedRole == "patient") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Hasta")
            }
            Button(
                onClick = { selectedRole = "doctor" },
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedRole == "doctor") MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                )
            ) {
                Text("Doktor")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    coroutineScope.launch {
                        isLoading = true
                        errorMessage = null
                        try {
                            // 1. Supabase ile Giriş Yapmayı Dene
                            SupabaseHelper.client.auth.signInWith(Email) {
                                this.email = email
                                this.password = password
                            }

                            // Başarılı giriş sonrası role göre yönlendir
                            if (selectedRole == "doctor") {
                                onNavigateToDoctor()
                            } else {
                                onNavigateToPatient()
                            }
                        } catch (e: Exception) {
                            // Eğer hesap yoksa otomatik Kayıt Olmayı (SignUp) dene
                            try {
                                SupabaseHelper.client.auth.signUpWith(Email) {
                                    this.email = email
                                    this.password = password
                                }

                                // Profil tablosuna rolü kaydet
                                val currentUserId = SupabaseHelper.client.auth.currentUserOrNull()?.id ?: ""
                                val profile = UserProfile(id = currentUserId, email = email, role = selectedRole)
                                SupabaseHelper.client.from("profiles").insert(profile)

                                if (selectedRole == "doctor") {
                                    onNavigateToDoctor()
                                } else {
                                    onNavigateToPatient()
                                }
                            } catch (regError: Exception) {
                                errorMessage = "Hata: ${e.message}"
                            }
                        } finally {
                            isLoading = false
                        }
                    }
                }
            ) {
                Text("Giriş Yap / Kayıt Ol")
            }
        }

        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
        }
    }
}