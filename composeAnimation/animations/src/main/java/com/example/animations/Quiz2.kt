package com.example.animations

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SplittingButton() {
    var isOpen by remember { mutableStateOf(false) }
    val offset by animateDpAsState(if (isOpen) 60.dp else 0.dp)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (!isOpen && offset < 20.dp) {
            Button(onClick = { isOpen = true }) {
                Text("Click Me")
            }
        } else {
            Row(horizontalArrangement = Arrangement.Center) {
                Box(
                    Modifier
                        .offset(x = -offset)
                ) {
                    Button(onClick = { isOpen = false }) {
                        Text("Button 1")
                    }
                }

                Box(
                    Modifier
                        .offset(x = offset)
                ) {
                    Button(onClick = { isOpen = false }) {
                        Text("Button 2")
                    }
                }
            }
        }
    }
}