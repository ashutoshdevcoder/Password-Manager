package com.passman.ui.customView

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.passman.ui.theme.regular


@Composable
fun FilledRoundedButton(
    onClick: () -> Unit,
    radius: Int = 8,
    modifier: Modifier,
    modifierText: Modifier,
    backgroundColor: Color,
    text: String,
    textColor: Color,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontFamily: FontFamily? = regular,
) {
    Button(
        onClick = { onClick.invoke() },
        shape = RoundedCornerShape(radius.dp),
        modifier = modifier,
        elevation = ButtonDefaults.buttonElevation(4.dp),
        colors = ButtonDefaults.buttonColors(backgroundColor)
    ) {
        CustomTextView(
            modifier = modifierText,
            textAlign = TextAlign.Center,
            text = text,
            color = textColor,
            fontFamily = fontFamily,
            maxLines = 2,
            fontSize = fontSize,
        )
    }
}