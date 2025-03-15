import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.ecommerce_app.feature_search.presentation.SearchCard
import com.example.ecommerce_app.feature_search.viewmodel.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
     navController: NavController,
     searchViewModel: SearchViewModel = hiltViewModel()
) {
     val query by searchViewModel.query.collectAsState()
     val searchItems by searchViewModel.filteredItems.collectAsState()
     var isSearchExpanded by remember { mutableStateOf(true) }

     Column(modifier = Modifier.fillMaxSize()) {
          CenterAlignedTopAppBar(
               title = {
                    AnimatedVisibility(
                         visible = isSearchExpanded,
                         enter = fadeIn(),
                         exit = fadeOut()
                    ) {
                         TextField(
                              value = query,
                              onValueChange = searchViewModel::onSearchQueryChanged,
                              placeholder = { Text("Search...", color = Color.White) },
                              colors = TextFieldDefaults.textFieldColors(
                                   containerColor = Color.Transparent,
                                   focusedIndicatorColor = Color.Transparent,
                                   unfocusedIndicatorColor = Color.Transparent,
                                   cursorColor = Color.White
                              ),
                              modifier = Modifier.fillMaxWidth()
                         )
                    }
               },
               navigationIcon = {
                    if (isSearchExpanded) {
                         IconButton(onClick = { isSearchExpanded = false }) {
                              Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                         }
                    }
               },
               actions = {
                    if (!isSearchExpanded) {
                         IconButton(onClick = { isSearchExpanded = true }) {
                              Icon(Icons.Default.Search, contentDescription = "Search", tint = Color.White)
                         }
                    }
               },
               colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.LightGray)
          )

          Spacer(modifier = Modifier.height(8.dp))

          LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
               items(searchItems) { product ->
                    SearchCard(
                         product,
                         onProductClick = { navController.navigate("product_details/${product.id}") },
                         onAddToCart = {navController.navigate("product_details/${product.id}")}
                    )
               }
          }
     }
}

