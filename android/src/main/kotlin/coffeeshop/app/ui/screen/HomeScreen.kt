package coffeeshop.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coffeeshop.app.ui.theme.*
import coffeeshop.shared.data.model.Banner
import coffeeshop.shared.data.model.FeaturedDrink
import coffeeshop.shared.data.repository.MockCoffeeRepository
import coffeeshop.shared.presentation.HomeScreenPresenter

@Composable
fun HomeScreen(
    presenter: HomeScreenPresenter = remember { HomeScreenPresenter(MockCoffeeRepository()) }
) {
    val greeting = remember { presenter.getGreeting() }
    val banners = remember { presenter.getBanners() }
    val featuredDrinks = remember { presenter.getFeaturedDrinks() }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(bottom = 16.dp)
    ) {
        item {
            GreetingSection(greeting)
        }
        
        item {
            BannerCarousel(banners)
        }
        
        item {
            FeaturedDrinksSection(featuredDrinks)
        }
    }
}

@Composable
fun GreetingSection(greeting: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(24.dp)
    ) {
        Text(
            text = greeting,
            style = MaterialTheme.typography.h1.copy(
                color = MaterialTheme.colors.onPrimary,
                fontSize = 28.sp
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "What can we brew for you today?",
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.9f),
                fontSize = 16.sp
            )
        )
    }
}

@Composable
fun BannerCarousel(banners: List<Banner>) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(
            text = "Special Offers",
            style = MaterialTheme.typography.h2,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(banners) { banner ->
                BannerCard(banner)
            }
        }
    }
}

@Composable
fun BannerCard(banner: Banner) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .height(150.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 4.dp,
        backgroundColor = Color(android.graphics.Color.parseColor(banner.backgroundColor))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = banner.title,
                style = MaterialTheme.typography.h3.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = banner.subtitle,
                style = MaterialTheme.typography.body1.copy(
                    color = Color.White.copy(alpha = 0.9f)
                )
            )
        }
    }
}

@Composable
fun FeaturedDrinksSection(drinks: List<FeaturedDrink>) {
    Column(modifier = Modifier.padding(vertical = 16.dp)) {
        Text(
            text = "Featured Drinks",
            style = MaterialTheme.typography.h2,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        
        Column(
            modifier = Modifier.padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            drinks.forEach { drink ->
                DrinkCard(drink)
            }
        }
    }
}

@Composable
fun DrinkCard(drink: FeaturedDrink) {
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
            // Placeholder for image (would use AsyncImage in real app)
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
        }
    }
}
