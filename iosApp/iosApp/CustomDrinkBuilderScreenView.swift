import SwiftUI
import shared

struct CustomDrinkBuilderScreenView: View {
    @StateObject private var viewModel = CustomDrinkBuilderViewModel()
    
    var body: some View {
        VStack(spacing: 0) {
            CustomDrinkBuilderHeader(currentStep: viewModel.currentStep)
            
            if viewModel.selectedMenuItem != nil {
                PricePreviewCard(totalPrice: viewModel.totalPrice)
            }
            
            ScrollView {
                VStack(spacing: 12) {
                    switch viewModel.currentStep {
                    case 1:
                        StepOneContent(viewModel: viewModel)
                    case 2:
                        StepTwoContent(viewModel: viewModel)
                    case 3:
                        StepThreeContent(viewModel: viewModel)
                    default:
                        EmptyView()
                    }
                }
                .padding(16)
            }
            
            NavigationButtons(viewModel: viewModel)
        }
        .background(CoffeeColors.creamyWhite)
        .edgesIgnoringSafeArea(.top)
        .sheet(isPresented: $viewModel.showSaveDialog) {
            SaveCustomDrinkDialog(viewModel: viewModel)
        }
        .alert("Success!", isPresented: $viewModel.showSuccessMessage) {
            Button("OK", role: .cancel) {}
        } message: {
            Text("Your custom drink has been saved for quick reordering.")
        }
    }
}

struct CustomDrinkBuilderHeader: View {
    let currentStep: Int
    
    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text("Custom Drink Builder")
                .font(.system(size: 28, weight: .bold))
                .foregroundColor(.white)
            
            Text("Create your perfect beverage")
                .font(.system(size: 16))
                .foregroundColor(.white.opacity(0.9))
            
            StepIndicator(currentStep: currentStep)
                .padding(.top, 16)
        }
        .frame(maxWidth: .infinity, alignment: .leading)
        .padding(24)
        .background(CoffeeColors.coffeeBrown)
    }
}

struct StepIndicator: View {
    let currentStep: Int
    
    var body: some View {
        HStack {
            ForEach(1...3, id: \.self) { step in
                HStack {
                    ZStack {
                        Circle()
                            .fill(step <= currentStep ? CoffeeColors.creamyWhite : CoffeeColors.creamyWhite.opacity(0.5))
                            .frame(width: 32, height: 32)
                        
                        Text("\(step)")
                            .font(.system(size: 16, weight: .bold))
                            .foregroundColor(step <= currentStep ? CoffeeColors.coffeeBrown : CoffeeColors.coffeeBrown.opacity(0.5))
                    }
                    
                    if step < 3 {
                        Spacer()
                    }
                }
            }
        }
    }
}

struct PricePreviewCard: View {
    let totalPrice: Double
    
    var body: some View {
        HStack {
            Text("Total Price:")
                .font(.system(size: 18, weight: .semibold))
                .foregroundColor(CoffeeColors.darkCoffee)
            
            Spacer()
            
            Text(String(format: "$%.2f", totalPrice))
                .font(.system(size: 24, weight: .bold))
                .foregroundColor(CoffeeColors.coffeeBrown)
        }
        .padding(16)
        .background(CoffeeColors.latteFoam)
        .cornerRadius(12)
        .shadow(color: Color.black.opacity(0.1), radius: 4, x: 0, y: 2)
        .padding(.horizontal, 16)
        .padding(.vertical, 8)
    }
}

struct StepOneContent: View {
    @ObservedObject var viewModel: CustomDrinkBuilderViewModel
    
    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text("Step 1: Choose Your Base Drink")
                .font(.system(size: 20, weight: .semibold))
                .foregroundColor(CoffeeColors.darkCoffee)
                .padding(.bottom, 8)
            
            ForEach(viewModel.menuItems, id: \.id) { item in
                MenuItemSelectionCard(
                    item: item,
                    isSelected: viewModel.selectedMenuItem?.id == item.id,
                    onClick: {
                        viewModel.selectMenuItem(item)
                    }
                )
            }
        }
    }
}

struct StepTwoContent: View {
    @ObservedObject var viewModel: CustomDrinkBuilderViewModel
    
    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text("Step 2: Select Size")
                .font(.system(size: 20, weight: .semibold))
                .foregroundColor(CoffeeColors.darkCoffee)
                .padding(.bottom, 8)
            
            ForEach([DrinkSize.small, DrinkSize.medium, DrinkSize.large], id: \.displayName) { size in
                SizeSelectionCard(
                    size: size,
                    basePrice: viewModel.selectedMenuItem?.price ?? 0.0,
                    isSelected: viewModel.selectedSize.displayName == size.displayName,
                    onClick: {
                        viewModel.selectSize(size)
                    }
                )
            }
        }
    }
}

struct StepThreeContent: View {
    @ObservedObject var viewModel: CustomDrinkBuilderViewModel
    
    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text("Step 3: Add Extras (Optional)")
                .font(.system(size: 20, weight: .semibold))
                .foregroundColor(CoffeeColors.darkCoffee)
                .padding(.bottom, 8)
            
            ForEach(viewModel.availableAddOns, id: \.id) { addOn in
                AddOnSelectionCard(
                    addOn: addOn,
                    isSelected: viewModel.selectedAddOns.contains(where: { $0.id == addOn.id }),
                    onClick: {
                        viewModel.toggleAddOn(addOn)
                    }
                )
            }
        }
    }
}

struct MenuItemSelectionCard: View {
    let item: MenuItem
    let isSelected: Bool
    let onClick: () -> Void
    
    var body: some View {
        Button(action: onClick) {
            HStack(spacing: 16) {
                ZStack {
                    RoundedRectangle(cornerRadius: 8)
                        .fill(CoffeeColors.coffeeBrown.opacity(0.3))
                        .frame(width: 60, height: 60)
                    
                    Text("☕")
                        .font(.system(size: 32))
                }
                
                VStack(alignment: .leading, spacing: 4) {
                    Text(item.name)
                        .font(.system(size: 18, weight: .semibold))
                        .foregroundColor(CoffeeColors.darkCoffee)
                    
                    Text(String(format: "$%.2f", item.price))
                        .font(.system(size: 16, weight: .bold))
                        .foregroundColor(CoffeeColors.coffeeBrown)
                }
                
                Spacer()
                
                if isSelected {
                    Image(systemName: "checkmark.circle.fill")
                        .resizable()
                        .frame(width: 28, height: 28)
                        .foregroundColor(CoffeeColors.coffeeBrown)
                }
            }
            .padding(16)
            .background(CoffeeColors.latteFoam)
            .cornerRadius(12)
            .overlay(
                RoundedRectangle(cornerRadius: 12)
                    .stroke(isSelected ? CoffeeColors.coffeeBrown : Color.clear, lineWidth: 2)
            )
            .shadow(color: Color.black.opacity(0.1), radius: 2, x: 0, y: 2)
        }
        .buttonStyle(PlainButtonStyle())
    }
}

struct SizeSelectionCard: View {
    let size: DrinkSize
    let basePrice: Double
    let isSelected: Bool
    let onClick: () -> Void
    
    var body: some View {
        Button(action: onClick) {
            HStack {
                VStack(alignment: .leading, spacing: 4) {
                    Text(size.displayName)
                        .font(.system(size: 18, weight: .semibold))
                        .foregroundColor(CoffeeColors.darkCoffee)
                    
                    Text(String(format: "$%.2f", basePrice * size.priceMultiplier))
                        .font(.system(size: 16, weight: .bold))
                        .foregroundColor(CoffeeColors.coffeeBrown)
                }
                
                Spacer()
                
                if isSelected {
                    Image(systemName: "checkmark.circle.fill")
                        .resizable()
                        .frame(width: 28, height: 28)
                        .foregroundColor(CoffeeColors.coffeeBrown)
                }
            }
            .padding(16)
            .background(CoffeeColors.latteFoam)
            .cornerRadius(12)
            .overlay(
                RoundedRectangle(cornerRadius: 12)
                    .stroke(isSelected ? CoffeeColors.coffeeBrown : Color.clear, lineWidth: 2)
            )
            .shadow(color: Color.black.opacity(0.1), radius: 2, x: 0, y: 2)
        }
        .buttonStyle(PlainButtonStyle())
    }
}

struct AddOnSelectionCard: View {
    let addOn: AddOn
    let isSelected: Bool
    let onClick: () -> Void
    
    var body: some View {
        Button(action: onClick) {
            HStack {
                VStack(alignment: .leading, spacing: 4) {
                    Text(addOn.name)
                        .font(.system(size: 16))
                        .foregroundColor(CoffeeColors.darkCoffee)
                    
                    Text(String(format: "+$%.2f", addOn.price))
                        .font(.system(size: 14))
                        .foregroundColor(CoffeeColors.coffeeBrown)
                }
                
                Spacer()
                
                if isSelected {
                    Image(systemName: "checkmark.circle.fill")
                        .resizable()
                        .frame(width: 24, height: 24)
                        .foregroundColor(CoffeeColors.coffeeBrown)
                }
            }
            .padding(16)
            .background(CoffeeColors.latteFoam)
            .cornerRadius(12)
            .overlay(
                RoundedRectangle(cornerRadius: 12)
                    .stroke(isSelected ? CoffeeColors.coffeeBrown : Color.clear, lineWidth: 2)
            )
            .shadow(color: Color.black.opacity(0.1), radius: 2, x: 0, y: 2)
        }
        .buttonStyle(PlainButtonStyle())
    }
}

struct NavigationButtons: View {
    @ObservedObject var viewModel: CustomDrinkBuilderViewModel
    
    var body: some View {
        HStack(spacing: 16) {
            if viewModel.currentStep > 1 {
                Button(action: {
                    viewModel.goToPreviousStep()
                }) {
                    Text("Previous")
                        .font(.system(size: 16, weight: .semibold))
                        .foregroundColor(CoffeeColors.coffeeBrown)
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(CoffeeColors.latteFoam)
                        .cornerRadius(12)
                }
            }
            
            Button(action: {
                viewModel.goToNextStep()
            }) {
                Text(viewModel.currentStep == 3 ? "Save Drink" : "Next")
                    .font(.system(size: 16, weight: .semibold))
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .padding()
                    .background(viewModel.canProceed ? CoffeeColors.coffeeBrown : CoffeeColors.coffeeBrown.opacity(0.5))
                    .cornerRadius(12)
            }
            .disabled(!viewModel.canProceed)
        }
        .padding(16)
        .background(CoffeeColors.latteFoam)
    }
}

struct SaveCustomDrinkDialog: View {
    @ObservedObject var viewModel: CustomDrinkBuilderViewModel
    
    var body: some View {
        NavigationView {
            VStack(spacing: 20) {
                Text("Give your drink a custom name (optional):")
                    .font(.system(size: 16))
                    .foregroundColor(CoffeeColors.darkCoffee)
                
                TextField("e.g., My Perfect Latte", text: $viewModel.customName)
                    .padding()
                    .background(CoffeeColors.creamyWhite)
                    .cornerRadius(8)
                    .overlay(
                        RoundedRectangle(cornerRadius: 8)
                            .stroke(CoffeeColors.coffeeBrown.opacity(0.3), lineWidth: 1)
                    )
                
                Spacer()
            }
            .padding()
            .navigationTitle("Save Custom Drink")
            .navigationBarTitleDisplayMode(.inline)
            .toolbar {
                ToolbarItem(placement: .navigationBarLeading) {
                    Button("Cancel") {
                        viewModel.showSaveDialog = false
                    }
                }
                ToolbarItem(placement: .navigationBarTrailing) {
                    Button("Save") {
                        viewModel.saveCustomDrink()
                    }
                }
            }
        }
    }
}

class CustomDrinkBuilderViewModel: ObservableObject {
    @Published var currentStep: Int = 1
    @Published var selectedMenuItem: MenuItem? = nil
    @Published var selectedSize: DrinkSize = DrinkSize.medium
    @Published var selectedAddOns: [AddOn] = []
    @Published var customName: String = ""
    @Published var showSaveDialog: Bool = false
    @Published var showSuccessMessage: Bool = false
    @Published var menuItems: [MenuItem] = []
    @Published var availableAddOns: [AddOn] = []
    
    private let presenter: CustomDrinkBuilderPresenter
    
    var totalPrice: Double {
        guard let item = selectedMenuItem else { return 0.0 }
        return presenter.calculateTotalPrice(
            baseMenuItem: item,
            size: selectedSize,
            addOns: selectedAddOns
        )
    }
    
    var canProceed: Bool {
        if currentStep == 1 {
            return selectedMenuItem != nil
        }
        return true
    }
    
    init() {
        let repository = MockCoffeeRepository()
        self.presenter = CustomDrinkBuilderPresenter(repository: repository)
        loadData()
    }
    
    private func loadData() {
        menuItems = presenter.getMenuItems()
        availableAddOns = presenter.getAvailableAddOns()
    }
    
    func selectMenuItem(_ item: MenuItem) {
        selectedMenuItem = item
    }
    
    func selectSize(_ size: DrinkSize) {
        selectedSize = size
    }
    
    func toggleAddOn(_ addOn: AddOn) {
        if let index = selectedAddOns.firstIndex(where: { $0.id == addOn.id }) {
            selectedAddOns.remove(at: index)
        } else {
            selectedAddOns.append(addOn)
        }
    }
    
    func goToPreviousStep() {
        if currentStep > 1 {
            currentStep -= 1
        }
    }
    
    func goToNextStep() {
        if currentStep < 3 {
            currentStep += 1
        } else {
            showSaveDialog = true
        }
    }
    
    func saveCustomDrink() {
        guard let item = selectedMenuItem else { return }
        
        _ = presenter.saveCustomDrink(
            baseMenuItem: item,
            size: selectedSize,
            addOns: selectedAddOns,
            customName: customName.isEmpty ? nil : customName
        )
        
        showSaveDialog = false
        showSuccessMessage = true
    }
}

#Preview {
    CustomDrinkBuilderScreenView()
}
