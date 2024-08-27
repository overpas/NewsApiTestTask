package com.example.newsapitesttask

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.newsapitesttask.ui.theme.NewsApiTestTaskTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewsApiTestTaskTheme {
                NewsApiTestTaskApp(Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun NewsApiTestTaskApp(modifier: Modifier = Modifier) {
    Text(text = "TODO", modifier = modifier)
}
