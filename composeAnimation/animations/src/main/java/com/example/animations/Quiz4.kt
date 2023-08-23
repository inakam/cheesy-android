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
    val texts = listOf("テキスト1", "テキスト2", "テキスト3")
    val animatables = remember { texts.map { Animatable(0f, 0f) } }

    LaunchedEffect(true) {
        while (true) { // 無限ループでアニメーションを繰り返す
            animatables.forEach { animatable ->
                animateAlpha(animatable, targetValue = 1f)
            }
            animatables.forEach { animatable ->
                animateAlpha(animatable, targetValue = 0f)
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
            texts.forEachIndexed { index, text ->
                AnimatedText(animatables[index].value, text)
                if (index < texts.size - 1) Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun AnimatedText(alpha: Float, text: String) {
    Text(
        modifier = Modifier.alpha(alpha),
        text = text
    )
}

suspend fun animateAlpha(animatable: Animatable<Float, AnimationVector1D>, targetValue: Float) {
    animatable.animateTo(
        targetValue = targetValue,
        animationSpec = tween(durationMillis = 500)
    )
    delay(100)
}
