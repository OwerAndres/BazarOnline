package com.example.bazarchino

import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.bazarchino.ui.theme.BazarChinoTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlin.math.sin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           appNavigation()
        }
    }
}

@Composable
fun MainScreen(navController: NavController){
 inicio(navController)
}

//Imagen
@Composable
fun imgLogo(size: Int,padding: Int,onClick: () -> Unit){

    Image(painterResource(id = R.drawable.cochebazar),
        "logo del bazar",
        modifier = Modifier
            .size(size.dp)
            .wrapContentSize(Alignment.Center)
            .padding(start = padding.dp)
            .clickable { onClick() },
        contentScale = ContentScale.Crop,)
}

//input del buscador
@Composable
fun Buscador(padding: Int, circleRadius: Int, text: String, onTextChanged: (String) -> Unit) {
    Spacer(modifier = Modifier.padding(padding.dp))

    TextField(
        modifier = Modifier
            .clip(RoundedCornerShape(circleRadius.dp)),
        value = text,
        onValueChange = { onTextChanged(it) }, // Llamar al callback para actualizar el texto
        placeholder = { Text("Laptops, Smartphones, ...") },
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

//Titulo del buscador
@Composable
fun TextoTitle(text: String){
    Text(text = text,
        modifier = Modifier
            .padding(start = 60.dp),
        style = TextStyle(
            fontSize = 30.sp),
            fontWeight = FontWeight.Bold,
        )
}

//Boton buscar
@Composable
fun buscarButton(navController: NavController, text: String) {
    Button(onClick = {
        if (text.isNotBlank()) {
            navController.navigate("catalogo/$text") // Si el texto no está vacío, pasamos el texto como parámetro
        } else {
            navController.navigate("catalogo") // Si está vacío, navegamos sin parámetros
        }
    },
        modifier = Modifier
            .width(230.dp)
            .height(35.dp)
            .padding(start = 60.dp)
            .shadow(10.dp, shape = MaterialTheme.shapes.medium),
        colors = ButtonDefaults.buttonColors(Color(0xFF000000))) {
        Text(text = "Buscar")
    }
}

@Composable
fun inicio(navController: NavController){
    var text by remember { mutableStateOf("") }
    Column(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)) {

        imgLogo(200,60){
            navController.navigate("main")
        }
        TextoTitle("Bazar online")
        Buscador(padding = 10, circleRadius = 10, text = text, onTextChanged = { text = it })
        Spacer(modifier = Modifier.padding(10.dp))
        buscarButton(navController = navController, text = text)

    }
}

@Preview(showSystemUi = true)
@Composable
fun preview() {
    appNavigation()
}