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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
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

/**
 * Función composable que define el encabezado (header) de la aplicación.
 * Este encabezado contiene un logo, una barra de búsqueda y un texto informativo.
 *
 * @param text El texto actual que se muestra o se usa en la barra de búsqueda.
 * @param onTextChanged Un callback que se invoca cuando el texto en la barra de búsqueda cambia.
 * @param navController El NavController utilizado para la navegación entre pantallas.
 */
@Composable
fun Header(text: String, onTextChanged: (String) -> Unit,navController: NavController){
    Column(modifier = Modifier.padding(top = 20.dp)) {
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
fun productImage(size: Int,round: Int,imagenURL: String, description: String, onClick: () -> Unit){
   AsyncImage(
       model = imagenURL,
       contentDescription = description,
       modifier = Modifier
           .size(size.dp)
           .clip(RoundedCornerShape(round.dp))
           .clickable { onClick() }
   )
}

@Composable
fun textProduct(text: String, size:Int,paddingStart: Int){
    Text(text = text,
        modifier = Modifier
            .padding(start = paddingStart.dp),
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
        ProductDetails(searchQuery = query.value,navController)
    }
}

@Composable
        /**
         * Función que filtra una lista de productos en función de un parámetro de búsqueda.
         *
         * @param products La lista de productos a filtrar.
         * @param param El parámetro de búsqueda. Puede ser el título, la categoría o la marca del producto.
         * @return Una nueva lista de productos que coinciden con el parámetro de búsqueda.
         *         Si el parámetro de búsqueda está vacío, devuelve la lista completa de productos.
         */
fun filtroProductos(products: List<Product>, param: String? = null, productId: String? = null): List<Product> {
    return when {
        productId != null -> products.filter { product -> product.id == productId.toInt()}
        !param.isNullOrBlank() -> {
            val busqueda = param.trim().lowercase()
            products.filter { product ->
                product.title?.lowercase()?.contains(busqueda) ?: false ||
                        product.category?.lowercase()?.contains(busqueda) ?: false ||
                        product.brand?.lowercase()?.contains(busqueda) ?: false
            }
        }
        else -> products
    }
}


/**Componente el cual accedera a la funcion de getProductFRomJson, y mostrara los atributos de los objetos
dentro del array de productos**/
/**
 * Función composable que muestra los detalles de los productos.
 * Permite filtrar los productos en función de una consulta de búsqueda.
 *
 * @param searchQuery La consulta de búsqueda para filtrar los productos.
 *                    Si está vacía, se muestran todos los productos.
 */
@Composable
fun ProductDetails(searchQuery: String = "", navController: NavController) {
    // Obtiene el contexto actual de la aplicación
    val context = LocalContext.current

    // Variable de estado para almacenar la lista de productos. Inicialmente es nula.
    var products by remember { mutableStateOf<List<Product>?>(null) }

    // Efecto secundario que se ejecuta una sola vez al iniciar el composable.
    LaunchedEffect(key1 = true) {
        // Carga los productos desde el archivo JSON.
        products = getProductFromJson(context, "products.json")
    }

    // Columna principal que contiene la lista de productos.
    Column(modifier = Modifier.padding(16.dp)) {
        // Comprueba si la lista de productos no es nula.
        if (products != null) {
            // Llama a la función de filtro para obtener los productos filtrados.
            val filtro = filtroProductos(products!!, searchQuery)

            // Comprueba si la lista de productos filtrados no está vacía.
            if (filtro.isNotEmpty()) {
                // Itera sobre cada producto en la lista filtrada.
                filtro.forEach { product ->
                    // Columna para cada producto.
                    Column {
                        // Espacio vertical entre productos.
                        Spacer(modifier = Modifier.padding(10.dp))
                        // Fila que contiene la imagen y la información del producto.
                        Row {
                            // Muestra la imagen del producto.
                            productImage(80,50,product.thumbnail, product.title){
                                navController.navigate("productView/${product.id}")
                            }
                            // Columna para la información del producto.
                            Column {
                                // Muestra el título del producto.
                                textProduct(product.title, 19,10)
                                // Muestra la descripción del producto.
                                textDescription(product.description, 10)
                                // Fila para el precio y la calificación del producto.
                                Row {
                                    // Muestra el precio del producto.
                                    textProduct("${product.price}€", 19,10)
                                    // Muestra la calificación del producto.
                                    StarRating(product.rating.toDouble())
                                }
                            }
                        }
                    }
                }
            } else {
                // Muestra un mensaje si no se encontraron productos.
                Text(text = "No se encontraron productos.")
            }
        } else {
            // Muestra un mensaje de carga mientras se cargan los productos.
            Text(text = "Loading...")
        }
    }
}




/*
@Preview(showSystemUi = true)
@Composable-
fun previw(){
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollState)) {
        Header()
        ProductDetails()
    }
}*/
