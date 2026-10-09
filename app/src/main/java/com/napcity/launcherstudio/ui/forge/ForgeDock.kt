package com.napcity.launcherstudio.ui.forge

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.napcity.launcherstudio.data.ThemeConfig
import com.napcity.launcherstudio.ui.theme.toComposeColor

/**
 * ForgeUI exclusive dock variants, adapted for the launcher.
 * Ported from ForgeUI v0.20.0's ExclusiveNavFeedback (floating-glass, aurora-pill).
 */

/** Animated aurora gradient brush — cycles through theme colors */
@Composable
fun auroraBrush(theme: ThemeConfig): Brush {
    val phase by rememberInfiniteTransition(label = "aurora").animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "phase"
    )
    val colors = listOf(
        theme.primary.toComposeColor(),
        theme.secondary.toComposeColor(),
        theme.tertiary.toComposeColor(),
        theme.primary.toComposeColor()
    )
    return Brush.linearGradient(
        colors = colors,
        start = androidx.compose.ui.geometry.Offset(phase * 600f, 0f),
        end = androidx.compose.ui.geometry.Offset(phase * 600f + 600f, 200f)
    )
}

@Composable
fun ForgeDock(
    variant: String,
    theme: ThemeConfig,
    onOpen: () -> Unit
) {
    val primary = theme.primary.toComposeColor()
    when (variant) {
        "floating-glass" -> {
            Box(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
                    .shadow(16.dp, RoundedCornerShape(28.dp))
                    .background(Color.White.copy(alpha = 0.10f), RoundedCornerShape(28.dp))
                    .border(
                        1.dp,
                        Brush.verticalGradient(listOf(
                            Color.White.copy(alpha = 0.5f),
                            Color.White.copy(alpha = 0.08f)
                        )),
                        RoundedCornerShape(28.dp)
                    )
                    .clickable(onClick = onOpen)
                    .padding(vertical = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "▲",
                        style = MaterialTheme.typography.titleMedium,
                        color = primary
                    )
                    Box(
                        Modifier.padding(top = 4.dp).size(5.dp)
                            .background(primary, CircleShape)
                            .shadow(6.dp, CircleShape)
                    )
                }
            }
        }
        "aurora-pill" -> {
            Box(
                modifier = Modifier.fillMaxWidth().padding(16.dp)
                    .shadow(16.dp, CircleShape)
                    .background(auroraBrush(theme), CircleShape)
                    .clickable(onClick = onOpen)
                    .padding(vertical = 18.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "▲  Swipe up for apps",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White
                )
            }
        }
        "neon-edge" -> {
            Box(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp)
                    .shadow(12.dp, RoundedCornerShape(16.dp))
                    .border(1.5.dp, primary, RoundedCornerShape(16.dp))
                    .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
                    .clickable(onClick = onOpen)
                    .padding(vertical = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "▲ Swipe up for apps",
                    style = MaterialTheme.typography.labelMedium,
                    color = primary
                )
            }
        }
        else -> {
            // Fallback to standard bar
            Box(
                modifier = Modifier.fillMaxWidth()
                    .background(theme.surface.toComposeColor())
                    .clickable(onClick = onOpen)
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "▲ Swipe up for apps",
                    style = MaterialTheme.typography.labelMedium,
                    color = primary
                )
            }
        }
    }
}
