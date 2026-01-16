import SwiftUI

// Premium Coffee Theme Colors - Matching Android Design
struct CoffeeColors {
    // Primary Browns - Rich Coffee Tones
    static let coffeeBrown = Color(red: 0.435, green: 0.306, blue: 0.216)       // #6F4E37
    static let lightCoffee = Color(red: 0.627, green: 0.322, blue: 0.176)       // #A0522D
    static let darkCoffee = Color(red: 0.243, green: 0.149, blue: 0.137)        // #3E2723
    static let espressoBlack = Color(red: 0.106, green: 0.059, blue: 0.039)     // #1B0F0A
    
    // Neutrals - Creamy and Soft
    static let creamyWhite = Color(red: 1.0, green: 0.973, blue: 0.906)         // #FFF8E7
    static let latteFoam = Color(red: 1.0, green: 0.980, blue: 0.941)           // #FFFAF0
    static let softBeige = Color(red: 0.961, green: 0.945, blue: 0.910)         // #F5F1E8
    static let warmIvory = Color(red: 1.0, green: 0.992, blue: 0.969)           // #FFFDF7
    
    // Accent Colors - Elegant Highlights
    static let caramelBrown = Color(red: 0.824, green: 0.412, blue: 0.118)      // #D2691E
    static let mochaBrown = Color(red: 0.545, green: 0.271, blue: 0.075)        // #8B4513
    static let goldenAccent = Color(red: 0.831, green: 0.686, blue: 0.216)      // #D4AF37
    static let darkGreenAccent = Color(red: 0.176, green: 0.314, blue: 0.086)   // #2D5016
    
    // Supporting Colors
    static let cinnamonBrown = Color(red: 0.769, green: 0.467, blue: 0.306)     // #C4764E
    static let hazelnutBrown = Color(red: 0.710, green: 0.565, blue: 0.353)     // #B5905A
    static let chocolateBrown = Color(red: 0.353, green: 0.227, blue: 0.161)    // #5A3A29
    
    // UI Enhancement Colors
    static let shadowColor = Color.black.opacity(0.10)
    static let cardShadow = Color.black.opacity(0.15)
    static let deepShadow = Color.black.opacity(0.20)
}

// Premium Typography Helpers
struct CoffeeTypography {
    // Serif fonts for headers (elegant)
    static func serifHeader(size: CGFloat, weight: Font.Weight = .bold) -> Font {
        .system(size: size, weight: weight, design: .serif)
    }
    
    // Sans-serif fonts for body (modern & readable)
    static func sansSerifBody(size: CGFloat, weight: Font.Weight = .regular) -> Font {
        .system(size: size, weight: weight, design: .default)
    }
}
