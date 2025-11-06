package com.passman.ui.customView

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import com.passman.R
import com.passman.ui.theme.regular

@Composable
fun CustomButton(
    onClick: () -> Unit,
    modifier: Modifier?=Modifier,
    modifierText: Modifier?=Modifier,
    backgroundColor: Color,
    text: String,
    textColor: Color,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontFamily: FontFamily? = regular,
) {
    Button(
        onClick = { onClick.invoke() },
        modifier = modifier ?:Modifier,
        colors = ButtonDefaults.buttonColors(backgroundColor)
    ) {
        CustomTextView(
            modifier = modifierText ?:Modifier,
            textAlign = TextAlign.Center,
            text = text,
            color = textColor,
            fontFamily = fontFamily,
            fontSize = fontSize,
        )
    }
}

@Preview
@Composable
fun PreviewCustomButton(){
    CustomButton(
        onClick = { /*TODO*/ },
        backgroundColor = Color.Transparent,
        text = "Hello",
        textColor = colorResource(R.color.colorPrimary)
    )
}