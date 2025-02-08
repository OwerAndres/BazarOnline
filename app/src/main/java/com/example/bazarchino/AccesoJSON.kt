package com.example.bazarchino

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import java.io.IOException

/***Funcion la cual se encargara de acceder y leer el archivo json en nuestras carpetas
 * el json esta contruido de forma que hace referencia a un array, por lo cual la funcion
 * recorre el json y guarda cada producto en un array de productos, la cual proviene de nuestra data class
 * products**/

fun getProductFromJson(context: Context, fileName: String): List<Product>? {
    val jsonString: String
    try {
        Log.d("getProductFromJson", "Intentando abrir el archivo: $fileName")
        jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        Log.d("getProductFromJson", "Contenido del archivo: $jsonString")
    } catch (ioException: IOException) {
        Log.e("getProductFromJson", "Error al leer el archivo: $fileName", ioException)
        ioException.printStackTrace()
        return null
    }

    return try {
        val productList = Gson().fromJson(jsonString, ProductList::class.java)
        Log.d("getProductFromJson", "Producto parseado: $productList")
        productList.products
    } catch (e: Exception) {
        Log.e("getProductFromJson", "Error al parsear el JSON", e)
        null
    }
}