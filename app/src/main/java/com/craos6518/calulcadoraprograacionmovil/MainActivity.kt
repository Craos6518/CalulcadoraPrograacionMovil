package com.craos6518.calulcadoraprograacionmovil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import com.craos6518.calulcadoraprograacionmovil.ui.theme.CalulcadoraPrograacionMovilTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val modeloVistaCalculadora = ViewModelProvider(this)[ModeloVistaCalculadora::class.java]
        enableEdgeToEdge()
        setContent {
            CalulcadoraPrograacionMovilTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Calculadora(modifier = Modifier.padding(innerPadding), modeloVistaCalculadora )
                }
            }
        }
    }
}

