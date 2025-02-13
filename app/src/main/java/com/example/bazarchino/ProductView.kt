package com.example.bazarchino

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage


@Composable
fun productHeadder(
    text: String,
    onTextChanged: (String) -> Unit,
    navController: NavController,
    showInfoText: Boolean = true // Nuevo parámetro para controlar el texto debajo
) {
    Column(modifier = Modifier.padding(top = 20.dp)) {
        Row(modifier = Modifier.padding(top = 30.dp).padding(start = 10.dp)) {
            imgLogo(60, 0) {
                navController.navigate("main")
            }
            Buscador(padding = 10, circleRadius = 10, text = text, onTextChanged = onTextChanged)
        }

        // Mostrar el texto solo si `showInfoText` es verdadero
        if (showInfoText) {
            textInfo(text)
        }
    }
}


@Composable
fun imgagesURL(size: Int,Paddingstart: Int,Paddintop: Int,round: Int,URL: String) {
     AsyncImage(
         model = URL,
         contentDescription = "Imagen",
         modifier = Modifier
             .padding(5.dp)
             .clip(RoundedCornerShape(round.dp))
             .size(size.dp)
             .padding(start = Paddingstart.dp)
             .padding(top = Paddintop.dp)
     )
}

@Composable
fun buttonCompra(with: Int, height: Int, padding: Int, navController: NavController, product: Product) {
    Button(
        onClick = {
            val titleEncoded = product.title.replace(" ", "%20")  // Reemplaza espacios
            val priceEncoded = product.price.toString()
            val descriptionEncoded = product.description.replace(" ", "%20")

            navController.navigate("DatosScreenInfo/$titleEncoded/$priceEncoded/$descriptionEncoded")
        },
        modifier = Modifier
            .width(with.dp)
            .height(height.dp)
            .padding(start = padding.dp)
            .shadow(5.dp, shape = MaterialTheme.shapes.medium),
        colors = ButtonDefaults.buttonColors(Color(0xFF1F2AC5))
    ) {
        Text(text = "Comprar")
    }
}


@Composable
fun producScreen(productId: String?, navController: NavController) {
    val context = LocalContext.current
    var products by remember { mutableStateOf<List<Product>?>(null) }
    var searchText by remember { mutableStateOf("") } // Agregar estado para el texto

    // Carga los productos de forma asíncrona
    LaunchedEffect(key1 = true) {
        products = getProductFromJson(context, "products.json")
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        // Incluye el encabezado con el estado del buscador
        productHeadder(
            text = searchText,
            onTextChanged = { newText -> searchText = newText }, // Ahora se actualiza correctamente
            navController = navController,
            showInfoText = false // No mostrar texto debajo del buscador en esta pantalla
        )

        // Verificación de productos
        if (products != null) {
            val filtro = filtroProductos(products!!, productId = productId)
            if (filtro.isNotEmpty()) {
                val product = filtro.first()
                Column(modifier = Modifier
                    .padding(16.dp)
                    .padding(top = 40.dp)) {
                    Row(modifier = Modifier.padding(start = 40.dp)) {
                        productImage(230, 200, product.thumbnail, product.title) {}
                        Column {
                            product.images.forEach { images ->
                                imgagesURL(70, 10, 10, 50, images)
                            }
                        }
                    }
                    Column(modifier = Modifier.padding(top = 20.dp)) {
                        textProduct(product.title, 19, 40)
                        Row(modifier = Modifier
                            .padding(top = 20.dp)
                            .padding(bottom = 20.dp)) {

                            Column {
                                textProduct("${product.price}€", 25, 40)
                                textProduct("${product.stock} disponibles", 10, 60)
                            }
                            StarRating(product.rating.toDouble())
                        }
                        textDescription(product.description, 18)
                        Spacer(modifier = Modifier.padding(top = 70.dp))
                        buttonCompra(330, 35, 50,navController,product)
                    }
                }
            } else {
                Text(text = "Producto no encontrado.")
            }
        } else {
            Text(text = "Cargando producto...")
        }
    }
}




