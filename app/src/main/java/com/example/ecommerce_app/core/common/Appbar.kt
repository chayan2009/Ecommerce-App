package com.example.ecommerce_app.core.common

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Appbar(
    title: String,
    bgColor: Color = Color(0xFF6200EA), // Updated color for better visibility
    navigationIconType: NavigationIconType = NavigationIconType.NONE,
    onNavigationClick: (() -> Unit)? = null,
    showIcons: Boolean = true,
    actions: @Composable RowScope.() -> Unit = {}
) {
    var isSearchExpanded by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    TopAppBar(
        backgroundColor = bgColor,
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Navigation Icon (Back/Menu)
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                if (navigationIconType != NavigationIconType.NONE && onNavigationClick != null) {
                    IconButton(onClick = onNavigationClick) {
                        Icon(
                            imageVector = when (navigationIconType) {
                                NavigationIconType.BACK -> Icons.Default.ArrowBack
                                NavigationIconType.MENU -> Icons.Default.Menu
                                else -> return@IconButton
                            },
                            contentDescription = "Navigation",
                            tint = Color.White
                        )
                    }
                } else if (isSearchExpanded) {
                    IconButton(onClick = { isSearchExpanded = false }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            }

            // Title or Search Field
            Box(
                modifier = Modifier.weight(2f),
                contentAlignment = Alignment.Center
            ) {
                this@Row.AnimatedVisibility(
                    visible = isSearchExpanded,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    TextField(
                        value = query,
                        onValueChange = { query = it },
                        placeholder = { Text("Search...", color = Color.White) },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.White
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                this@Row.AnimatedVisibility(visible = !isSearchExpanded) {
                    Text(title, color = Color.White, style = MaterialTheme.typography.h6)
                }
            }

            // Actions (Search & Cart Icons)
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row {
                    if (!isSearchExpanded) {
                        IconButton(onClick = { isSearchExpanded = true }) {
                            Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                        }
                        if (showIcons) {
                            IconButton(onClick = { }) {
                                Icon(Icons.Default.ShoppingCart, contentDescription = "Cart", tint = Color.White)
                            }
                        }
                    }
                    actions(this)
                }
            }
        }
    }
}
