package io.github.xinkev.movies.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import io.github.xinkev.movies.ui.screens.home.HomeScreen
import io.github.xinkev.movies.ui.screens.details.MovieDetailsScreen

@Composable
fun MainNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable(route = "home") {
            HomeScreen(
                goToDetails = { movieId -> navController.navigate("movie/$movieId") },
                vm = hiltViewModel()
            )
        }
        composable(
            route = "movie/{movieId}",
            arguments = listOf(
                navArgument("movieId") { type = NavType.LongType }
            )
        ) { backstackEntry ->
            MovieDetailsScreen(
                backstackEntry.arguments?.getLong("movieId"),
                vm = hiltViewModel(),
                goBack = { navController.navigateUp() }
            )
        }
    }
}