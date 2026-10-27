package com.craos6518.calulcadoraprograacionmovil

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.craos6518.calulcadoraprograacionmovil.ui.theme.CalculatorControlColor
import com.craos6518.calulcadoraprograacionmovil.ui.theme.CalculatorNumberColor
import com.craos6518.calulcadoraprograacionmovil.ui.theme.CalculatorOperatorColor

val botonesLista = listOf(
    "C", "AC", "%", "÷",
    "7", "8", "9", "×",
    "4", "5", "6", "-",
    "1", "2", "3", "+",
    "+/-", "0", ",", "="
)

@Composable
fun Calculadora(
    modifier: Modifier = Modifier,
    viewModel: ModeloVistaCalculadora,
    onOpenHistory: () -> Unit = {}
) {
    val ecuacionText by viewModel.ecuacionText.observeAsState("")
    val resultadosText by viewModel.resultadosText.observeAsState("0")
    val hayError by viewModel.isError.observeAsState(false)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.End
    ) {
        // Top Bar con botón de Historial
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(onClick = onOpenHistory) {
                Text(text = stringResource(R.string.calculator_history))
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        // 1. Vista de la ECUACIÓN / OPERACIÓN
        Text(
            text = ecuacionText,
            style = TextStyle(
                fontSize = 32.sp,
                textAlign = TextAlign.End,
            ),
            maxLines = 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 2. Vista del RESULTADO o ERROR
        Text(
            text = if (hayError) {
                stringResource(R.string.error_matematico)
            } else {
                resultadosText
            },
            style = TextStyle(
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 3. Grid de Botones
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(botonesLista) { btn ->
                CalculadoraBoton(
                    btn = btn,
                    clickBoton = { viewModel.clickBoton(btn) }
                )
            }
        }
    }
}

@Composable
fun CalculadoraBoton(btn: String, clickBoton: () -> Unit) {
    Box(
        modifier = Modifier
            .aspectRatio(1f)
            .padding(2.dp)
    ) {
        FloatingActionButton(
            onClick = clickBoton,
            modifier = Modifier.fillMaxSize(),
            shape = CircleShape,
            containerColor = obtenerColor(btn),
            contentColor = Color.White
        ) {
            Text(
                text = btn,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

fun obtenerColor(btn: String): Color {
    return when (btn) {
        "C", "AC", "%" -> CalculatorControlColor
        "÷", "×", "-", "+", "=" -> CalculatorOperatorColor
        else -> CalculatorNumberColor
    }
}
