package com.example.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun RotatingButton() {
    // 回転の状態を管理する変数
    var continuousRotation by remember { mutableStateOf(0f) }
    // アニメーションが進行中かどうかを管理する変数
    var isAnimating by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition()

    val rotation by infiniteTransition.animateFloat(
        initialValue = continuousRotation,
        targetValue = continuousRotation + 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 4000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    LaunchedEffect(isAnimating) {
        if (isAnimating) {
            continuousRotation += 360f
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                isAnimating = !isAnimating // アニメーション状態を切り替える
            },
            modifier = Modifier.graphicsLayer(
                rotationZ = if (isAnimating) rotation else continuousRotation // 回転角度を適用
            )
        ) {
            Text("Rotate Me!")
        }
    }
}
