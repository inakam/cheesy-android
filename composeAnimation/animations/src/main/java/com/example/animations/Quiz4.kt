package com.example.animations

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

// https://tech.mirrativ.stream/entry/2022/07/29/105354

@Composable
fun TextAnimation() {
    val text1AlphaAnimatable = remember { Animatable(0f) }
    val text2AlphaAnimatable = remember { Animatable(0f) }
    val text3AlphaAnimatable = remember { Animatable(0f) }

    LaunchedEffect(true) {
        while (true) { // 無限ループでアニメーションを繰り返す
            listOf(text1AlphaAnimatable, text2AlphaAnimatable, text3AlphaAnimatable).forEach { animatable ->
                // アルファ値を0から1へ変化
                animatable.animateTo(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 500)
                )
                delay(100)
            }
            listOf(text1AlphaAnimatable, text2AlphaAnimatable, text3AlphaAnimatable).forEach { animatable ->
                // アルファ値を1から0へ変化
                animatable.animateTo(
                    targetValue = 0f,
                    animationSpec = tween(durationMillis = 500)
                )
                delay(100)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.alpha(text1AlphaAnimatable.value),
                text = "テキスト1"
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.alpha(text2AlphaAnimatable.value),
                text = "テキスト2"
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                modifier = Modifier.alpha(text3AlphaAnimatable.value),
                text = "テキスト3"
            )
        }
    }
}
