package coffeeshop.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coffeeshop.app.ui.theme.*
import coffeeshop.shared.data.model.FavoriteDrink
import coffeeshop.shared.data.repository.MockCoffeeRepository
import coffeeshop.shared.presentation.FavoritesPresenter

@Composable
fun FavoritesScreen(
    presenter: FavoritesPresenter = remember { FavoritesPresenter(MockCoffeeRepository()) }
) {
    var favoriteDrinks by remember { mutableStateOf(presenter.getFavoriteDrinks()) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        // Header
        FavoritesHeader()
        
        // Favorites List or Empty State
        if (favoriteDrinks.isEmpty()) {
            EmptyFavoritesState()
        } else {
            FavoritesList(
                drinks = favoriteDrinks,
                onToggleFavorite = { drink ->
                    presenter.removeFavoriteDrink(drink.id)
                    favoriteDrinks = presenter.getFavoriteDrinks()
                }
            )
        }
    }
}

@Composable
fun FavoritesHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(24.dp)
    ) {
        Text(
            text = "My Favorites",
            style = MaterialTheme.typography.h1.copy(
                color = MaterialTheme.colors.onPrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Your favorite coffee drinks",
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.9f),
                fontSize = 16.sp
            )
        )
    }
}

@Composable
fun EmptyFavoritesState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "☕",
                fontSize = 72.sp,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Text(
                text = "No Favorites Yet",
                style = MaterialTheme.typography.h2.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onBackground
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Start adding your favorite drinks\nto see them here!",
                style = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.6f),
                    fontSize = 16.sp
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun FavoritesList(
    drinks: List<FavoriteDrink>,
    onToggleFavorite: (FavoriteDrink) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(drinks) { drink ->
            FavoriteDrinkCard(
                drink = drink,
                onToggleFavorite = { onToggleFavorite(drink) }
            )
        }
    }
}

@Composable
fun FavoriteDrinkCard(
    drink: FavoriteDrink,
    onToggleFavorite: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = 2.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Placeholder for image
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(
                        color = CoffeeBrown.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "☕",
                    fontSize = 40.sp
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = drink.name,
                    style = MaterialTheme.typography.h3.copy(fontSize = 18.sp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = drink.description,
                    style = MaterialTheme.typography.body2,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$${String.format("%.2f", drink.price)}",
                        style = MaterialTheme.typography.body1.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colors.primary
                        )
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Text(
                        text = "⭐ ${drink.rating}",
                        style = MaterialTheme.typography.body2
                    )
                }
            }
            
            // Favorite button
            IconButton(
                onClick = onToggleFavorite,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Remove from favorites",
                    tint = Color(0xFFE91E63),
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
