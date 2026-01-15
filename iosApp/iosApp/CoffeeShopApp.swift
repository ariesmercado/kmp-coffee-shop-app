import SwiftUI

@main
struct CoffeeShopApp: App {
    var body: some Scene {
        WindowGroup {
            // To view the Order History Screen, uncomment the line below
            OrderHistoryScreenView()
            // To view the Payment Screen, uncomment the line below and comment out OrderHistoryScreenView()
            // PaymentScreenView()
            // To view the Order Summary Screen, uncomment the line below and comment out OrderHistoryScreenView()
            // OrderSummaryScreenView()
            // HomeScreenView()
        }
    }
}
