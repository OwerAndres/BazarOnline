package com.example.bazarchino


//Array de productos la cual guardara cada uno de los productos del archivo json
data class ProductList(
    val products: List<Product>
)


//Objeto producto
data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val discountPercentage: Double,
    val rating: Double,
    val stock: Int,
    val brand: String?,
    val category: String,
    val thumbnail: String,
    val images: List<String>
)
