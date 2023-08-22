package com.example.animations

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AnimateContentSizeAnimation(
    state: Boolean
) {
    Box(
        modifier = Modifier
            .background(Color.LightGray)
            .width( if(state) 200.dp else 50.dp)
            .height( if(state) 200.dp else 50.dp)
            .animateContentSize()
    ) {
        Text(text = "Correct")
    }
}
