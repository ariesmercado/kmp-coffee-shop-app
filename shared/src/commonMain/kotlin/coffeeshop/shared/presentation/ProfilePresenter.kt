package coffeeshop.shared.presentation

import coffeeshop.shared.data.model.LoyaltyMembership
import coffeeshop.shared.data.model.RewardTransaction
import coffeeshop.shared.data.model.User
import coffeeshop.shared.data.repository.CoffeeRepository
import coffeeshop.shared.utils.RewardPointsCalculator

class ProfilePresenter(private val repository: CoffeeRepository) {
    
    fun getCurrentUser(): User {
        return repository.getCurrentUser()
    }
    
    fun getRewardPointsBalance(): Int {
        return repository.getRewardPointsBalance()
    }
    
    fun getRewardTransactions(): List<RewardTransaction> {
        return repository.getRewardTransactions()
    }
    
    fun getPointsToNextTier(): Int {
        val currentPoints = repository.getRewardPointsBalance()
        return RewardPointsCalculator.getPointsToNextTier(currentPoints)
    }
    
    fun canRedeemPoints(): Boolean {
        val currentPoints = repository.getRewardPointsBalance()
        return RewardPointsCalculator.canRedeemPoints(currentPoints)
    }
    
    fun getRedemptionOptions(): List<Pair<Int, Double>> {
        val currentPoints = repository.getRewardPointsBalance()
        return RewardPointsCalculator.getRedemptionOptions(currentPoints)
    }
    
    fun redeemPoints(points: Int): Boolean {
        val currentBalance = repository.getRewardPointsBalance()
        
        // Validate points is multiple of 100
        if (points < 100 || points % 100 != 0) {
            return false
        }
        
        // Validate sufficient balance
        if (points > currentBalance) {
            return false
        }
        
        val discount = RewardPointsCalculator.calculateDiscountFromPoints(points)
        return repository.redeemRewardPoints(points, "Redeemed for $${String.format("%.2f", discount)} discount")
    }
    
    fun getLoyaltyMembership(): LoyaltyMembership {
        val totalPointsEarned = repository.getTotalPointsEarned()
        val currentPoints = repository.getRewardPointsBalance()
        return RewardPointsCalculator.getLoyaltyMembership(totalPointsEarned, currentPoints)
    }
}
