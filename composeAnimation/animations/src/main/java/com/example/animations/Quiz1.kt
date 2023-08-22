package com.example.animations

import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Transition
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Quiz1(
    transition: Transition<Boolean>
) {
    val scale by transition.animateFloat(label = "scale") {
        if (it) 2f else 1f
    }

    val color by transition.animateColor(label = "color") {
        if (it) Color.LightGray else Color.Yellow
    }
    val textSize by transition.animateFloat (label = "textSize") {
        if (it) 10f else 20f
    }
    Box(
        Modifier
            .animateContentSize()
            .background(color)
            .width(100.dp * scale)
            .height(100.dp * scale)
    ) {
        Text(
            fontSize = textSize.sp,
            text = "UpdateTransition")
    }
}
