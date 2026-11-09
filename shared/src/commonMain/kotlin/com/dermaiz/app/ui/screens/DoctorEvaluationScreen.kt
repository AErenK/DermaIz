package com.dermaiz.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DoctorEvaluationScreen(
    onPrioritySelected: (String) -> Unit
) {
    var selectedPriority by remember { mutableStateOf("Orta Öncelikli") }
    var doctorNotes by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Vaka Değerlendirmesi",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp, top = 8.dp)
        )

        // Hastadan Gelen Verilerin Özeti (Demo amaçlı statik UI)
        Card(modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Hasta Şikayeti: Yaklaşık 3 aydır var.", style = MaterialTheme.typography.bodyMedium)
                Text("Kanama: Var", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.error)
                Text("Değişim: Son 1 ayda rengi koyulaştı.", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                // Yapay Zeka & Grad-CAM alanı (Sonradan eklenecek)
                Text("Yapay Zeka Önerisi: Yüksek Risk (%87)", style = MaterialTheme.typography.titleMedium)
            }
        }

        Text(
            text = "Triyaj Öncelik Belirleme",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Sistemin önerdiği önceliği değiştirebilir veya onaylayabilirsiniz.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Öncelik Seçim Butonları
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            listOf("Düşük", "Orta", "Yüksek").forEach { priority ->
                FilterChip(
                    selected = selectedPriority == priority,
                    onClick = { selectedPriority = priority },
                    label = { Text(priority) }
                )
            }
        }

        // Doktor Notları
        OutlinedTextField(
            value = doctorNotes,
            onValueChange = { doctorNotes = it },
            label = { Text("Doktor Değerlendirme Notu") },
            modifier = Modifier.fillMaxWidth().height(120.dp).padding(bottom = 24.dp),
            maxLines = 4
        )

        Button(
            onClick = { onPrioritySelected(selectedPriority) },
            modifier = Modifier.fillMaxWidth().height(56.dp)
        ) {
            Text("Değerlendirmeyi Kaydet")
        }
    }
}