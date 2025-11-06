package com.passman.ui.customView

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.passman.R
import com.passman.ui.theme.regular

@Composable
fun OutlineRoundedButton(
    onClick: () -> Unit,
    radius: Int = 8,
    modifier: Modifier = Modifier,
    modifierText: Modifier = Modifier,
    backgroundColor: Color,
    text: String,
    textColor: Color,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontFamily: FontFamily? = regular,
    borderColor: Color = Color.Transparent,
    borderWidth: Dp = 1.dp,
) {
    OutlinedButton(
        onClick = { onClick.invoke() },
        shape = RoundedCornerShape(radius.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor),
        border = BorderStroke(width = borderWidth, color = borderColor),
        modifier = modifier
    ) {
        CustomTextView(
            modifier = modifierText,
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
fun OutlineRoundedButtonPreview() {
    OutlineRoundedButton(
        onClick = { /*TODO*/ }, backgroundColor = Color.White, text = "OK", textColor = colorResource(
            R.color.colorPrimary),
        modifier = Modifier.fillMaxWidth().height(45.dp), modifierText = Modifier
    )
}