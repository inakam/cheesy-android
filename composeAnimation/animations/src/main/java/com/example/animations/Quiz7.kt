package com.example.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.Color

@Composable
fun RotatingEffectButton() {
    var isAnimating by remember { mutableStateOf(false) }
    val rotationHistory = remember { mutableListOf<Float>() }

    val infiniteTransition = rememberInfiniteTransition()

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 4000,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Restart
        )
    )

    LaunchedEffect(isAnimating, rotation) {
        if (isAnimating) {
            rotationHistory.add(rotation)
            // 保持する履歴の数を制限する（ここでは最後の20個）
            if (rotationHistory.size > 10) {
                rotationHistory.removeFirst()
            }
        } else {
            rotationHistory.clear()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        // 残像をレンダリング
        rotationHistory.forEachIndexed { index, rotationValue ->
            if (index % 2 == 0) {
                val alpha = 1f - (index * 0.1f)
                Button(
                    onClick = { /* 何もしない */ },
                    modifier = Modifier.graphicsLayer(
                        rotationZ = rotationValue,
                        alpha = alpha // 透明度を設定
                    )
                ) {
                    Text("Rotate Me!", color = Color.White.copy(alpha = alpha))
                }
            }
        }

        // 実際のクリッカブルなボタン
        Button(
            onClick = {
                isAnimating = !isAnimating // アニメーション状態を切り替える
            },
            modifier = Modifier.graphicsLayer(
                rotationZ = if (isAnimating) rotation else 0f // 回転角度を適用
            )
        ) {
            Text("Rotate Me!")
        }
    }
}
