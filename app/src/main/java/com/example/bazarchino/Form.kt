package com.example.bazarchino

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import okhttp3.Response
import retrofit2.Call
import com.example.bazarchino.ApiResponse



@Composable
fun BoxTextForm(
    placeholder: String,
    padding: Int,
    paddingStar: Int,
    circleRadius: Int
) {
    var text by remember { mutableStateOf("") }

    Spacer(modifier = androidx.compose.ui.Modifier.padding(padding.dp).padding(start = paddingStar.dp))

    TextField(
        modifier = androidx.compose.ui.Modifier
            .clip(RoundedCornerShape(circleRadius.dp)),
        value = text,
        onValueChange = { text = it }, // Ahora el texto se actualiza correctamente
        placeholder = { Text(placeholder) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.LightGray,
            unfocusedContainerColor = Color.LightGray,
            disabledContainerColor = Color.LightGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true
    )
}



@Composable
fun buttonEnviar(width: Int, height: Int, padding: Int) {
    val context = LocalContext.current // Obtener el contexto actual

    Button(
        onClick = {
            // Aquí obtienes los valores de los campos de entrada
            val nombre = "Juan" // Cambia estos valores con los datos de los campos de texto
            val apellido = "Perez"
            val correo = "owerandres611@gmail.com"
            val direccion = "Calle Falsa 123"
            val dni = "12345678"
            val producto = "Producto A"
            val precio = "10.99"
            val descripcion = "Descripcion del producto A"

            // Crear el objeto FormData
            val formData = FormData(nombre, apellido, correo, direccion, dni, producto, precio, descripcion)

            // Realizar la solicitud HTTP
            RetrofitInstance.api.sendFormData(formData).enqueue(object : retrofit2.Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: retrofit2.Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        Log.d("RetrofitSuccess", "Respuesta exitosa: ${response.body()}")
                    } else {
                        val errorBody = response.errorBody()?.string() ?: "Error desconocido"
                        Log.e("RetrofitError", "Código: ${response.code()}, Error: $errorBody")
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.e("RetrofitError", "Error de conexión: ${t.message}", t)
                    Toast.makeText(context, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()

                }
            })
        },
        modifier = androidx.compose.ui.Modifier
            .width(width.dp)
            .height(height.dp)
            .padding(start = padding.dp)
            .shadow(5.dp, shape = MaterialTheme.shapes.medium),
        colors = ButtonDefaults.buttonColors(Color(0xFF1F2AC5))
    ) {
        Text(text = "Comprar")
    }
}




@Composable
fun Datos(title: String, price: String, description: String, navController: NavController) {
    Column {
        Spacer(modifier = androidx.compose.ui.Modifier.padding(top = 20.dp))
        Row(modifier = androidx.compose.ui.Modifier.padding(top = 20.dp).padding(start = 140.dp)) {
            imgLogo(100, 0) { navController.navigate("main") }
        }
        Row {
            Column(modifier = androidx.compose.ui.Modifier.padding(start = 60.dp).padding(top = 20.dp)) {
                Text("Producto: $title")
                Text("Precio: $price €")

                Spacer(modifier = androidx.compose.ui.Modifier.padding(10.dp))
                BoxTextForm("Nombre", 0, 0, 10)
                Spacer(modifier = androidx.compose.ui.Modifier.padding(10.dp))
                BoxTextForm("Apellido", 0, 0, 10)
                Spacer(modifier = androidx.compose.ui.Modifier.padding(10.dp))
                BoxTextForm("Correo", 0, 0, 10)
                Spacer(modifier = androidx.compose.ui.Modifier.padding(10.dp))
                BoxTextForm("Dirección", 0, 0, 10)
                Spacer(modifier = androidx.compose.ui.Modifier.padding(10.dp))
                BoxTextForm("DNI", 0, 0, 10)
            }
        }
        Spacer(modifier = androidx.compose.ui.Modifier.padding(30.dp))
        buttonEnviar(330, 35, 70)
    }
}
