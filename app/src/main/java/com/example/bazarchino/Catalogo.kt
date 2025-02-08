package com.example.bazarchino

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.bazarchino.ui.theme.BazarChinoTheme
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.material3.Surface
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import android.util.Log
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController

@Composable
fun Header(text: String, onTextChanged: (String) -> Unit,navController: NavController){
    Column {
        Row(modifier = Modifier.padding(top = 30.dp).padding(start = 10.dp)){

            imgLogo(60,0){
                navController.navigate("main")
            }
            Buscador(padding = 10, circleRadius = 10, text = text, onTextChanged = onTextChanged)
        }
        textInfo(text)
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

@Composable
fun productImage(imagenURL: String, description: String){
   AsyncImage(
       model = imagenURL,
       contentDescription = description,
       modifier = Modifier
           .size(80.dp)
           .clip(RoundedCornerShape(50.dp))
   )
}

@Composable
fun textProduct(text: String, size:Int,){
    Text(text = text,
        modifier = Modifier
            .padding(start = 10.dp),
        style = TextStyle(
            fontSize = size.sp,
            fontWeight = FontWeight.Bold
        )
    )
}

@Composable
fun textDescription(text: String, size:Int){
        Text(text = text,
            modifier = Modifier
                .padding(start = 10.dp),
            style = TextStyle(
                fontSize = size.sp,
            )
        )
}

/**Funcion del rating de strellas el cual nos cargara la cantidad de estrellas en funcion
 * del rating de cada producto, usando icons para mostrar las estrellas**/
/**
 * La función StarRating(rating: Double, maxStars: Int) genera una fila de estrellas.
 * Las estrellas llenas se muestran si su posición es menor o igual al rating.
 * Se usan íconos de Icons.Filled.Star para estrellas llenas y Icons.Outlined.Star para vacías.
 * El color FFD700 es un clásico para estrellas doradas.**/
@Composable
fun StarRating(rating: Double, maxStars: Int = 5) {
    Row(modifier = Modifier.padding(start = 30.dp)) {
        for (i in 1..maxStars) {
            val filled = i <= rating
            Icon(
                imageVector = if (filled) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = "Star $i",
                tint = if (filled) Color(0xFFFFD700) else Color.Gray, // Oro y gris
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun catalogoScreen(searchQuery: String?,navController: NavController) {
    val scrollState = rememberScrollState()

    // Usamos un valor predeterminado si searchQuery es nulo o vacío
    val query = remember { mutableStateOf(searchQuery?.takeIf { it.isNotBlank() } ?: "") }

    Column(modifier = Modifier.verticalScroll(scrollState)) {
        // Pasamos el texto y el onTextChanged al Header
        Header(text = query.value, onTextChanged = { query.value = it }, navController = navController)

        // Si hay texto, mostramos los resultados
        if (query.value.isNotBlank()) {
            query.value
        }

        // Aquí se muestran los productos
        ProductDetails(searchQuery = query.value)
    }
}

@Composable
fun filtroProductos(products: List<Product>, param: String): List<Product>{
    //Convertiremos el paremetro de busqueda en misnusculas para evitar posibles errores
    val busqueda = param.trim().lowercase()

    return if (param.isBlank()) {
        products
    }else{
        products.filter { product ->

            product.title?.lowercase()?.contains(busqueda) ?: false ||
                    product.category?.lowercase()?.contains(busqueda) ?: false ||
                    product.brand?.lowercase()?.contains(busqueda) ?: false

        }
    }
}

/**Componente el cual accedera a la funcion de getProductFRomJson, y mostrara los atributos de los objetos
dentro del array de productos**/
@Composable
fun ProductDetails(searchQuery: String = "") {
    val context = LocalContext.current
    var products by remember { mutableStateOf<List<Product>?>(null) }

    LaunchedEffect(key1 = true) {
        products = getProductFromJson(context, "products.json")
    }

    Column(modifier = Modifier.padding(16.dp)) {
        if (products != null) {
            //llamo la funcion del filtro
            val filtro = filtroProductos(products!!,searchQuery)
            if (filtro.isNotEmpty()) {
                filtro.forEach { product ->
                    Column {
                        Spacer(modifier = Modifier.padding(10.dp))
                        Row {
                            productImage(product.thumbnail, product.title)
                            Column {
                                textProduct(product.title, 19)
                                textDescription(product.description, 10)
                                Row {
                                    textProduct("${product.price}€", 19)
                                    StarRating(product.rating.toDouble())
                                }
                            }
                        }
                    }
                }
            } else {
                Text(text = "No se encontraron productos.")
            }
        } else {
            Text(text = "Loading...")
        }
    }
}




/*
@Preview(showSystemUi = true)
@Composable
fun previw(){
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollState)) {
        Header()
        ProductDetails()
    }
}*/
