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
fun SymptomFormScreen(
    onSubmit: (duration: String, hasBleeding: Boolean, changes: String) -> Unit
) {
    var duration by remember { mutableStateOf("") }
    var hasBleeding by remember { mutableStateOf(false) }
    var changes by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Belirti ve Şikayetler",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp, top = 16.dp)
        )

        // 1. Şikayet Süresi
        Text(
            text = "Bu lezyonu ne zamandır fark ediyorsunuz?",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = duration,
            onValueChange = { duration = it },
            placeholder = { Text("Örn: Yaklaşık 3 aydır") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
        )

        // 2. Kanama Durumu
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp)
        ) {
            Checkbox(
                checked = hasBleeding,
                onCheckedChange = { hasBleeding = it }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Lezyonda kanama veya sızıntı var mı?",
                style = MaterialTheme.typography.bodyLarge
            )
        }

        // 3. Değişim Durumu
        Text(
            text = "Zaman içinde boyutu, rengi veya şeklinde bir değişiklik oldu mu?",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        OutlinedTextField(
            value = changes,
            onValueChange = { changes = it },
            placeholder = { Text("Örn: Son bir ayda rengi koyulaştı ve büyüdü") },
            modifier = Modifier.fillMaxWidth().height(120.dp).padding(bottom = 32.dp),
            maxLines = 4
        )

        Button(
            onClick = { onSubmit(duration, hasBleeding, changes) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = duration.isNotBlank() && changes.isNotBlank()
        ) {
            Text("Analize Gönder")
        }
    }
}