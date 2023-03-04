package eu.tuto.readabook.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import eu.tuto.readabook.screens.CreateAccount.CreateAccount
import eu.tuto.readabook.screens.Home.HomeScreen
import eu.tuto.readabook.screens.SplashScreen
import eu.tuto.readabook.screens.details.BookDetailsScreen
import eu.tuto.readabook.screens.login.LoginScreen
import eu.tuto.readabook.screens.search.SearchScreen
import eu.tuto.readabook.screens.search.SearchViewModel
import eu.tuto.readabook.screens.stats.StatsScreen
import eu.tuto.readabook.screens.update.UpdateScreen


@Composable
fun ReadNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ReadScreens.SplashScreen.name) {
        //SplashScreen
        composable(route = ReadScreens.SplashScreen.name) {
            SplashScreen(navController = navController)
        }
        //HomeScreen
        composable(route = ReadScreens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }
        //SearchScreen
        composable(route = ReadScreens.SearchScreen.name) {
            val viewModel = hiltViewModel<SearchViewModel>()
            SearchScreen(navController = navController, viewModel = viewModel)
        }
        //DetailScreen
        composable(route = ReadScreens.DetailScreen.name) {
            BookDetailsScreen(navController = navController)
        }
        //StatsScreen
        composable(route = ReadScreens.StatsScreen.name) {
            StatsScreen(navController = navController)
        }
        //UpdateScreen
        composable(route = ReadScreens.UpdateScreen.name) {
            UpdateScreen(navController = navController)
        }
        //LoginScreen
        composable(route = ReadScreens.LoginScreen.name) {
            LoginScreen(navController = navController)
        }
        //CreateAccount
        composable(route = ReadScreens.CreateAccountScreen.name) {
            CreateAccount(navController = navController)
        }
    }
}