package com.dermaiz.app.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.vinceglb.filekit.compose.rememberFilePickerLauncher
import io.github.vinceglb.filekit.core.PickerType
import kotlinx.coroutines.launch // Yeni eklendi

@Composable
fun ImageSelectionScreen(
    onImageSelected: (ByteArray) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val launcher = rememberFilePickerLauncher(
        type = PickerType.Image,
        title = "Lezyon Fotoğrafı Seç"
    ) { file ->
        // Kullanıcı bir dosya seçtiğinde burası tetiklenir
        file?.let { selectedFile ->
            coroutineScope.launch {
                // Dosyayı byte dizisine çevirip ana akışa (navigasyona) gönderiyoruz
                val bytes = selectedFile.readBytes()
                onImageSelected(bytes)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Dermaİz Ön Değerlendirme",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Lütfen lezyon bölgenin net bir fotoğrafını yükleyin veya çekin.",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 32.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Button(
            onClick = { launcher.launch() },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(bottom = 16.dp)
        ) {
            Text("Galeriden Fotoğraf Seç")
        }

        OutlinedButton(
            onClick = {
                // TODO: Kamera entegrasyonu daha sonra eklenecek
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Kamera ile Çek")
        }
    }
}