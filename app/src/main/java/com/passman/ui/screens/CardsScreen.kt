package com.passman.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.passman.R
import com.passman.models.CardInfo
import com.passman.models.PasswordInfo
import com.passman.ui.customView.CustomTextView
import com.passman.ui.theme.bold
import com.passman.ui.theme.darkBlue
import com.passman.ui.theme.regular
import com.passman.ui.theme.semiBold
import com.passman.viewModel.CardEvent
import com.passman.viewModel.CardViewModel
import com.passman.viewModel.PasswordEvent

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun CardsScreen(cardViewModel: CardViewModel?,onClicked: (Screens?) -> Unit){

    val cardListState = cardViewModel?.getCardInfoState?.collectAsStateWithLifecycle()
    val cardListData = remember { derivedStateOf { cardListState?.value } }

    LaunchedEffect(true) {
        cardViewModel?.onEvent(CardEvent.GetCardList)
    }

    Box(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxSize()
    ) {
        LazyColumn {
            item {
                CustomTextView(
                    modifier = Modifier.fillMaxSize().background(
                        color = colorResource(
                            R.color.dark_blue_30
                        ), shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                    ).padding(horizontal = 20.dp, vertical = 20.dp),
                    text = "Cards",
                    fontSize = 24.sp,
                    fontFamily = semiBold,
                )
            }
            item {
                Spacer(modifier = Modifier.size(5.dp))
            }
            items(cardListData.value.orEmpty(), key = {it._id}) { cardInfo ->
                val dismissState = rememberDismissState()

                if (dismissState.isDismissed(DismissDirection.StartToEnd)) {
                    cardViewModel?.onEvent(CardEvent.DeleteCardItem(cardInfo._id))
                } else if (dismissState.isDismissed(DismissDirection.EndToStart)) {
                    cardViewModel?.onEvent(CardEvent.DeleteCardItem(cardInfo._id))
                }
                SwipeToDismiss(state = dismissState,
                    modifier = Modifier.animateItem(),
                    directions = setOf(DismissDirection.StartToEnd, DismissDirection.EndToStart),
                    background = {
                        val direction = dismissState.dismissDirection
                        val color by animateColorAsState(
                            when (direction) {
                                DismissDirection.StartToEnd -> Color.Red
                                DismissDirection.EndToStart -> Color.Red
                                null -> Color.Transparent
                            }
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(16.dp),
                            contentAlignment = when (direction) {
                                DismissDirection.StartToEnd -> Alignment.CenterStart
                                DismissDirection.EndToStart -> Alignment.CenterEnd
                                null -> Alignment.Center
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Delete,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                    }
                ) {
                    ItemView(onClicked,cardInfo)
                }

            }

        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(20.dp),
            onClick = {
                onClicked(Screens.SaveInfoScreen(true))
            },
            contentColor = Color.White,
            backgroundColor = darkBlue
        ) {
            Icon(Icons.Filled.Add, "")
        }
    }
}

@Composable
private fun ItemView(
    onClicked: (Screens?) -> Unit,
    it: CardInfo
) {
    Column {
        Row(modifier = Modifier.background(Color.White)
            .clickable {
                onClicked(Screens.SaveInfoScreen(true, it._id))
            }
            .padding(horizontal = 10.dp)) {
            CustomTextView(
                modifier = Modifier
                    .drawBehind {
                        drawCircle(color = darkBlue)
                    }
                    .padding(15.dp),
                text = it.bankName?.first().toString(),
                fontSize = 28.sp,
                fontFamily = semiBold,
                color = Color.White
            )
            Column {
                CustomTextView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, top = 15.dp),
                    text = it.bankName.orEmpty(),
                    fontSize = 18.sp,
                    fontFamily = bold,
                )
                CustomTextView(
                    modifier = Modifier
                        .padding(start = 10.dp, bottom = 15.dp)
                        .fillMaxWidth(),
                    text = it.cardNumber.orEmpty(),
                    fontSize = 18.sp,
                    fontFamily = regular,
                )
            }
        }
        HorizontalDivider(thickness = 1.dp, color = colorResource(R.color.colorGreyE0),)
    }

}

@Composable
@Preview
fun CardsScreenPreview(){
    CardsScreen(null,{})
}