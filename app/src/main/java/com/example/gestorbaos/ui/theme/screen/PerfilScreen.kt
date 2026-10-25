package com.example.gestorbaos.ui.theme.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gestorbaos.R

@Composable
fun PerfilScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Image(
            modifier = Modifier
                .size(150.dp)
                .clip(CircleShape)
                .border(
                    width = 4.dp,
                    color = Color.Blue,
                    shape = CircleShape
                ),
            painter = painterResource(R.drawable.sans),
            contentDescription = "Foto perfil",
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text("SANS", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("Encargado", style = MaterialTheme.typography.bodyMedium, color = Color.Gray)

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.surfaceVariant, contentColor = MaterialTheme.colorScheme.onSurface)
        ) {
            Text("AJUSTES")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error,
                contentColor = MaterialTheme.colorScheme.onError
            )
        ) {
            Text("CERRAR SESIÓN")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PerfilScreenPreview() {
    MaterialTheme {
        PerfilScreen()
    }
}
