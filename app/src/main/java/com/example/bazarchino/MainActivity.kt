package com.example.bazarchino

import android.os.Bundle
import android.provider.MediaStore.Audio.Radio
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BazarChinoTheme {
                inicio()
            }
        }
    }
}

//Imagen
@Composable
fun imgLogo(){

    Image(painterResource(id = R.drawable.cochebazar),
        "logo del bazar",
        modifier = Modifier
            .size(200.dp)
            .wrapContentSize(Alignment.Center)
            .padding(start = 60.dp),
        contentScale = ContentScale.Crop,)
}

//input del buscador
@Composable
fun Buscador() {
    var text by remember { mutableStateOf("") }

        Spacer(modifier = Modifier.padding(10.dp))
        TextField(modifier = Modifier
            .background(androidx.compose.ui.graphics.Color.Gray),
            value = text,
            onValueChange = { text = it },
            placeholder = { Text("Laptos, Smarphones, ...")}
        )
}

//Titulo del buscador
@Composable
fun TextoTitle(){
    Text(text = "Bazar Online",
        modifier = Modifier
            .padding(start = 60.dp),
        fontSize = 30.sp)
}

//Boton buscar
@Composable
fun buscarButton(){
    Button(onClick = { /*TODO*/ },
        modifier = Modifier
            .width(230.dp)
            .height(35.dp)
            .padding(start = 60.dp)){
        Text(text = "Buscar")
    }
}

@Composable
fun inicio(){
    Column(modifier = Modifier
        .fillMaxSize()
        .wrapContentSize(Alignment.Center)) {

        imgLogo()
        TextoTitle()
        Buscador()
        Spacer(modifier = Modifier.padding(10.dp))
        buscarButton()

    }
}

@Preview(showSystemUi = true)
@Composable
fun preview() {
    inicio()
}