import SwiftUI
import AVFoundation
import shared

struct BarcodeScannerView: View {
    @Environment(\.dismiss) var dismiss
    @StateObject private var viewModel = BarcodeScannerViewModel()
    @State private var showManualEntry = false
    @State private var showAlert = false
    @State private var alertTitle = ""
    @State private var alertMessage = ""
    @State private var scanSuccess = false
    
    var onScanComplete: (Bool, Int32) -> Void
    
    var body: some View {
        ZStack {
            if viewModel.isAuthorized {
                if showManualEntry {
                    ManualBarcodeEntryView(
                        onSubmit: { code in
                            processBarcode(code)
                        },
                        onSwitchToCamera: {
                            showManualEntry = false
                        }
                    )
                } else {
                    CameraPreviewView(viewModel: viewModel)
                        .overlay(alignment: .bottom) {
                            VStack(spacing: 16) {
                                Text("📱 Position barcode in the center")
                                    .font(.system(size: 16))
                                    .foregroundColor(.white)
                                    .padding(.horizontal, 20)
                                    .padding(.vertical, 12)
                                    .background(Color.black.opacity(0.7))
                                    .cornerRadius(12)
                                
                                Button(action: {
                                    showManualEntry = true
                                }) {
                                    Text("Enter Code Manually")
                                        .font(.system(size: 16, weight: .semibold))
                                        .foregroundColor(.white)
                                        .frame(maxWidth: .infinity)
                                        .padding(.vertical, 14)
                                        .background(CoffeeColors.coffeeBrown)
                                        .cornerRadius(12)
                                }
                                .padding(.horizontal, 24)
                            }
                            .padding(.bottom, 40)
                        }
                }
            } else {
                CameraPermissionView(
                    onRequestPermission: {
                        viewModel.requestCameraPermission()
                    }
                )
            }
        }
        .navigationTitle("Scan Receipt Barcode")
        .navigationBarTitleDisplayMode(.inline)
        .toolbar {
            ToolbarItem(placement: .navigationBarLeading) {
                Button("Cancel") {
                    dismiss()
                }
            }
        }
        .onAppear {
            viewModel.checkCameraPermission()
            viewModel.onBarcodeDetected = { code in
                processBarcode(code)
            }
        }
        .onDisappear {
            viewModel.stopScanning()
        }
        .alert(alertTitle, isPresented: $showAlert) {
            Button("OK") {
                if scanSuccess {
                    onScanComplete(true, viewModel.lastPointsAdded)
                    dismiss()
                }
            }
        } message: {
            Text(alertMessage)
        }
    }
    
    private func processBarcode(_ code: String) {
        viewModel.processBarcode(code) { result in
            alertTitle = result.success ? "Success!" : "Error"
            alertMessage = result.message
            scanSuccess = result.success
            showAlert = true
        }
    }
}

struct CameraPreviewView: UIViewRepresentable {
    @ObservedObject var viewModel: BarcodeScannerViewModel
    
    func makeUIView(context: Context) -> UIView {
        return viewModel.previewView
    }
    
    func updateUIView(_ uiView: UIView, context: Context) {}
}

struct CameraPermissionView: View {
    let onRequestPermission: () -> Void
    
    var body: some View {
        VStack(spacing: 24) {
            Text("📷")
                .font(.system(size: 64))
            
            Text("Camera Permission Required")
                .font(.system(size: 24, weight: .bold))
                .foregroundColor(CoffeeColors.darkCoffee)
                .multilineTextAlignment(.center)
            
            Text("To scan barcodes, we need access to your camera")
                .font(.system(size: 16))
                .foregroundColor(CoffeeColors.coffeeBrown)
                .multilineTextAlignment(.center)
                .padding(.horizontal, 32)
            
            Button(action: onRequestPermission) {
                Text("Grant Permission")
                    .font(.system(size: 16, weight: .semibold))
                    .foregroundColor(.white)
                    .frame(maxWidth: .infinity)
                    .padding(.vertical, 14)
                    .background(CoffeeColors.coffeeBrown)
                    .cornerRadius(12)
            }
            .padding(.horizontal, 32)
        }
        .padding()
        .background(CoffeeColors.creamyWhite)
    }
}

struct ManualBarcodeEntryView: View {
    @State private var barcodeText = ""
    @State private var isProcessing = false
    
    let onSubmit: (String) -> Void
    let onSwitchToCamera: () -> Void
    
    var body: some View {
        VStack(spacing: 24) {
            Spacer()
            
            VStack(spacing: 24) {
                Text("⌨️")
                    .font(.system(size: 48))
                
                Text("Enter Barcode Manually")
                    .font(.system(size: 24, weight: .bold))
                    .foregroundColor(CoffeeColors.darkCoffee)
                
                Text("Type the code from your receipt")
                    .font(.system(size: 16))
                    .foregroundColor(CoffeeColors.coffeeBrown)
                    .multilineTextAlignment(.center)
                
                TextField("RECEIPT-...", text: $barcodeText)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                    .autocapitalization(.allCharacters)
                    .disableAutocorrection(true)
                    .padding(.horizontal, 32)
                
                Button(action: {
                    if !barcodeText.isEmpty && !isProcessing {
                        isProcessing = true
                        onSubmit(barcodeText)
                    }
                }) {
                    Text("Submit")
                        .font(.system(size: 16, weight: .semibold))
                        .foregroundColor(.white)
                        .frame(maxWidth: .infinity)
                        .padding(.vertical, 14)
                        .background(barcodeText.isEmpty ? Color.gray : CoffeeColors.coffeeBrown)
                        .cornerRadius(12)
                }
                .disabled(barcodeText.isEmpty || isProcessing)
                .padding(.horizontal, 32)
                
                Button(action: onSwitchToCamera) {
                    Text("📷 Switch to Camera Scanner")
                        .font(.system(size: 16))
                        .foregroundColor(CoffeeColors.coffeeBrown)
                }
            }
            .padding(24)
            .background(CoffeeColors.latteFoam)
            .cornerRadius(16)
            .shadow(color: Color.black.opacity(0.1), radius: 8, x: 0, y: 4)
            .padding(.horizontal, 24)
            
            Spacer()
        }
        .background(CoffeeColors.creamyWhite)
    }
}

class BarcodeScannerViewModel: NSObject, ObservableObject, AVCaptureMetadataOutputObjectsDelegate {
    @Published var isAuthorized = false
    @Published var lastPointsAdded: Int32 = 0
    
    let previewView = UIView()
    private var captureSession: AVCaptureSession?
    private var previewLayer: AVCaptureVideoPreviewLayer?
    private let repository: CoffeeRepository = MockCoffeeRepository()
    private var isProcessing = false
    
    var onBarcodeDetected: ((String) -> Void)?
    
    func checkCameraPermission() {
        switch AVCaptureDevice.authorizationStatus(for: .video) {
        case .authorized:
            isAuthorized = true
            setupCamera()
        case .notDetermined:
            break
        default:
            isAuthorized = false
        }
    }
    
    func requestCameraPermission() {
        AVCaptureDevice.requestAccess(for: .video) { [weak self] granted in
            DispatchQueue.main.async {
                self?.isAuthorized = granted
                if granted {
                    self?.setupCamera()
                } else {
                    // Open settings
                    if let settingsURL = URL(string: UIApplication.openSettingsURLString) {
                        UIApplication.shared.open(settingsURL)
                    }
                }
            }
        }
    }
    
    private func setupCamera() {
        DispatchQueue.global(qos: .userInitiated).async { [weak self] in
            guard let self = self else { return }
            
            let session = AVCaptureSession()
            session.beginConfiguration()
            
            guard let videoCaptureDevice = AVCaptureDevice.default(for: .video),
                  let videoInput = try? AVCaptureDeviceInput(device: videoCaptureDevice),
                  session.canAddInput(videoInput) else {
                return
            }
            
            session.addInput(videoInput)
            
            let metadataOutput = AVCaptureMetadataOutput()
            
            if session.canAddOutput(metadataOutput) {
                session.addOutput(metadataOutput)
                
                metadataOutput.setMetadataObjectsDelegate(self, queue: DispatchQueue.main)
                metadataOutput.metadataObjectTypes = [.qr, .ean8, .ean13, .pdf417, .code128]
            }
            
            session.commitConfiguration()
            
            DispatchQueue.main.async {
                let previewLayer = AVCaptureVideoPreviewLayer(session: session)
                previewLayer.frame = self.previewView.bounds
                previewLayer.videoGravity = .resizeAspectFill
                self.previewView.layer.addSublayer(previewLayer)
                self.previewLayer = previewLayer
                self.captureSession = session
                
                DispatchQueue.global(qos: .userInitiated).async {
                    session.startRunning()
                }
            }
        }
    }
    
    func metadataOutput(_ output: AVCaptureMetadataOutput, didOutput metadataObjects: [AVMetadataObject], from connection: AVCaptureConnection) {
        guard !isProcessing else { return }
        
        if let metadataObject = metadataObjects.first as? AVMetadataMachineReadableCodeObject,
           let stringValue = metadataObject.stringValue {
            isProcessing = true
            onBarcodeDetected?(stringValue)
        }
    }
    
    func processBarcode(_ code: String, completion: @escaping (BarcodeScanResult) -> Void) {
        let result = repository.processReceiptBarcode(barcodeCode: code)
        lastPointsAdded = result.pointsAdded
        DispatchQueue.main.async {
            completion(result)
        }
    }
    
    func stopScanning() {
        captureSession?.stopRunning()
    }
}

#Preview {
    NavigationView {
        BarcodeScannerView(onScanComplete: { _, _ in })
    }
}
