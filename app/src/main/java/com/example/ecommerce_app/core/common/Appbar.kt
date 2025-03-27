package com.example.ecommerce_app.core.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Badge
import androidx.compose.material.BadgedBox
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun Appbar(
    title: String,
    bgColor: Color = Color.Black,
    navigationIconType: NavigationIconType = NavigationIconType.NONE,
    onNavigationClick: (() -> Unit)? = null,
    showIcons: Boolean = true,
    navController: NavController,
    cartCount: Int = 0,
    onSearchQueryChanged: (String) -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    var isSearchExpanded by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf("") }

    val systemUiController = rememberSystemUiController()
    val currentRoute = navController.currentDestination?.route

    val isSearchVisible = currentRoute in listOf("home")

    SideEffect {
        systemUiController.setStatusBarColor(color = bgColor)
    }

    TopAppBar(
        backgroundColor = bgColor,
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                    IconButton(onClick = {
                        isSearchExpanded = false
                        query = ""
                        onSearchQueryChanged("")
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                }
            }

            Box(
                modifier = Modifier.weight(2f),
                contentAlignment = Alignment.Center
            ) {
                if (isSearchExpanded) {
                    TextField(
                        value = query,
                        onValueChange = {
                            query = it
                            onSearchQueryChanged(it)
                        },
                        placeholder = { Text("Search product", color = Color.White) },
                        colors = TextFieldDefaults.textFieldColors(
                            backgroundColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            cursorColor = Color.White,
                            textColor = Color.White
                        ),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Text(title, color = Color.White, style = MaterialTheme.typography.h6)
                }
            }

            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row {
                    if (isSearchVisible) {
                        if (!isSearchExpanded) {
                            IconButton(onClick = { isSearchExpanded = true }) {
                                Icon(
                                    Icons.Default.Search,
                                    contentDescription = "Search",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                    if (!isSearchExpanded && showIcons) {
                        IconButton(onClick = { navController.navigate("cart") }) {
                            BadgedBox(
                                badge = {
                                    if (cartCount > 0) {
                                        Badge { Text(cartCount.toString()) }
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.Default.ShoppingCart,
                                    contentDescription = "Cart",
                                    tint = Color.White
                                )
                            }
                        }
                    }
                    actions(this)
                }
            }
        }
    }
}

