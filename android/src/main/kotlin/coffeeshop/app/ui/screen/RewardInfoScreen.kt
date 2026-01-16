package coffeeshop.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coffeeshop.app.ui.theme.*

@Composable
fun RewardInfoScreen(
    onBackClick: () -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        item {
            RewardInfoHeader(onBackClick)
        }
        
        item {
            HowItWorksSection()
        }
        
        item {
            EarningPointsSection()
        }
        
        item {
            RedeemingPointsSection()
        }
        
        item {
            RewardTiersSection()
        }
        
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun RewardInfoHeader(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Reward Program",
                    style = MaterialTheme.typography.h1.copy(
                        color = MaterialTheme.colors.onPrimary,
                        fontSize = 28.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Earn points with every purchase and redeem for discounts",
                    style = MaterialTheme.typography.body1.copy(
                        color = MaterialTheme.colors.onPrimary.copy(alpha = 0.9f),
                        fontSize = 16.sp
                    )
                )
            }
            TextButton(onClick = onBackClick) {
                Text(
                    text = "← Back",
                    style = MaterialTheme.typography.button.copy(
                        color = MaterialTheme.colors.onPrimary
                    )
                )
            }
        }
    }
}

@Composable
fun HowItWorksSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 6.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "☕",
                    fontSize = 40.sp
                )
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = "How It Works",
                    style = MaterialTheme.typography.h3.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(18.dp))
            
            Text(
                text = "Our reward program is designed to thank you for being a loyal customer. " +
                        "Every time you make a purchase, you'll automatically earn points that can be " +
                        "redeemed for discounts on future orders.",
                style = MaterialTheme.typography.body1.copy(
                    fontSize = 16.sp,
                    lineHeight = 24.sp
                )
            )
        }
    }
}

@Composable
fun EarningPointsSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 8.dp,
        backgroundColor = CoffeeBrown
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "⭐",
                    fontSize = 40.sp
                )
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = "Earning Points",
                    style = MaterialTheme.typography.h3.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Card(
                shape = RoundedCornerShape(14.dp),
                backgroundColor = GoldenAccent.copy(alpha = 0.25f),
                elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "5 points per $1 spent",
                        style = MaterialTheme.typography.h2.copy(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Example: A $5 coffee earns you 25 points!",
                        style = MaterialTheme.typography.body1.copy(
                            color = Color.White.copy(alpha = 0.95f),
                            fontSize = 15.sp
                        )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Points are automatically added to your account after each successful purchase.",
                style = MaterialTheme.typography.body1.copy(
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 15.sp
                )
            )
        }
    }
}

@Composable
fun RedeemingPointsSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 8.dp,
        backgroundColor = GoldenAccent
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🎁",
                    fontSize = 40.sp
                )
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = "Redeeming Points",
                    style = MaterialTheme.typography.h3.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = DarkCoffee
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Card(
                shape = RoundedCornerShape(14.dp),
                backgroundColor = DarkCoffee.copy(alpha = 0.15f),
                elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier.padding(20.dp)
                ) {
                    Text(
                        text = "100 points = $5 discount",
                        style = MaterialTheme.typography.h2.copy(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold,
                            color = DarkCoffee
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Redeem in multiples of 100 points",
                        style = MaterialTheme.typography.body1.copy(
                            color = DarkCoffee.copy(alpha = 0.85f),
                            fontSize = 15.sp
                        )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Apply your points at checkout to receive an instant discount on your order. " +
                        "Choose how many points to redeem each time!",
                style = MaterialTheme.typography.body1.copy(
                    color = DarkCoffee.copy(alpha = 0.8f),
                    fontSize = 15.sp
                )
            )
        }
    }
}

@Composable
fun RewardTiersSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 6.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "🏆",
                    fontSize = 40.sp
                )
                Spacer(modifier = Modifier.width(14.dp))
                Text(
                    text = "Reward Tiers",
                    style = MaterialTheme.typography.h3.copy(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            RewardTierItem(
                tier = "Bronze",
                points = "100 points",
                benefit = "First redemption available"
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            RewardTierItem(
                tier = "Silver",
                points = "200 points",
                benefit = "Unlock bonus offers"
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            RewardTierItem(
                tier = "Gold",
                points = "500 points",
                benefit = "Priority rewards & specials"
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            RewardTierItem(
                tier = "Platinum",
                points = "1000 points",
                benefit = "Exclusive perks & surprises"
            )
        }
    }
}

@Composable
fun RewardTierItem(tier: String, points: String, benefit: String) {
    Card(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = CoffeeBrown.copy(alpha = 0.08f),
        elevation = 0.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = tier,
                    style = MaterialTheme.typography.h5.copy(
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = points,
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 14.sp,
                        color = GoldenAccent,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
            Column(
                modifier = Modifier.weight(1.5f),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = benefit,
                    style = MaterialTheme.typography.body2.copy(
                        fontSize = 13.sp,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                    )
                )
            }
        }
    }
}
