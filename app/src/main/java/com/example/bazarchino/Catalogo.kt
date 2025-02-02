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
import kotlin.math.sin
import android.content.Context
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import org.json.JSONObject
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.bazarchino.Product
import com.example.bazarchino.getProductFromJson
import coil.compose.AsyncImage





class Catalogo : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BazarChinoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProductDetails()
                }
            }
        }
    }
}


/**Componente el cual accedera a la funcion de getProductFRomJson, y mostrara los atributos de los objetos
dentro del array de productos**/
@Composable
fun ProductDetails() {
    val context = LocalContext.current
    var products by remember { mutableStateOf<List<Product>?>(null) }

    LaunchedEffect(key1 = true) {
        products = getProductFromJson(context, "products.json")
    }

    Column(modifier = Modifier.padding(16.dp)) {
        if (products != null) {
            products!!.forEach { product ->
                Text(text = "Title: ${product.title}")
                Text(text = "Description: ${product.description}")
                Text(text = "Price: ${product.price}")
                Text(text = "Rating: ${product.rating}")
                // ... muestra otras propiedades ...
            }
        } else {
            Text(text = "Loading...")
        }
    }
}

@Composable
fun viewproducts(){

}

@Composable
fun Header(){
    Column {
        Row(modifier = Modifier.padding(top = 16.dp).padding(start = 10.dp)){

            imgLogo(60,0)
            Buscador(10,10)
        }
        textInfo("smart")
    }

}

@Composable
fun textInfo(text: String){
    Text( "Resultados de búsqueda de \"$text\"",
        modifier = Modifier
            .padding(top = 16.dp)
            .padding(start = 10.dp),
        style = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        ))
}

@Preview(showSystemUi = true)
@Composable
fun previw(){
    //ProductDetails()
    Header()
}