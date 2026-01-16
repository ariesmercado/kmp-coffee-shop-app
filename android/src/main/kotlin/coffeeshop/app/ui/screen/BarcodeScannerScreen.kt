package coffeeshop.app.ui.screen

import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import coffeeshop.app.ui.theme.*
import coffeeshop.shared.data.repository.MockCoffeeRepository
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BarcodeScannerScreen(
    repository: coffeeshop.shared.data.repository.CoffeeRepository = remember { MockCoffeeRepository() },
    onScanComplete: (success: Boolean, points: Int, message: String) -> Unit = { _, _, _ -> },
    onBackClick: () -> Unit = {}
) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    var showManualEntry by remember { mutableStateOf(false) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
    ) {
        BarcodeScannerHeader(onBackClick)
        
        if (cameraPermissionState.status.isGranted) {
            if (showManualEntry) {
                ManualBarcodeEntry(
                    repository = repository,
                    onScanComplete = onScanComplete,
                    onSwitchToCamera = { showManualEntry = false }
                )
            } else {
                CameraPreview(
                    repository = repository,
                    onScanComplete = onScanComplete,
                    onSwitchToManual = { showManualEntry = true }
                )
            }
        } else {
            CameraPermissionRequest(cameraPermissionState)
        }
    }
}

@Composable
fun BarcodeScannerHeader(onBackClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(24.dp)
    ) {
        Button(
            onClick = onBackClick,
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White.copy(alpha = 0.2f)),
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("← Back", color = Color.White)
        }
        
        Text(
            text = "Scan Receipt Barcode",
            style = MaterialTheme.typography.h1.copy(
                color = MaterialTheme.colors.onPrimary,
                fontSize = 28.sp
            )
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Position the barcode within the frame",
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.9f),
                fontSize = 16.sp
            )
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermissionRequest(cameraPermissionState: PermissionState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "📷",
            fontSize = 64.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = "Camera Permission Required",
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "To scan barcodes, we need access to your camera",
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        Button(
            onClick = { cameraPermissionState.launchPermissionRequest() },
            colors = ButtonDefaults.buttonColors(backgroundColor = CoffeeBrown),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Grant Permission", color = Color.White, fontSize = 16.sp)
        }
    }
}

@Composable
fun CameraPreview(
    repository: coffeeshop.shared.data.repository.CoffeeRepository,
    onScanComplete: (success: Boolean, points: Int, message: String) -> Unit,
    onSwitchToManual: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var isProcessing by remember { mutableStateOf(false) }
    
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                val previewView = PreviewView(ctx)
                val cameraProviderFuture = ProcessCameraProvider.getInstance(ctx)
                
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()
                    val preview = Preview.Builder().build().also {
                        it.setSurfaceProvider(previewView.surfaceProvider)
                    }
                    
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                    
                    imageAnalysis.setAnalyzer(Executors.newSingleThreadExecutor()) { imageProxy ->
                        if (!isProcessing) {
                            processImageProxy(imageProxy, repository) { success, points, message ->
                                isProcessing = true
                                onScanComplete(success, points, message)
                            }
                        } else {
                            imageProxy.close()
                        }
                    }
                    
                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    
                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            imageAnalysis
                        )
                    } catch (e: Exception) {
                        Toast.makeText(ctx, "Camera initialization failed", Toast.LENGTH_SHORT).show()
                    }
                }, ContextCompat.getMainExecutor(ctx))
                
                previewView
            },
            modifier = Modifier.fillMaxSize()
        )
        
        // Scanning frame overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.Center)
                .padding(32.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            )
        }
        
        // Instructions card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "📱 Position barcode in the center",
                    style = MaterialTheme.typography.body1.copy(fontSize = 16.sp),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = onSwitchToManual,
                    colors = ButtonDefaults.buttonColors(backgroundColor = CoffeeBrown),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Enter Code Manually", color = Color.White)
                }
            }
        }
    }
}

@Composable
fun ManualBarcodeEntry(
    repository: coffeeshop.shared.data.repository.CoffeeRepository,
    onScanComplete: (success: Boolean, points: Int, message: String) -> Unit,
    onSwitchToCamera: () -> Unit
) {
    var barcodeText by remember { mutableStateOf("") }
    var isProcessing by remember { mutableStateOf(false) }
    val context = LocalContext.current
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            shape = RoundedCornerShape(16.dp),
            elevation = 4.dp,
            backgroundColor = MaterialTheme.colors.surface
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "⌨️",
                    fontSize = 48.sp,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                
                Text(
                    text = "Enter Barcode Manually",
                    style = MaterialTheme.typography.h2,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                
                Text(
                    text = "Type the code from your receipt",
                    style = MaterialTheme.typography.body2.copy(
                        color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
                
                OutlinedTextField(
                    value = barcodeText,
                    onValueChange = { barcodeText = it },
                    label = { Text("Barcode") },
                    placeholder = { Text("RECEIPT-...") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            if (barcodeText.isNotBlank() && !isProcessing) {
                                isProcessing = true
                                val result = repository.processReceiptBarcode(barcodeText)
                                Toast.makeText(context, result.message, Toast.LENGTH_LONG).show()
                                onScanComplete(result.success, result.pointsAdded, result.message)
                            }
                        }
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                )
                
                Button(
                    onClick = {
                        if (barcodeText.isNotBlank() && !isProcessing) {
                            isProcessing = true
                            val result = repository.processReceiptBarcode(barcodeText)
                            Toast.makeText(context, result.message, Toast.LENGTH_LONG).show()
                            onScanComplete(result.success, result.pointsAdded, result.message)
                        }
                    },
                    enabled = barcodeText.isNotBlank() && !isProcessing,
                    colors = ButtonDefaults.buttonColors(backgroundColor = CoffeeBrown),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text("Submit", color = Color.White, fontSize = 16.sp)
                }
                
                Spacer(modifier = Modifier.height(12.dp))
                
                TextButton(onClick = onSwitchToCamera) {
                    Text("📷 Switch to Camera Scanner", color = CoffeeBrown)
                }
            }
        }
    }
}

@androidx.annotation.OptIn(ExperimentalGetImage::class)
private fun processImageProxy(
    imageProxy: ImageProxy,
    repository: coffeeshop.shared.data.repository.CoffeeRepository,
    onBarcodeDetected: (success: Boolean, points: Int, message: String) -> Unit
) {
    val mediaImage = imageProxy.image
    if (mediaImage != null) {
        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
        val scanner = BarcodeScanning.getClient()
        
        scanner.process(image)
            .addOnSuccessListener { barcodes ->
                for (barcode in barcodes) {
                    when (barcode.valueType) {
                        Barcode.TYPE_TEXT -> {
                            barcode.rawValue?.let { code ->
                                val result = repository.processReceiptBarcode(code)
                                onBarcodeDetected(result.success, result.pointsAdded, result.message)
                            }
                        }
                        else -> { /* Ignore other barcode types */ }
                    }
                }
            }
            .addOnFailureListener {
                // Silent failure for image processing errors
            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    } else {
        imageProxy.close()
    }
}
