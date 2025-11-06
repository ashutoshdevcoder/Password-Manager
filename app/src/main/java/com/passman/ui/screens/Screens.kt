package com.passman.ui.screens

import kotlinx.serialization.Serializable

sealed class Screens {

    @Serializable
    data object OnBoardingScreens : Screens() {
        @Serializable
        data object IntroductionScreen : Screens()

        @Serializable
        data object SplashScreen : Screens()
    }

    @Serializable
    data object LoginScreen : Screens()

    @Serializable
    data class SaveInfoScreen(val isAddCard:Boolean,val id:Int?=null) : Screens()

    @Serializable
    data object HomeScreens : Screens() {

        @Serializable
        data object PasswordScreen : Screens()

        @Serializable
        data object CardScreen : Screens()

    }

}