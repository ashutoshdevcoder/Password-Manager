package com.passman.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.passman.R

// Set of Material typography styles to start with
val regular= FontFamily(Font(R.font.mazzard_m_regular, weight = FontWeight.Normal))
val medium= FontFamily(Font(R.font.mazzard_m_medium, weight = FontWeight.Medium))
val bold= FontFamily(Font(R.font.mazzard_m_bold, weight = FontWeight.Bold))
val semiBold= FontFamily(Font(R.font.mazzard_m_semibold, weight = FontWeight.SemiBold))
val Typography = Typography(
    labelSmall = TextStyle(
        fontFamily = regular,
        fontWeight = FontWeight.Normal,
    ),
    //Other default text styles to override
    labelMedium = TextStyle(
        fontFamily = medium,
        fontWeight = FontWeight.Medium
    ),
    labelLarge = TextStyle(
        fontFamily = semiBold,
        fontWeight = FontWeight.Bold,
    )
)