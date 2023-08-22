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
    var rotationState by remember { mutableStateOf(0f) }
    // アニメーションが進行中かどうかを管理する変数
    var isAnimating by remember { mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if (isAnimating) 360f else 0f,
        animationSpec = if (isAnimating) {
            // 加速しながら開始、減速しながら終了
            keyframes {
                durationMillis = 4000 // アニメーションの合計時間
                0f at 0 with FastOutSlowInEasing // 開始時の回転角度
                360f at 4000 with FastOutSlowInEasing // 終了時の回転角度
            }
        } else {
            spring() // 通常はspringアニメーション
        }
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                isAnimating = !isAnimating // アニメーション状態をトグル
            },
            modifier = Modifier.graphicsLayer(
                rotationZ = rotation // 回転角度を適用
            )
        ) {
            Text("Rotate Me!")
        }
    }
}
