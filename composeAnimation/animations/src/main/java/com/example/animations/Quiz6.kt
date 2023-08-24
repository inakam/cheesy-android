package com.example.animations

import androidx.compose.animation.core.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale

@Composable
fun ExpandButton() {
    // アニメーションの状態を保存するための変数
    var expanded by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (expanded) 1.5f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // クリックでexpandedの値を切り替える
        Button(
            onClick = {
                expanded = !expanded
            },
            modifier = Modifier.scale(scale)
        ) {
            Text("Click Me")
        }
    }
}
