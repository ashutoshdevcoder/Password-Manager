package com.passman.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.passman.R
import com.passman.helper.isTrue
import com.passman.ui.customView.CustomTextView
import com.passman.ui.theme.medium
import com.passman.viewModel.SplashViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(splashViewModel: SplashViewModel?,onClicked: (Screens?) -> Unit){

    LaunchedEffect(key1 = Unit) {
        delay(2000)
        if (splashViewModel?.redirectToLogin?.value.isTrue())
            onClicked(Screens.LoginScreen)
        else
            onClicked(Screens.OnBoardingScreens.IntroductionScreen)
    }


    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround) {
            Image(
                painter = painterResource(R.drawable.intro_1),
                contentDescription = null,
                modifier = Modifier
                    .width(250.dp)
                    .height(250.dp)
            )
            CustomTextView(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),text = stringResource(R.string.app_name)
                    ,
                fontSize = 24.sp,
                fontFamily = medium,
                textAlign = TextAlign.Center,
                color = colorResource(R.color.colorPrimary)
            )
        }
    }
}

@Composable
@Preview
fun SplashScreenPreview(){
    SplashScreen(null,{})
}