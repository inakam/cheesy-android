package com.example.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import kotlin.math.abs
import kotlin.math.sin

@Composable
fun MotimotiButton(onClick: () -> Unit, content: @Composable () -> Unit) {
    // 無限アニメーションのためのAnimatableを作成
    val infiniteTransition = rememberInfiniteTransition()
    val keyframe by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val ratio = sin(Math.PI * abs(keyframe - 0.5) / 0.5) / 4
    val scaleOffset = Offset(ratio.toFloat(), (-ratio).toFloat())

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.graphicsLayer(
                scaleX = 1 + scaleOffset.x,
                scaleY = 1 + scaleOffset.y,
                translationX = -scaleOffset.x,
                translationY = scaleOffset.y
            )
        ) {
            content()
        }
    }
}