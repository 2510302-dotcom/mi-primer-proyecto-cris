package com.example.miprimeraapp
import androidx.compose.foundation.layout.padding
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Text
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import kotlinx.coroutines.launch

import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.miprimeraapp.ui.theme.MiPrimeraAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiPrimeraAppTheme {

                // Así debe quedar tu bloque de setContent
                setContent {
                    MiPrimeraAppTheme {
                        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                            // LLAMA A LA FUNCIÓN ASÍ:
                            Greeting(modifier = Modifier.padding(innerPadding))
                        }
                    }
                }




                }
            }
        }
    }


@Composable
fun Greeting(modifier: Modifier = Modifier) {
    var nombre by remember { mutableStateOf("") }
    var matricula by remember { mutableStateOf("") }
    var asignatura by remember { mutableStateOf("") }
    var hora by remember { mutableStateOf("") }
    var fecha by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    )
    {


        Text(text = "Registro de Alumno", fontSize = 25.sp, color = Color.Blue)

        TextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre Completo") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = matricula,
            onValueChange = { matricula = it },
            label = { Text("Matrícula") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = asignatura,
            onValueChange = { asignatura = it },
            label = { Text("Asignatura") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = hora,
            onValueChange = { hora = it },
            label = { Text("Hora") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = fecha,
            onValueChange = { fecha = it },
            label = { Text("Fecha") },
            modifier = Modifier.fillMaxWidth()
        )
                Text(text = "Registro de Alumno", fontSize = 28.sp, color = Color.Cyan)
                Spacer(modifier = Modifier.height(20.dp))

                // 1. NOMBRE: Solo letras
                val nombreError =
                    nombre.isNotEmpty() && !nombre.all { it.isLetter() || it.isWhitespace() }
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre Completo") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = nombreError,
                    supportingText = {
                        if (nombreError) Text("Está mal: No se aceptan números", color = Color.Red)
                        else if (nombre.isNotEmpty()) Text("Está bien", color = Color.Green)
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))

                // 2. MATRÍCULA: Solo números
                val matError = matricula.isNotEmpty() && !matricula.all { it.isDigit() }
                OutlinedTextField(
                    value = matricula,
                    onValueChange = { matricula = it },
                    label = { Text("Matrícula") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = matError,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    supportingText = {
                        if (matError) Text("Eso está mal: Solo números", color = Color.Red)
                        else if (matricula.isNotEmpty()) Text("Está bien", color = Color.Green)
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))

                // 3. ASIGNATURA
                OutlinedTextField(
                    value = asignatura,
                    onValueChange = { asignatura = it },
                    label = { Text("Asignatura") },
                    modifier = Modifier.fillMaxWidth(),
                    supportingText = {
                        if (asignatura.isNotEmpty()) Text(
                            "Está bien",
                            color = Color.Green
                        )
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))

                // 4. HORA: Máximo 2 números después de los ":"
                val horaError = hora.isNotEmpty() && (
                        hora.substringAfter(":", "").filter { it.isDigit() }.length > 2 ||
                                hora.any { !it.isDigit() && it != ':' && !it.isWhitespace() && it.uppercase() !in "AMPM" }
                        )
                OutlinedTextField(
                    value = hora,
                    onValueChange = { hora = it },
                    label = { Text("Hora (Ej: 1:18 PM)") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = horaError,
                    supportingText = {
                        if (horaError) Text(
                            "Está mal: Revisa el formato o los números extras",
                            color = Color.Red
                        )
                        else if (hora.isNotEmpty()) Text("Está bien", color = Color.Green)
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))

                // 5. FECHA: Solo números y diagonales
                val fechaError = fecha.isNotEmpty() && fecha.any { it.isLetter() }
                OutlinedTextField(
                    value = fecha,
                    onValueChange = { input ->
                        val digits = input.filter { it.isDigit() }
                        if (digits.length <= 8) {
                            fecha = when {
                                digits.length >= 5 -> "${digits.take(2)}/${
                                    digits.substring(
                                        2,
                                        4
                                    )
                                }/${digits.substring(4)}"

                                digits.length >= 3 -> "${digits.take(2)}/${digits.substring(2)}"
                                else -> input
                            }
                        } else {
                            fecha = input
                        }
                    },
                    label = { Text("Fecha (Solo números)") },
                    modifier = Modifier.fillMaxWidth(),
                    isError = fechaError,
                    supportingText = {
                        if (fechaError) Text(
                            "Está mal: Fecha es con números, no letras",
                            color = Color.Red
                        )
                        else if (fecha.length >= 10) Text("Está bien", color = Color.Green)
                    }
                )

                Spacer(modifier = Modifier.height(30.dp))

                Button(
                    onClick = {

                        scope.launch {

                            snackbarHostState.showSnackbar(message = "¡Datos registrados con éxito!")
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(55.dp),
                    enabled = true,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE91E63))
                ) {
                    Text(text = "Guardar Formulario", color = Color.White)
                }
            }
        }

