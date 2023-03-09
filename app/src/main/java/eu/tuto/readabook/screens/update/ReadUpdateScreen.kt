package eu.tuto.readabook.screens.update

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import eu.tuto.readabook.screens.Home.HomeViewModel

@Composable
fun UpdateScreen(
    navController: NavHostController,
    bookItemId: String,
    viewModel: HomeViewModel = hiltViewModel()
) {
}