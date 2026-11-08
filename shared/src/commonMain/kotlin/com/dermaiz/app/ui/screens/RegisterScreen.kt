package com.dermaiz.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dermaiz.app.network.Supabase
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

val SuccessGreen = Color(0xFF22C55E)

@Composable
fun RegisterScreen(
    onNavigateBackToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var selectedRole by remember { mutableStateOf("patient") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successMessage by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            Surface(
                color = SurfaceBg.copy(alpha = 0.9f),
                shadowElevation = 1.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(64.dp)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    IconButton(onClick = onNavigateBackToLogin) {
                        Text("←", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = PrimaryNavy)
                    }
                    Text(
                        text = "Dermaİz | Yeni Hesap Kaydı",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = PrimaryNavy
                    )
                }
            }
        },
        containerColor = SurfaceBg
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Aramıza Katılın",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryNavy
                )
                Text(
                    text = "Güvenli teletriyaj ve yapay zeka analiz platformu",
                    fontSize = 12.sp,
                    color = OnSurfaceVariant
                )
            }

            // Kayıt Rol Seçimi
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(SurfaceContainerHigh)
                    .padding(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Button(
                    onClick = { selectedRole = "patient" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedRole == "patient") SurfaceContainerLowest else Color.Transparent,
                        contentColor = if (selectedRole == "patient") PrimaryNavy else OnSurfaceVariant
                    ),
                    shape = RoundedCornerShape(8.dp),
                    elevation = if (selectedRole == "patient") ButtonDefaults.buttonElevation(2.dp) else ButtonDefaults.buttonElevation(0.dp),
                    modifier = Modifier.weight(1f).height(40.dp)
                ) {
                    Text("👤 Hasta Kaydı", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }

                Button(
                    onClick = { selectedRole = "doctor" },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (selectedRole == "doctor") SurfaceContainerLowest else Color.Transparent,
                        contentColor = if (selectedRole == "doctor") PrimaryNavy else OnSurfaceVariant
                    ),
                    shape = RoundedCornerShape(8.dp),
                    elevation = if (selectedRole == "doctor") ButtonDefaults.buttonElevation(2.dp) else ButtonDefaults.buttonElevation(0.dp),
                    modifier = Modifier.weight(1f).height(40.dp)
                ) {
                    Text("🩺 Hekim Kaydı", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            Surface(
                color = SurfaceContainerLowest,
                shape = RoundedCornerShape(16.dp),
                shadowElevation = 3.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(text = "E-Posta Adresi", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PrimaryNavy)
                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            placeholder = { Text("ornek@mail.com", color = OutlineColor, fontSize = 13.sp) },
                            singleLine = true,
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = SurfaceContainerLow,
                                unfocusedContainerColor = SurfaceContainerLow,
                                focusedBorderColor = SecondaryAzure,
                                unfocusedBorderColor = Color.Transparent
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(text = "Şifre (En az 6 karakter)", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PrimaryNavy)
                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            placeholder = { Text("••••••••", color = OutlineColor, fontSize = 13.sp) },
                            singleLine = true,
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            trailingIcon = {
                                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                    Text(if (passwordVisible) "👁️‍🗨️" else "👁️", fontSize = 16.sp)
                                }
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedContainerColor = SurfaceContainerLow,
                                unfocusedContainerColor = SurfaceContainerLow,
                                focusedBorderColor = SecondaryAzure,
                                unfocusedBorderColor = Color.Transparent
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }

                    if (errorMessage != null) {
                        Text(text = errorMessage!!, color = ErrorColor, fontSize = 12.sp)
                    }
                    if (successMessage != null) {
                        Text(text = successMessage!!, color = SuccessGreen, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    Button(
                        onClick = {
                            scope.launch {
                                isLoading = true
                                errorMessage = null
                                successMessage = null
                                try {
                                    Supabase.client.auth.signUpWith(Email) {
                                        this.email = email
                                        this.password = password
                                        this.data = buildJsonObject {
                                            put("role", selectedRole)
                                        }
                                    }
                                    successMessage = "Kayıt başarılı! Giriş sayfasına dönebilirsiniz."
                                } catch (e: Exception) {
                                    errorMessage = "Kayıt başarısız. Şifre kurallarını kontrol edin."
                                } finally {
                                    isLoading = false
                                }
                            }
                        },
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryNavy),
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(color = Color.White, modifier = Modifier.size(22.dp))
                        } else {
                            Text(text = "Kayıt Ol", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }

            TextButton(onClick = onNavigateBackToLogin) {
                Text(text = "← Zaten hesabınız var mı? Giriş yapın", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SecondaryAzure)
            }
        }
    }
}