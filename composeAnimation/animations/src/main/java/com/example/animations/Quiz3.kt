package com.example.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerRectangle(
    colors: List<Color> = listOf(
        Color(0xffe4e4e7),
        Color(0xfffafafa),
        Color(0xffe4e4e7),
    ),
    height: Dp = 225.dp
) {
    BoxWithConstraints {
        val widthPx = with(LocalDensity.current) { maxWidth.toPx() }
        val heightPx = with(LocalDensity.current) { height.toPx() }
        val gradientWidthPx = with(LocalDensity.current) {
            height.toPx() * 0.2f
        }
        val infiniteTransition = rememberInfiniteTransition()
        val animation = tween<Float>(durationMillis = 1300, delayMillis = 300, easing = LinearEasing)
        val xShimmer = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = widthPx + gradientWidthPx,
            animationSpec = infiniteRepeatable(
                animation = animation,
                repeatMode = RepeatMode.Restart
            )
        )
        val yShimmer = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = heightPx + gradientWidthPx,
            animationSpec = infiniteRepeatable(
                animation = animation,
                repeatMode = RepeatMode.Restart
            )
        )
        val brush = Brush.linearGradient(
            colors = colors,
            start = Offset(xShimmer.value - gradientWidthPx, yShimmer.value - gradientWidthPx),
            end = Offset(xShimmer.value, yShimmer.value),
        )
        Surface(
            shape = MaterialTheme.shapes.small
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
                    .background(brush = brush)
            )
        }
    }
}