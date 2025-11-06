package com.passman.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.passman.ui.screens.IntroductionScreen
import com.passman.ui.screens.Screens
import com.passman.ui.screens.SplashScreen
import com.passman.viewModel.IntroductionViewModel
import com.passman.viewModel.SplashViewModel

fun NavGraphBuilder.onBoardingNavGraph(navController: NavHostController){
    navigation<Screens.OnBoardingScreens>(startDestination = Screens.OnBoardingScreens.SplashScreen) {
        composable<Screens.OnBoardingScreens.SplashScreen>(enterTransition = {
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
            val splashViewModel: SplashViewModel = hiltViewModel()
            SplashScreen(splashViewModel, onClicked = { screens ->
                if(screens!=null)
                    navController.navigate(screens){
                        popUpTo(0)
                    }
                else
                    navController.navigateUp()
            })
        }
        composable<Screens.OnBoardingScreens.IntroductionScreen>(enterTransition = {
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
            val introductionViewModel: IntroductionViewModel = hiltViewModel()
            IntroductionScreen(introductionViewModel, onClicked = { screens ->
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