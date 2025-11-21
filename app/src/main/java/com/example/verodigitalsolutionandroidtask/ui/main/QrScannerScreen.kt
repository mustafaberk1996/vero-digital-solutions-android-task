package com.example.verodigitalsolutionandroidtask.ui.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.verodigitalsolutionandroidtask.ui.theme.VeroDigitalSolutionAndroidTaskTheme
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage


@Composable
fun QrCodeScannerScreen(
    modifier: Modifier = Modifier,
    onCloseClicked: () -> Unit,
    onQrScanned: (String) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.White, shape = RoundedCornerShape(4.dp)),
        contentAlignment = Alignment.Center
    ) {

        var cameraPermissionGranted by rememberSaveable { mutableStateOf(false) }
        if (!cameraPermissionGranted){
            CameraPermission {
                cameraPermissionGranted = true
            }
        }else{
            QrScanner(onQrScanned = onQrScanned)
        }

        Icon(
            imageVector = Icons.Outlined.Close,
            contentDescription = "Scan Qr Code",
            modifier = Modifier
                .padding(8.dp)
                .size(32.dp)
                .align(alignment = Alignment.TopEnd)
                .clickable {
                    onCloseClicked()
                }
        )
    }
}

@Preview
@Composable
private fun QrCodeScannerScreenPreview() {
    VeroDigitalSolutionAndroidTaskTheme {
        QrCodeScannerScreen(onCloseClicked = {}, onQrScanned = {})
    }
}

@Composable
fun CameraPermission(
    modifier: Modifier = Modifier,
    onPermissionGranted: () -> Unit
) {
    val context = LocalContext.current
    val cameraPermission = Manifest.permission.CAMERA
    val activity = context as? Activity
    var hasPermission by remember { mutableStateOf(ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) ==
            PackageManager.PERMISSION_GRANTED) }
    var permanentlyDenied by remember { mutableStateOf(
        (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            && !ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.CAMERA))
     }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            onPermissionGranted()
            hasPermission = true
            permanentlyDenied = false
        } else {
            permanentlyDenied = !ActivityCompat.shouldShowRequestPermissionRationale(
                activity!!,
                Manifest.permission.CAMERA
            )
        }

    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (ContextCompat.checkSelfPermission(context, cameraPermission)
                    == PackageManager.PERMISSION_GRANTED
                ) {
                    onPermissionGranted()
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

if (!hasPermission){
    if (permanentlyDenied){
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Camera permission is permanently denied. You need to enable it from settings.", textAlign = TextAlign.Center)
            Button(onClick = {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }
                context.startActivity(intent)
            }) {
                Text("Open Settings")
            }
        }
    }else{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = "Camera",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(80.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Camera Permission Required",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "We need access to your camera to scan QR codes and allow quick searches directly from the app.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { launcher.launch(cameraPermission) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Allow")
                }
            }
        }
    }
}

}

@Preview
@Composable
private fun CameraPermissionPreview() {
    VeroDigitalSolutionAndroidTaskTheme {
        CameraPermission(onPermissionGranted = {})
    }
}


@Composable
fun QrScanner(modifier: Modifier = Modifier, onQrScanned: (String) -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val scanner = remember {
        BarcodeScanning.getClient(
            BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                .build()
        )
    }

    val cameraController = remember {
        LifecycleCameraController(context).apply {
            setEnabledUseCases(CameraController.IMAGE_ANALYSIS or CameraController.IMAGE_CAPTURE)
        }
    }

    DisposableEffect(Unit) {
        cameraController.bindToLifecycle(lifecycleOwner)
        onDispose {
            cameraController.unbind()
        }
    }

    LaunchedEffect(cameraController) {
        cameraController.setImageAnalysisAnalyzer(
            ContextCompat.getMainExecutor(context)
        ) { imageProxy ->

            val mediaImage = imageProxy.image
            if (mediaImage != null) {
                val input = InputImage.fromMediaImage(
                    mediaImage,
                    imageProxy.imageInfo.rotationDegrees
                )

                scanner.process(input)
                    .addOnSuccessListener { barcodes ->
                        for (barcode in barcodes) {
                            barcode.rawValue?.let { value ->
                                onQrScanned(value)
                            }
                        }
                    }
                    .addOnCompleteListener {
                        imageProxy.close()
                    }
            } else {
                imageProxy.close()
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()){

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { ctx ->
            PreviewView(ctx).apply {
                this.controller = cameraController
            }
        }
    )
        Text(
            text = "Scan QR Code",
            color = Color.White,
            fontSize = 25.sp,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
        )



    }
}
