package com.example.animations

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedVisibilityAnimation(
    visible: Boolean
) {
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(initialAlpha = 0.1f),
        exit = fadeOut(animationSpec = tween(durationMillis = 250))
    ) {
        Column {
            Text(text = "AnimatedVisibility")
            Text(
                text = "Hoge",
                modifier = Modifier.animateEnterExit(
                        enter = slideInVertically(),
                        exit = slideOutVertically()
                )
            )
        }
    }
}