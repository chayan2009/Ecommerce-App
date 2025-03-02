import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.ecommerce_app.domain.model.Product
import com.example.ecommerce_app.feature_search.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
     navController: NavController,
     searchViewModel: SearchViewModel = hiltViewModel()
) {
     val query by searchViewModel.query.collectAsState()
     val searchItems by searchViewModel.filteredItems.collectAsState()

     Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
          TextField(
               value = query,
               onValueChange = searchViewModel::onSearchQueryChanged,
               modifier = Modifier.fillMaxWidth(),
               label = { Text("Search...") }
          )

          Spacer(modifier = Modifier.height(8.dp))

          LazyVerticalGrid(
               columns = GridCells.Fixed(2),
               modifier = Modifier.fillMaxSize(),
               contentPadding = PaddingValues(8.dp)
          ) {
               items(searchItems) { product ->
                    ProductCard(product, onAddToCart = {})
               }
          }
     }
}

@Composable
fun ProductCard(product: Product, onAddToCart: () -> Unit) {
     Card(
          modifier = Modifier
               .padding(8.dp)
               .fillMaxWidth(),
          elevation = 6.dp,
          shape = RoundedCornerShape(12.dp)
     ) {
          Column(modifier = Modifier.padding(12.dp)) {
               Image(
                    painter = rememberAsyncImagePainter(model = product.image),
                    contentDescription = product.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                         .fillMaxWidth()
                         .height(120.dp)
                         .clip(RoundedCornerShape(8.dp))
               )

               Spacer(modifier = Modifier.height(8.dp))

               Text(
                    text = product.title,
                    style = MaterialTheme.typography.h6.copy(
                         fontWeight = FontWeight.Bold,
                         fontSize = 16.sp
                    ),
                    modifier = Modifier.padding(vertical = 4.dp)
               )

               Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
               ) {
                    Text(
                         text = "$${product.price}",
                         style = MaterialTheme.typography.subtitle1.copy(
                              fontWeight = FontWeight.Bold,
                              fontSize = 14.sp,
                              color = Color(0xFF388E3C)
                         )
                    )

                    IconButton(
                         onClick = onAddToCart,
                         modifier = Modifier.size(32.dp)
                    ) {
                         Icon(
                              imageVector = Icons.Filled.ShoppingCart,
                              contentDescription = "Add to Cart",
                              tint = Color(0xFFFF9800)
                         )
                    }
               }
          }
     }
}
