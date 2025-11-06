package com.passman.ui.customView

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.passman.R
import com.passman.ui.theme.regular


@Composable
fun CustomAnnotatedTextView(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: Color = colorResource(R.color.colorPrimary),
    fontSize: TextUnit = TextUnit.Unspecified,
    letterSpacing: TextUnit = 0.5.sp,
    textAlign: TextAlign? = null,
    fontFamily: androidx.compose.ui.text.font.FontFamily? = regular,
    lineHeight: TextUnit = TextUnit.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap:Boolean = true
) {
    Text(
        text = text,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        letterSpacing = letterSpacing,
        textAlign = textAlign,
        fontFamily = fontFamily,
        lineHeight = lineHeight,
        maxLines = maxLines,
        overflow = overflow,
        softWrap = softWrap,
    )
}