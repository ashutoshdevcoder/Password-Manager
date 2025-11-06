package com.passman.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.passman.ui.screens.CardsScreen
import com.passman.ui.screens.PasswordScreen
import com.passman.ui.screens.Screens
import com.passman.viewModel.CardViewModel
import com.passman.viewModel.PasswordViewModel

@Composable
fun HomeNavHost(navController: NavHostController,onClicked: (Screens?) -> Unit) {
    NavHost (navController = navController, startDestination = Screens.HomeScreens.PasswordScreen) {
        composable<Screens.HomeScreens.PasswordScreen> {
            val passwordViewModel:PasswordViewModel = hiltViewModel()
            PasswordScreen(passwordViewModel,onClicked)
        }
        composable<Screens.HomeScreens.CardScreen> {
            val cardViewModel:CardViewModel = hiltViewModel()
            CardsScreen(cardViewModel,onClicked)
        }

    }
}
