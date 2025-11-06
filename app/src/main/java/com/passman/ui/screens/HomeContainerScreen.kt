package com.passman.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.passman.navigation.HomeNavHost
import com.passman.ui.customView.CustomTextView
import com.passman.ui.theme.darkBlue
import com.passman.ui.theme.darkBlue77
import com.passman.ui.theme.medium

@Composable
fun HomeContainerScreen(onClicked: (Screens?) -> Unit){
    val navController = rememberNavController()


    val bottomNavigationItems = listOf(
        HomeBottomNav.Home,
        HomeBottomNav.Profile,
    )

    Scaffold (bottomBar = {
        BottomNavigation(
            backgroundColor = Color.White,
            elevation = 2.dp
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination?.route
                ?: Screens.HomeScreens.PasswordScreen::class.qualifiedName.orEmpty()
            bottomNavigationItems.forEach { item ->
                val selected = currentDestination == item.screen::class.qualifiedName
                BottomNavigationItem(
                    selected = selected,
                    onClick = {
                        navController.navigate(item.screen){popUpTo(0)}
                    },
                    label = {
                        CustomTextView(
                            text = item.label,
                            fontSize = 14.sp,
                            color = if (selected) darkBlue else darkBlue77,
                            fontFamily = medium
                        )
                    },
                    icon = {
                        Image(
                            painter = if (selected) painterResource(id = item.selectedIcon) else painterResource(
                                id = item.unSelectedIcon
                            ), contentDescription = ""
                        )
                    })

            }

        }
    }){ innerPadding ->
        Box(modifier = Modifier.padding(innerPadding))
        {
            HomeNavHost(navController,onClicked)
        }


    }
}