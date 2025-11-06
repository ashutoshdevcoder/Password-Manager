package com.passman.ui.screens

import androidx.annotation.DrawableRes
import com.passman.R

sealed class HomeBottomNav(val screen: Screens, @DrawableRes val unSelectedIcon: Int, @DrawableRes val selectedIcon: Int, val label: String) {
    data object Home : HomeBottomNav(Screens.HomeScreens.PasswordScreen, R.drawable.pass_unselected,R.drawable.pass_selected,"Passwords")
    data object Profile : HomeBottomNav(Screens.HomeScreens.CardScreen, R.drawable.card_unseleced,R.drawable.card_selected,"Cards")
}