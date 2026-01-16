package coffeeshop.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coffeeshop.app.ui.theme.*
import coffeeshop.shared.data.model.FavoriteDrink
import coffeeshop.shared.data.model.MenuCategory
import coffeeshop.shared.data.model.MenuItem
import coffeeshop.shared.data.repository.MockCoffeeRepository
import coffeeshop.shared.presentation.FavoritesPresenter
import coffeeshop.shared.presentation.MenuScreenPresenter

@Composable
fun MenuScreen(
    presenter: MenuScreenPresenter = remember { MenuScreenPresenter(MockCoffeeRepository()) },
    favoritesPresenter: FavoritesPresenter = remember { FavoritesPresenter(MockCoffeeRepository()) }
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryId by remember { mutableStateOf<String?>(null) }
    var favoriteIds by remember { mutableStateOf(favoritesPresenter.getFavoriteIds()) }
    
    val categories = remember { presenter.getCategories() }
    val menuItems by remember(searchQuery, selectedCategoryId) {
        mutableStateOf(
            presenter.searchMenuItemsByCategory(searchQuery, selectedCategoryId)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        // Header with search bar
        MenuHeader()
        
        // Search Bar
        SearchBar(
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it }
        )
        
        // Category Filter
        CategoryFilter(
            categories = categories,
            selectedCategoryId = selectedCategoryId,
            onCategorySelected = { categoryId ->
                selectedCategoryId = if (selectedCategoryId == categoryId) null else categoryId
            }
        )
        
        // Menu Items
        MenuItemsList(
            menuItems = menuItems,
            favoriteIds = favoriteIds,
            onToggleFavorite = { item ->
                val favoriteDrink = FavoriteDrink(
                    id = item.id,
                    name = item.name,
                    description = item.description,
                    price = item.price,
                    imageUrl = item.imageUrl,
                    rating = item.rating
                )
                favoritesPresenter.toggleFavorite(favoriteDrink)
                favoriteIds = favoritesPresenter.getFavoriteIds()
            }
        )
    }
}

@Composable
fun MenuHeader() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(24.dp)
    ) {
        Text(
            text = "Our Menu",
            style = MaterialTheme.typography.h1.copy(
                color = MaterialTheme.colors.onPrimary,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Browse our selection of drinks",
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.9f),
                fontSize = 16.sp
            )
        )
    }
}

@Composable
fun SearchBar(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = 6.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        TextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Search for drinks...",
                    style = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
                    )
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = GoldenAccent
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = GoldenAccent
            ),
            singleLine = true,
            textStyle = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun CategoryFilter(
    categories: List<MenuCategory>,
    selectedCategoryId: String?,
    onCategorySelected: (String) -> Unit
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(vertical = 8.dp)
    ) {
        items(categories) { category ->
            CategoryChip(
                category = category,
                isSelected = selectedCategoryId == category.id,
                onClick = { onCategorySelected(category.id) }
            )
        }
    }
}

@Composable
fun CategoryChip(
    category: MenuCategory,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.surface
    val textColor = if (isSelected) MaterialTheme.colors.onPrimary else MaterialTheme.colors.onSurface
    
    Card(
        modifier = Modifier
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp),
        elevation = if (isSelected) 6.dp else 3.dp,
        backgroundColor = backgroundColor
    ) {
        Text(
            text = category.name,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp),
            style = MaterialTheme.typography.body1.copy(
                color = textColor,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 15.sp
            )
        )
    }
}

@Composable
fun MenuItemsList(
    menuItems: List<MenuItem>,
    favoriteIds: Set<String>,
    onToggleFavorite: (MenuItem) -> Unit
) {
    if (menuItems.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No drinks found",
                style = MaterialTheme.typography.body1.copy(
                    color = MaterialTheme.colors.onBackground.copy(alpha = 0.6f)
                )
            )
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(menuItems) { item ->
                MenuItemCard(
                    item = item,
                    isFavorite = favoriteIds.contains(item.id),
                    onToggleFavorite = { onToggleFavorite(item) }
                )
            }
        }
    }
}

@Composable
fun MenuItemCard(
    item: MenuItem,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
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
            // Enhanced image placeholder
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
                    text = item.name,
                    style = MaterialTheme.typography.h4.copy(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.body2.copy(
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    ),
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "$${String.format("%.2f", item.price)}",
                        style = MaterialTheme.typography.h4.copy(
                            fontWeight = FontWeight.Bold,
                            color = GoldenAccent
                        )
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "⭐",
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = String.format("%.1f", item.rating),
                            style = MaterialTheme.typography.body1.copy(
                                fontWeight = FontWeight.Medium,
                                fontSize = 15.sp
                            )
                        )
                    }
                }
            }
            
            // Enhanced favorite button
            IconButton(
                onClick = onToggleFavorite,
                modifier = Modifier.size(48.dp)
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                    tint = if (isFavorite) Color(0xFFE91E63) else MaterialTheme.colors.onSurface.copy(alpha = 0.4f),
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    }
}
