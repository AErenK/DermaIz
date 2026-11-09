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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun PatientHomeScreen(
    onNavigateToAnalysis: () -> Unit,
    onLogout: () -> Unit
) {
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

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

                    IconButton(onClick = onLogout) {
                        Text("Çıkış", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = ErrorColor)
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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {

            // Hoşgeldin Kartı
            Surface(
                color = PrimaryNavy,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Hoş Geldiniz,", fontSize = 14.sp, color = SurfaceContainerHigh)
                    Text("Nasıl Yardımcı Olabiliriz?", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                }
            }

            // Yeni Analiz Başlat (Fotoğraf Yükleme/Çekme) Bölümü
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Yeni Cilt Analizi", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = PrimaryNavy)

                Surface(
                    color = SurfaceContainerLowest,
                    shape = RoundedCornerShape(16.dp),
                    shadowElevation = 2.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(CircleShape)
                                .background(SurfaceContainerHigh),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("📸", fontSize = 28.sp)
                        }

                        Text(
                            text = "Şüpheli lezyonun net bir fotoğrafını yükleyerek yapay zeka destekli ön analiz alın.",
                            fontSize = 13.sp,
                            color = OnSurfaceVariant,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )

                        Button(
                            onClick = onNavigateToAnalysis,
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = SecondaryAzure),
                            modifier = Modifier.fillMaxWidth().height(48.dp)
                        ) {
                            Text("Yeni Fotoğraf Yükle / Çek", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.White)
                        }
                    }
                }
            }

            // Geçmiş Analizler Listesi (Şimdilik boş state)
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Geçmiş Analizlerim", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = PrimaryNavy)
                    TextButton(onClick = { }) {
                        Text("Tümünü Gör", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = SecondaryAzure)
                    }
                }

                Surface(
                    color = SurfaceContainerLow,
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("📁", fontSize = 32.sp)
                        Text("Henüz bir analiz bulunmuyor.", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = PrimaryNavy)
                        Text("İlk lezyonunuzu yükleyerek takibe başlayabilirsiniz.", fontSize = 12.sp, color = OnSurfaceVariant, textAlign = androidx.compose.ui.text.style.TextAlign.Center)
                    }
                }
            }
        }
    }
}