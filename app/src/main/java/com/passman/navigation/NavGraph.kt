package com.passman.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.passman.ui.screens.HomeContainerScreen
import com.passman.ui.screens.LoginScreen
import com.passman.ui.screens.PasswordScreen
import com.passman.ui.screens.SaveInfoScreen
import com.passman.ui.screens.Screens
import com.passman.viewModel.LoginViewModel
import com.passman.viewModel.SaveInfoViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()
    Scaffold { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screens.OnBoardingScreens,
            modifier = Modifier.padding(innerPadding),
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(300)
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(300)
                )
            }) {

            onBoardingNavGraph(navController)

             composable<Screens.HomeScreens>(enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(300)
                )
            }, exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(300)
                )
            }
            ) {
                HomeContainerScreen (onClicked = { screens ->
                    if(screens!=null)
                        navController.navigate(screens)
                    else
                        navController.navigateUp()
                })
            }
            composable<Screens.SaveInfoScreen>(enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(300)
                )
            }, exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(300)
                )
            }
            ) {
               val isAddCard = it.toRoute<Screens.SaveInfoScreen>().isAddCard
               val id = it.toRoute<Screens.SaveInfoScreen>().id
                val saveInfoViewModel:SaveInfoViewModel = hiltViewModel()
                SaveInfoScreen (isAddCard,id,saveInfoViewModel,onClicked = { screens ->
                    if(screens!=null)
                        navController.navigate(screens)
                    else
                        navController.navigateUp()
                })
            }
        composable<Screens.LoginScreen>(enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start,
                    tween(300)
                )
            }, exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End,
                    tween(300)
                )
            }
            ) {
                val loginViewModel : LoginViewModel = hiltViewModel()
                LoginScreen (loginViewModel,onClicked = { screens ->
                    if(screens!=null)
                        navController.navigate(screens){
                            popUpTo(0)
                        }
                    else
                        navController.navigateUp()
                })
            }


        }
    }
}

