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
    presenter: HomeScreenPresenter = remember { HomeScreenPresenter(MockCoffeeRepository()) },
    onNavigateToDrinkBuilder: () -> Unit = {},
    onNavigateToLoyalty: () -> Unit = {},
    onNavigateToScanner: () -> Unit = {},
    onNavigateToPayment: () -> Unit = {}
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
            NavigationSection(
                onNavigateToDrinkBuilder = onNavigateToDrinkBuilder,
                onNavigateToLoyalty = onNavigateToLoyalty,
                onNavigateToScanner = onNavigateToScanner,
                onNavigateToPayment = onNavigateToPayment
            )
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
        elevation = 8.dp,
        backgroundColor = Color(android.graphics.Color.parseColor(banner.backgroundColor))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.CenterStart),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = banner.title,
                    style = MaterialTheme.typography.h3.copy(
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = banner.subtitle,
                    style = MaterialTheme.typography.body1.copy(
                        color = Color.White.copy(alpha = 0.95f),
                        fontSize = 15.sp
                    )
                )
            }
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
            .height(130.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 6.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Enhanced image placeholder with gradient-like background
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .background(
                        color = CoffeeBrown.copy(alpha = 0.15f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "☕",
                    fontSize = 48.sp
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = drink.name,
                    style = MaterialTheme.typography.h4.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = drink.description,
                    style = MaterialTheme.typography.body2.copy(
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    ),
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "$${String.format("%.2f", drink.price)}",
                        style = MaterialTheme.typography.h4.copy(
                            fontWeight = FontWeight.Bold,
                            color = GoldenAccent
                        )
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "⭐",
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = String.format("%.1f", drink.rating),
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NavigationSection(
    onNavigateToDrinkBuilder: () -> Unit,
    onNavigateToLoyalty: () -> Unit,
    onNavigateToScanner: () -> Unit,
    onNavigateToPayment: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Text(
            text = "Quick Actions",
            style = MaterialTheme.typography.h2.copy(fontSize = 22.sp),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            NavigationButton(
                text = "Build Drink",
                emoji = "🎨",
                onClick = onNavigateToDrinkBuilder,
                modifier = Modifier.weight(1f)
            )
            NavigationButton(
                text = "Rewards",
                emoji = "⭐",
                onClick = onNavigateToLoyalty,
                modifier = Modifier.weight(1f)
            )
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            NavigationButton(
                text = "Scan Code",
                emoji = "📷",
                onClick = onNavigateToScanner,
                modifier = Modifier.weight(1f)
            )
            NavigationButton(
                text = "Payment",
                emoji = "💳",
                onClick = onNavigateToPayment,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun NavigationButton(
    text: String,
    emoji: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(90.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.surface
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 6.dp,
            pressedElevation = 10.dp
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = emoji,
                fontSize = 32.sp
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 13.sp,
                    color = MaterialTheme.colors.onSurface
                )
            )
        }
    }
}
