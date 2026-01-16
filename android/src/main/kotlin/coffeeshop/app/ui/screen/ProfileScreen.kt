package coffeeshop.app.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coffeeshop.app.ui.theme.*
import coffeeshop.shared.data.model.LoyaltyMembership
import coffeeshop.shared.data.model.LoyaltyTier
import coffeeshop.shared.data.model.RewardTransaction
import coffeeshop.shared.data.model.RewardTransactionType
import coffeeshop.shared.data.repository.MockCoffeeRepository
import coffeeshop.shared.presentation.ProfilePresenter
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ProfileScreen(
    presenter: ProfilePresenter = remember { ProfilePresenter(MockCoffeeRepository()) },
    onRewardInfoClick: () -> Unit = {},
    onScanBarcodeClick: () -> Unit = {}
) {
    val user = remember { presenter.getCurrentUser() }
    val rewardPoints by remember { mutableStateOf(presenter.getRewardPointsBalance()) }
    val pointsToNextTier = remember { presenter.getPointsToNextTier() }
    val canRedeem = remember { presenter.canRedeemPoints() }
    val transactions = remember { presenter.getRewardTransactions() }
    val loyaltyMembership = remember { presenter.getLoyaltyMembership() }
    
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        item {
            ProfileHeader(user.name)
        }
        
        item {
            LoyaltyMembershipCard(loyaltyMembership)
        }
        
        item {
            RewardPointsCard(
                points = rewardPoints,
                pointsToNextTier = pointsToNextTier,
                canRedeem = canRedeem,
                onRewardInfoClick = onRewardInfoClick,
                onScanBarcodeClick = onScanBarcodeClick
            )
        }
        
        item {
            Text(
                text = "Recent Activity",
                style = MaterialTheme.typography.h2,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
        }
        
        items(transactions) { transaction ->
            TransactionItem(transaction)
        }
        
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun ProfileHeader(userName: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(24.dp)
    ) {
        Text(
            text = "Profile",
            style = MaterialTheme.typography.h1.copy(
                color = MaterialTheme.colors.onPrimary,
                fontSize = 28.sp
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Welcome, $userName!",
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.9f),
                fontSize = 16.sp
            )
        )
    }
}

@Composable
fun RewardPointsCard(
    points: Int,
    pointsToNextTier: Int,
    canRedeem: Boolean,
    onRewardInfoClick: () -> Unit,
    onScanBarcodeClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 10.dp,
        backgroundColor = CoffeeBrown
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Reward Points",
                    style = MaterialTheme.typography.h3.copy(
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "⭐",
                    fontSize = 32.sp
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Text(
                text = "$points",
                style = MaterialTheme.typography.h1.copy(
                    color = GoldenAccent,
                    fontSize = 56.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            
            Text(
                text = "points available",
                style = MaterialTheme.typography.body1.copy(
                    color = Color.White.copy(alpha = 0.85f),
                    fontSize = 15.sp
                )
            )
            
            Spacer(modifier = Modifier.height(20.dp))
            
            if (pointsToNextTier > 0) {
                Card(
                    shape = RoundedCornerShape(12.dp),
                    backgroundColor = Color.White.copy(alpha = 0.15f),
                    elevation = 0.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "🎯",
                            fontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "$pointsToNextTier points to next reward tier",
                            style = MaterialTheme.typography.body1.copy(
                                color = Color.White,
                                fontSize = 14.sp
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Scan Barcode button (full width)
            Button(
                onClick = onScanBarcodeClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = GoldenAccent,
                    contentColor = DarkCoffee
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 4.dp,
                    pressedElevation = 8.dp
                )
            ) {
                Text(
                    text = "📱 Scan Receipt Barcode",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }
            
            Spacer(modifier = Modifier.height(14.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = onRewardInfoClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White,
                        contentColor = CoffeeBrown
                    ),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.elevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 8.dp
                    )
                ) {
                    Text(
                        text = "Learn More",
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
                
                if (canRedeem) {
                    Button(
                        onClick = { /* Redeem action would go here */ },
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = CaramelBrown,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(12.dp),
                        elevation = ButtonDefaults.elevation(
                            defaultElevation = 4.dp,
                            pressedElevation = 8.dp
                        )
                    ) {
                        Text(
                            text = "Redeem",
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TransactionItem(transaction: RewardTransaction) {
    val dateFormat = remember { SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()) }
    val date = remember { dateFormat.format(Date(transaction.timestamp)) }
    val isPositive = transaction.type == RewardTransactionType.EARNED
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(14.dp),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(
                        color = if (isPositive) GoldenAccent.copy(alpha = 0.2f) 
                                else CaramelBrown.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(23.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isPositive) "+" else "−",
                    fontSize = 24.sp,
                    color = if (isPositive) GoldenAccent else CaramelBrown,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.width(14.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = transaction.details,
                    style = MaterialTheme.typography.body1.copy(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = date,
                    style = MaterialTheme.typography.caption.copy(
                        fontSize = 12.sp,
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.6f)
                    )
                )
            }
            
            Text(
                text = if (isPositive) "+${transaction.points}" else "${transaction.points}",
                style = MaterialTheme.typography.h4.copy(
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isPositive) GoldenAccent else CaramelBrown
                )
            )
        }
    }
}

@Composable
fun LoyaltyMembershipCard(membership: LoyaltyMembership) {
    val tierColor = getTierColor(membership.currentTier)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .padding(top = 0.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 10.dp,
        backgroundColor = tierColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Loyalty Membership",
                    style = MaterialTheme.typography.h4.copy(
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = membership.currentTier.emoji,
                    fontSize = 32.sp
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = membership.currentTier.tierName,
                    style = MaterialTheme.typography.h1.copy(
                        color = Color.White,
                        fontSize = 40.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "TIER",
                    style = MaterialTheme.typography.body1.copy(
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.padding(bottom = 6.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Tier Benefits
            Card(
                shape = RoundedCornerShape(12.dp),
                backgroundColor = Color.White.copy(alpha = 0.15f),
                elevation = 0.dp
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = "Your Benefits",
                        style = MaterialTheme.typography.body1.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    membership.currentTier.benefits.take(3).forEach { benefit ->
                        Row(
                            modifier = Modifier.padding(vertical = 3.dp)
                        ) {
                            Text(
                                text = "• ",
                                style = MaterialTheme.typography.body2.copy(
                                    color = Color.White,
                                    fontSize = 13.sp
                                )
                            )
                            Text(
                                text = benefit,
                                style = MaterialTheme.typography.body2.copy(
                                    color = Color.White,
                                    fontSize = 13.sp
                                )
                            )
                        }
                    }
                    if (membership.currentTier.benefits.size > 3) {
                        Text(
                            text = "• And ${membership.currentTier.benefits.size - 3} more...",
                            style = MaterialTheme.typography.body2.copy(
                                color = Color.White,
                                fontSize = 13.sp
                            )
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Progress to next tier
            membership.nextTier?.let { nextTier ->
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Progress to ${nextTier.tierName}",
                            style = MaterialTheme.typography.body1.copy(
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Text(
                            text = "${membership.progressPercentage}%",
                            style = MaterialTheme.typography.body1.copy(
                                color = Color.White,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    LinearProgressIndicator(
                        progress = membership.progressPercentage / 100f,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(10.dp)
                            .clip(RoundedCornerShape(5.dp)),
                        backgroundColor = Color.White.copy(alpha = 0.25f),
                        color = GoldenAccent
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "${membership.pointsToNextTier} points needed",
                        style = MaterialTheme.typography.body2.copy(
                            color = Color.White.copy(alpha = 0.85f),
                            fontSize = 12.sp
                        )
                    )
                }
            } ?: run {
                Text(
                    text = "🎉 You've reached the highest tier!",
                    style = MaterialTheme.typography.body1.copy(
                        color = GoldenAccent,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
fun getTierColor(tier: LoyaltyTier): Color {
    return when (tier) {
        LoyaltyTier.BRONZE -> Color(0xFFCD7F32)
        LoyaltyTier.SILVER -> Color(0xFFC0C0C0)
        LoyaltyTier.GOLD -> Color(0xFFFFD700)
        LoyaltyTier.PLATINUM -> Color(0xFFE5E4E2)
    }
}
