package com.craos6518.calulcadoraprograacionmovil

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.craos6518.calulcadoraprograacionmovil.ui.theme.CalulcadoraPrograacionMovilTheme

@Composable
fun Historial(
    viewModel: ModeloVistaCalculadora,
    onBack: () -> Unit
) {
    val operaciones by viewModel.historial.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onBack) {
                Text(stringResource(R.string.history_title))
            }
            Text("Historial", style = MaterialTheme.typography.headlineSmall)
            TextButton(
                onClick = viewModel::limpiarHistorial,
                enabled = operaciones.isNotEmpty()
            ) {
                Text(stringResource(R.string.history_clear))
            }
        }

        if (operaciones.isEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(R.string.history_empty))
                Button(onClick = onBack, modifier = Modifier.padding(top = 12.dp)) {
                    Text(stringResource(R.string.history_return))
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(operaciones) { operacion ->
                    Card(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = operacion,
                            modifier = Modifier.padding(16.dp),
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Historial Vacio")
@Composable
fun HistorialVacioPreview() {
    val viewModel = remember { ModeloVistaCalculadora() }
    CalulcadoraPrograacionMovilTheme {
        Historial(
            viewModel = viewModel,
            onBack = {}
        )
    }
}

@Preview(showBackground = true, name = "Historial Con Datos")
@Composable
fun HistorialConDatosPreview() {
    val viewModel = remember {
        ModeloVistaCalculadora().apply {
            clickBoton("1")
            clickBoton("+")
            clickBoton("2")
            clickBoton("=")
            clickBoton("5")
            clickBoton("×")
            clickBoton("3")
            clickBoton("=")
        }
    }
    CalulcadoraPrograacionMovilTheme {
        Historial(
            viewModel = viewModel,
            onBack = {}
        )
    }
}

