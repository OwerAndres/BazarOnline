package com.example.bazarchino

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@Composable
fun appNavigation(){
    // Inicializo  el controlador de navegación
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        // Defino la ruta principal (ruta de inicio)
        composable(route = "main") {
            MainScreen(navController)
        }
        // Defino una ruta la cual se espera que se envie con un argumento para realizar la busqueda en el catalggo
        composable(
            route = "catalogo/{searchQuery}",
            arguments = listOf(navArgument("searchQuery") { type = NavType.StringType })
        ) { backStackEntry ->
            // guardo la consulta de búsqueda de los argumentos de navegación,
            // usando una cadena vacía en caso de que sea nulo
            val searchQuery = backStackEntry.arguments?.getString("searchQuery") ?: ""
            catalogoScreen(searchQuery = searchQuery, navController = navController)
        }
        // Define una ruta para el catálogo sin parámetros de búsqueda
        composable(route = "catalogo") {
            catalogoScreen(searchQuery = "", navController = navController)
        }

        composable(
            route = "productView/{productId}",
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        )
        {backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: ""
            producScreen(productId = productId, navController = navController)
        }

        composable(route = "producstView"){
            producScreen(productId = "",navController = navController)
        }

        composable(
            route = "DatosScreenInfo/{title}/{price}/{description}",
            arguments = listOf(
                navArgument("title") { type = NavType.StringType },
                navArgument("price") { type = NavType.StringType },
                navArgument("description") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val title = backStackEntry.arguments?.getString("title")?.replace("%20", " ") ?: ""
            val price = backStackEntry.arguments?.getString("price") ?: ""
            val description = backStackEntry.arguments?.getString("description")?.replace("%20", " ") ?: ""

            Datos(title, price, description, navController)
        }


    }
}
