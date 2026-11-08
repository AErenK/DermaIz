package com.dermaiz.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import kotlinx.serialization.json.jsonPrimitive

val SurfaceBg = Color(0xFFF8F9FF)
val PrimaryNavy = Color(0xFF001428)
val SecondaryAzure = Color(0xFF0284C7)
val SurfaceContainerLowest = Color(0xFFFFFFFF)
val SurfaceContainerLow = Color(0xFFEFF4FF)
val SurfaceContainerHigh = Color(0xFFDCE9FF)
val OnSurfaceVariant = Color(0xFF43474D)
val OutlineColor = Color(0xFF74777E)
val ErrorContainerBg = Color(0xFFFFDAD6)
val OnErrorContainer = Color(0xFF93000A)
val ErrorColor = Color(0xFFBA1A1A)

@Composable
fun LoginScreen(
    onNavigateToPatient: () -> Unit,
    onNavigateToDoctor: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    var identifier by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    var selectedRole by remember { mutableStateOf("patient") }
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
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(PrimaryNavy),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("D", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                        Text(
                            text = "Dermaİz",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryNavy
                        )
                    }
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
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(PrimaryNavy),
                    contentAlignment = Alignment.Center
                ) {
                    Text("🩺", fontSize = 28.sp)
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Dermaİz Teletriyaj",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = PrimaryNavy
                )
                Text(
                    text = "KLİNİK KARAR DESTEK & TRİYAJ SİSTEMİ",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = SecondaryAzure,
                    letterSpacing = 1.sp
                )
            }

            // Rol Seçim Sekmeleri
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
                    Text("👤 Hasta Girişi", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
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
                    Text("🩺 Hekim Girişi", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                }
            }

            // Bağlam Kartı
            Surface(
                color = SurfaceContainerLow,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(14.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(SurfaceContainerHigh),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(if (selectedRole == "patient") "📸" else "🏥", fontSize = 18.sp)
                    }
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = if (selectedRole == "patient") "Bireysel Cilt Analizi & Sevk" else "Uzman Hekim Triyaj Havuzu",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryNavy
                        )
                        Text(
                            text = if (selectedRole == "patient") "Lezyon takibi yapabilir, acil teletriyaj değerlendirmesi alabilirsiniz."
                            else "ÇKYS/Tescil No veya kurum e-postası ile konsültasyon paneli.",
                            fontSize = 11.sp,
                            color = OnSurfaceVariant
                        )
                    }
                }
            }

            // Ana Giriş Kartı
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
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (selectedRole == "patient") "E-Posta Adresi" else "Tescil No / E-posta",
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold,
                                color = PrimaryNavy
                            )
                            Surface(color = SurfaceContainerHigh, shape = RoundedCornerShape(4.dp)) {
                                Text(
                                    text = if (selectedRole == "patient") "Kayıtlı E-posta" else "ÇKYS",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = SecondaryAzure,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        OutlinedTextField(
                            value = identifier,
                            onValueChange = { identifier = it },
                            placeholder = { Text(if (selectedRole == "patient") "ornek@mail.com" else "hekim@maku.edu.tr", color = OutlineColor, fontSize = 13.sp) },
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
                        Text(text = "Şifre", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = PrimaryNavy)
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

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            Checkbox(
                                checked = rememberMe,
                                onCheckedChange = { rememberMe = it },
                                colors = CheckboxDefaults.colors(checkedColor = PrimaryNavy)
                            )
                            Text("Beni hatırla", fontSize = 12.sp, color = PrimaryNavy, fontWeight = FontWeight.Medium)
                        }
                        TextButton(onClick = {}) {
                            Text("Şifremi Unuttum", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SecondaryAzure)
                        }
                    }

                    if (errorMessage != null) {
                        Text(text = errorMessage!!, color = ErrorColor, fontSize = 12.sp)
                    }

                    Button(
                        onClick = {
                            scope.launch {
                                isLoading = true
                                errorMessage = null
                                try {
                                    Supabase.client.auth.signInWith(Email) {
                                        this.email = identifier
                                        this.password = password
                                    }
                                    val userRole = Supabase.client.auth.currentUserOrNull()
                                        ?.userMetadata?.get("role")?.jsonPrimitive?.content
                                    if (userRole == "doctor" || selectedRole == "doctor") onNavigateToDoctor()
                                    else onNavigateToPatient()
                                } catch (e: Exception) {
                                    errorMessage = "Giriş başarısız. Bilgilerinizi kontrol edin."
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
                            Text(text = "Giriş Yap", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        HorizontalDivider(modifier = Modifier.weight(1f), color = SurfaceContainerHigh)
                        Text("VEYA", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = OutlineColor)
                        HorizontalDivider(modifier = Modifier.weight(1f), color = SurfaceContainerHigh)
                    }

                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = ErrorContainerBg),
                        modifier = Modifier.fillMaxWidth().height(48.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Box(
                                modifier = Modifier.size(20.dp).clip(CircleShape).background(ErrorColor),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("★", color = Color.White, fontSize = 10.sp)
                            }
                            Text(text = "e-Devlet Kapısı ile Giriş Yap", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = OnErrorContainer)
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text("Hesabınız yok mu?", fontSize = 13.sp, color = OnSurfaceVariant)
                TextButton(onClick = onNavigateToRegister) {
                    Text(text = "Yeni Hesap Oluştur →", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = SecondaryAzure)
                }
            }
        }
    }
}