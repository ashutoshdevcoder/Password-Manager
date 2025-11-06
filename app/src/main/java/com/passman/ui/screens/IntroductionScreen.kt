package com.passman.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.passman.ui.customView.CustomTextView
import com.passman.ui.customView.OutlineRoundedButton
import com.passman.ui.theme.medium
import com.passman.ui.theme.regular
import com.passman.ui.theme.semiBold
import com.passman.viewModel.IntroEvent
import com.passman.viewModel.IntroductionViewModel

@Composable
fun IntroductionScreen(introductionViewModel: IntroductionViewModel?,
                       onClicked: (Screens?) -> Unit) {

    var pageNo by remember {  mutableIntStateOf(1)
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly) {
            Image(
                painter =
                when (pageNo) {
                    1 -> painterResource(R.drawable.intro_1)
                    2 -> painterResource(R.drawable.intro_2)
                    else -> painterResource(R.drawable.intro_3)
                },
                contentDescription = null,
                modifier = Modifier
                    .width(250.dp)
                    .height(250.dp)
            )
            CustomTextView(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),text = when(pageNo){
                        1 -> stringResource(R.string.manage_all_passwords)
                        2 -> stringResource(R.string.we_store_for_you)
                        else -> stringResource(R.string.save_your_cards)
                    },
                fontSize = 24.sp,
                fontFamily = medium,
                textAlign = TextAlign.Center,
                color = colorResource(R.color.colorPrimary)
            )
            CustomTextView(
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
                text = when(pageNo){
                    1 -> stringResource(R.string.intro_1_text)
                    2 -> stringResource(R.string.intro_2_text)
                    else -> stringResource(R.string.intro_3)
                },
                fontSize = 18.sp,
                fontFamily = regular,
                textAlign = TextAlign.Center,
                color = colorResource(R.color.colorPrimary)
            )

            OutlineRoundedButton(
                onClick = {
                    if(pageNo<3)
                        pageNo += 1
                    else
                    {
                        introductionViewModel?.onEvent(IntroEvent.SetIntroScreenSeen(true))
                        onClicked(Screens.LoginScreen)
                    }
                },
                modifier = Modifier.padding(16.dp)
                    .fillMaxWidth(),
                backgroundColor = colorResource(R.color.colorPrimary),
                textColor = Color.White,
                text = when(pageNo){
                    1 -> stringResource(R.string.continue_text)
                    2 -> stringResource(R.string.next)
                    else -> stringResource(R.string.lets_start)
                },
                fontSize = 14.sp,
                fontFamily = semiBold // Replace with your actual font
            )




        }
    }
}

@Composable
@Preview
fun IntroductionScreenPreview() {
    IntroductionScreen(null,{})
}