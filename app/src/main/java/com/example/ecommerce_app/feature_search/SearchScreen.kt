import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import com.google.gson.Gson
import android.util.Base64
import android.util.Log
import com.example.ecommerce_app.feature_product.presentation.screen.ProductCard

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

          LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxSize()) {
               items(searchItems) { product ->
                    ProductCard(product, onProductClick = {
                         navController.navigate("product_details/${product.id}")
                    })
               }
          }
     }

     @Composable
     fun ProductCard(product: Product, onProductClick: () -> Unit, onAddToCart: () -> Unit) {
          Card(
               modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .clickable { onProductClick() },
               elevation = 6.dp,
               shape = RoundedCornerShape(12.dp)
          ) {
               Column(modifier = Modifier.padding(12.dp)) {
                    Image(
                         painter = rememberAsyncImagePainter(model = product.image),
                         contentDescription = product.title,
                         contentScale = ContentScale.Crop,
                         modifier = Modifier.fillMaxWidth().height(120.dp)
                              .clip(RoundedCornerShape(8.dp))
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = product.title, fontSize = 16.sp, fontWeight = FontWeight.Bold)

                    Row(
                         modifier = Modifier.fillMaxWidth(),
                         horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                         Text(
                              text = "$${product.price}",
                              fontSize = 14.sp,
                              fontWeight = FontWeight.Bold,
                              color = Color.Green
                         )
                         IconButton(onClick = onAddToCart) {
                              Icon(
                                   Icons.Filled.ShoppingCart,
                                   contentDescription = "Add to Cart",
                                   tint = Color(0xFFFF9800)
                              )
                         }
                    }
               }
          }
     }
}
