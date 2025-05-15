package com.example.freeupcopy.ui.presentation.sell_screen.gallery_screen

import android.Manifest
import android.app.Activity
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.transformer.EditedMediaItem
import androidx.media3.transformer.Transformer
import coil.compose.AsyncImage
import com.example.freeupcopy.R
import com.example.freeupcopy.common.Constants.MAX_IMAGES_UPLOAD
import com.example.freeupcopy.ui.presentation.sell_screen.SellUiEvent
import com.example.freeupcopy.ui.presentation.sell_screen.SellViewModel
import com.example.freeupcopy.ui.presentation.sell_screen.location_screen.location_screen.PleaseWaitLoading
import com.example.freeupcopy.ui.theme.ButtonShape
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import kotlin.math.min

// --- Custom ActivityResultContract for Video Capture with 10-second limit ---
class CaptureVideoWithLimit : ActivityResultContract<Uri, Boolean>() {
    override fun createIntent(context: Context, input: Uri): Intent {
        return Intent(MediaStore.ACTION_VIDEO_CAPTURE).apply {
            // Output URI where the video will be saved.
            putExtra(MediaStore.EXTRA_OUTPUT, input)
            // Limit the capture duration to 10 seconds.
            putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10)
        }
    }

    override fun parseResult(resultCode: Int, intent: Intent?): Boolean {

        return resultCode == android.app.Activity.RESULT_OK
    }
}

data class Album(
    val name: String,
    val count: Int
)

@Composable
fun CustomGalleryScreen(
    sellViewModel: SellViewModel,
    onClose: (List<String>, String?) -> Unit,
    numberOfUploadedImages: Int,
    galleryViewModel: GalleryViewModel = hiltViewModel()
) {
    val state by sellViewModel.state.collectAsState()
    val context = LocalContext.current
    val galleryState by galleryViewModel.state.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    // Track if we should show permission denied message
    var showPermissionDeniedMessage by remember { mutableStateOf(false) }

    // Define the storage permission based on Android version
    val storagePermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        Manifest.permission.READ_MEDIA_IMAGES
    } else {
        Manifest.permission.READ_EXTERNAL_STORAGE
    }

    // Create a state that will be updated when permission changes
    var permissionGranted by remember { mutableStateOf(false) }

    // Define the permission launcher
    val storagePermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        permissionGranted = isGranted
        showPermissionDeniedMessage = !isGranted
    }

    // Check permission status when the app resumes
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                // Check permission status when app resumes
                permissionGranted = ContextCompat.checkSelfPermission(
                    context,
                    storagePermission
                ) == PackageManager.PERMISSION_GRANTED

                showPermissionDeniedMessage = !permissionGranted
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    // Check permission on launch
    LaunchedEffect(Unit) {
        val isPermissionGranted = ContextCompat.checkSelfPermission(
            context,
            storagePermission
        ) == PackageManager.PERMISSION_GRANTED

        if (isPermissionGranted) {
            permissionGranted = true
        } else {
            // Request permission
            storagePermissionLauncher.launch(storagePermission)
        }

        Log.e("GalleryScreen: ", numberOfUploadedImages.toString())
    }

    LaunchedEffect(state.error) {
        state.error.takeIf { it.isNotBlank() }?.let {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(galleryState.onSuccessfulUpload) {
        if (galleryState.onSuccessfulUpload) {
            sellViewModel.onEvent(SellUiEvent.PopUpToSellScreen(false))
            Log.e("GalleryScreen uploaded video: ", galleryState.uploadedVideo ?: "null")
            onClose(galleryState.uploadedImage, galleryState.uploadedVideo)
        }
    }

    var mode by remember { mutableStateOf("Gallery") }

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        containerColor = MaterialTheme.colorScheme.surface,
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(bottom = 8.dp)
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, Color.Black.copy(0.3f), RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.primaryContainer),
                horizontalArrangement = Arrangement.Center
            ) {
                BottomBarItem(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(topStart = 12.dp, bottomStart = 12.dp)),
                    isSelected = mode == "Gallery",
                    color = Color.Black,
                    text = "Gallery",
                    onClick = { mode = "Gallery" }
                )
                if (mode == "Video") {
                    VerticalDivider(
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                BottomBarItem(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp)),
                    isSelected = mode == "Camera",
                    color = Color.Black,
                    text = "Camera",
                    onClick = { mode = "Camera" }
                )
                if (mode == "Gallery") {
                    VerticalDivider(
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
                BottomBarItem(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(bottomEnd = 12.dp, topEnd = 12.dp)),
                    isSelected = mode == "Video",
                    color = Color.Black,
                    text = "Video",
                    onClick = { mode = "Video" }
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            // Show permission denied message if needed
            if (showPermissionDeniedMessage) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Storage permission is required to access your media",
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (ActivityCompat.shouldShowRequestPermissionRationale(
                                    context as Activity, storagePermission
                                )
                            ) {
                                // User has denied once but not permanently
                                storagePermissionLauncher.launch(storagePermission)
                            } else {
                                // User has permanently denied, direct to settings
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.fromParts("package", context.packageName, null)
                                }
                                context.startActivity(intent)
                            }
                        },
                        shape = ButtonShape
                    ) {
                        Text("Grant Permission")
                    }
                }
            } else {
                when (mode) {
                    "Gallery" -> GalleryScreenContent(
                        isLoading = state.isLoading,
                        maxSelectable = MAX_IMAGES_UPLOAD - numberOfUploadedImages,
                        onUploadClick = { imageUris ->
                            // Convert Uris to Files. Assume uriToFile is defined elsewhere.
                            val fileList = imageUris.mapNotNull { uri ->
                                uriToFile(context, uri)
                            }
                            // Call the ViewModel event.
                            galleryViewModel.onEvent(GalleryUiEvent.UploadImages(fileList))
                        },
                        onClose = { onClose(emptyList(), null) }
                    )

                    "Camera" -> CameraScreenContent(
                        isLoading = state.isLoading,
                        onUploadClick = { image ->
                            val file = uriToFile(context, image)
                            file?.let {
                                galleryViewModel.onEvent(GalleryUiEvent.UploadImages(listOf(file)))
                            }
                        }
                    )

                    "Video" -> VideoScreenContent(
                        isLoading = state.isLoading,
                        onUploadClick = { videoUri ->
                            val file = uriToFile(context, videoUri)
                            file?.let {
                                galleryViewModel.onEvent(GalleryUiEvent.UploadVideo(file))
                            }
                        }
                    )
                }
            }
        }
    }

    if (galleryState.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(0.3f)),
            contentAlignment = Alignment.Center
        ) {
            PleaseWaitLoading()
        }
    }
}


@Composable
fun GalleryScreenContent(
    isLoading: Boolean,
    onUploadClick: (List<Uri>) -> Unit,
    onClose: () -> Unit,
    maxSelectable: Int  // New parameter: maximum images that can be selected
) {
    val context = LocalContext.current
    var albums by remember { mutableStateOf<List<Album>>(emptyList()) }
    var selectedAlbum by remember { mutableStateOf("All") }
    var galleryImages by remember { mutableStateOf<List<Uri>>(emptyList()) }
    val selectedImages = remember { mutableStateListOf<Uri>() }

    // Load gallery images and albums.
    LaunchedEffect(Unit) {
        val allImages = getGalleryImages(context)
        albums = listOf(Album("All", allImages.size)) + getAlbums(context)
        galleryImages = getGalleryImages(context, selectedAlbum)
    }

    LaunchedEffect(selectedAlbum) {
        galleryImages = getGalleryImages(context, selectedAlbum)
    }

    if (galleryImages.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No images. Check for permission")
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onClose) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }

                LazyRow(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(albums) { album ->
                        AlbumTabItem(
                            modifier = Modifier.padding(4.dp),
                            isSelected = album.name == selectedAlbum,
                            album = album,
                            onClick = {
                                selectedAlbum = album.name
                                // Reset selected images when album changes.
                                selectedImages.clear()
                            }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 100.dp),
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(4.dp)
            ) {
                items(galleryImages) { uri ->
                    val isSelected = selectedImages.contains(uri)
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clickable {
                                if (isSelected) {
                                    selectedImages.remove(uri)
                                } else if (selectedImages.size < maxSelectable) {
                                    selectedImages.add(uri)
                                }
                            }
                    ) {
                        AsyncImage(
                            model = uri,
                            contentDescription = "Gallery image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f),
                            contentScale = ContentScale.Crop
                        )
                        GalleryRadioButton(
                            modifier = Modifier
                                .padding(4.dp)
                                .align(Alignment.TopEnd),
                            isSelected = isSelected,
                            color = MaterialTheme.colorScheme.tertiary,
                            backgroundColor = if (isSelected) Color.Black.copy(0.5f) else Color.Transparent
                        )
                    }
                }
            }
            Row(
                modifier = Modifier.padding(top = 8.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(Modifier.weight(1f))
                Button(
                    onClick = { onUploadClick(selectedImages) },
                    shape = ButtonShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    )
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            "Upload ${selectedImages.size}/$maxSelectable",
                            fontWeight = FontWeight.W500
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun CameraScreenContent(
    isLoading: Boolean,
    onUploadClick: (Uri) -> Unit
) {
    val context = LocalContext.current
    var capturedImage by remember { mutableStateOf<Bitmap?>(null) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val scope = rememberCoroutineScope()

    // Create the launcher using the full-resolution TakePicture contract.
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success && imageUri != null) {
            // Load the full-resolution bitmap from the saved file URI using BitmapFactory.
            capturedImage = loadBitmapUsingFactory(context, imageUri!!)
        } else {
            capturedImage = null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (capturedImage != null) {
            Image(
                bitmap = capturedImage!!.asImageBitmap(),
                contentDescription = "Captured image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(0.75f),
                contentScale = ContentScale.Crop
            )
        } else {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(R.drawable.im_upload_image),
                    contentDescription = "No image captured",
                )
                Text(
                    "Capture an image to continue..",
                    fontWeight = FontWeight.W500,
                    fontStyle = FontStyle.Italic,
                    fontSize = 15.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                modifier = Modifier.weight(0.65f),
                onClick = {
                    // Create a file in the external pictures directory.
                    val imageFile = File(
                        context.getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                        "captured_image_${System.currentTimeMillis()}.jpg"
                    )
                    imageUri = FileProvider.getUriForFile(
                        context,
                        "${context.packageName}.fileprovider",
                        imageFile
                    )
                    // Launch the camera to take a full-resolution picture.
                    imageUri?.let { cameraLauncher.launch(it) }
                },
                shape = ButtonShape
            ) {
                Text("Capture Image")
            }

            Spacer(Modifier.size(8.dp))

            Button(
                modifier = Modifier.weight(0.35f),
                onClick = {
                    imageUri?.let {
                        onUploadClick(imageUri!!)
                    }
                },
                shape = ButtonShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                enabled = capturedImage != null
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = Color.White,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        "Upload",
                        fontWeight = FontWeight.W500
                    )
                }
            }
        }
    }
}

@Composable
fun VideoScreenContent(
    isLoading: Boolean,
    onUploadClick: (Uri) -> Unit
) {
    val context = LocalContext.current
    var capturedVideoUri by remember { mutableStateOf<Uri?>(null) }
    var capturedVideoFile by remember { mutableStateOf<File?>(null) }
    var videoThumbnail by remember { mutableStateOf<Bitmap?>(null) }

    // Launcher for video capture with a 10-second limit.
    val videoLauncher = rememberLauncherForActivityResult(
        contract = CaptureVideoWithLimit()
    ) { success: Boolean ->
        Log.e("VideoCapture", "Success: $success")
        if (!success) {
            capturedVideoUri = null
            capturedVideoFile = null
            videoThumbnail = null
        } else {
            // Trigger a media scan so the captured video is indexed.
            capturedVideoFile?.let { file ->
                MediaScannerConnection.scanFile(
                    context,
                    arrayOf(file.absolutePath),
                    null
                ) { _, uri ->
                    Log.e("VideoCapture", "Scanned: $uri")
                }
            }
            // Once a video is captured, generate a thumbnail.
            capturedVideoUri?.let { uri ->
                videoThumbnail = getVideoThumbnail(context, uri)
            }
        }
    }

    // Launcher for selecting a video from the gallery.
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            capturedVideoUri = uri
            // Generate a thumbnail for the selected video.
            videoThumbnail = getVideoThumbnail(context, uri)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // If a video is selected, show its thumbnail preview.
        if (capturedVideoUri != null && videoThumbnail != null) {
            // Clicking the thumbnail triggers the video trimmer.
            Image(
                bitmap = videoThumbnail!!.asImageBitmap(),
                contentDescription = "Video preview",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {

                    },
                contentScale = ContentScale.Crop
            )
        } else {
            // Otherwise, show a placeholder.
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    painter = painterResource(R.drawable.im_upload_video),
                    contentDescription = "No video captured"
                )
                Text(
                    "Capture a video to continue..",
                    fontWeight = FontWeight.W500,
                    fontStyle = FontStyle.Italic,
                    fontSize = 15.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Button to capture a video using the camera.
        OutlinedButton(
            onClick = {
                // Create a file reference in the external Movies directory.
                val videoFile = File(
                    context.getExternalFilesDir(Environment.DIRECTORY_MOVIES),
                    "captured_video_${System.currentTimeMillis()}.mp4"
                )
                capturedVideoFile = videoFile
                capturedVideoUri = FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.fileprovider",
                    videoFile
                )
                capturedVideoUri?.let { videoLauncher.launch(it) }
            },
            shape = ButtonShape
        ) {
            Text("Capture Video (max 10 sec)")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Button to open the gallery to select a video.
        Button(
            onClick = { galleryLauncher.launch("video/*") },
            shape = ButtonShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onTertiary
            )
        ) {
            Text("Select Video from Gallery")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Button to upload the captured/selected video.
        Button(
            onClick = {
                capturedVideoUri?.let { uri ->
                    onUploadClick(uri)
                }
            },
            shape = ButtonShape,
            enabled = capturedVideoUri != null && !isLoading,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            )
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color.Black,
                    strokeWidth = 2.dp
                )
            } else {
                Text("Upload Video")
            }
        }
    }
}

// Helper function to extract a thumbnail bitmap from a video URI.
fun getVideoThumbnail(context: Context, videoUri: Uri): Bitmap? {
    return try {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, videoUri)
        val bitmap = retriever.getFrameAtTime(0, MediaMetadataRetriever.OPTION_CLOSEST_SYNC)
        retriever.release()
        bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}


// --- Helper functions to query MediaStore for gallery images and albums ---
fun getGalleryImages(context: Context, album: String = "All"): List<Uri> {
    val imageUris = mutableListOf<Uri>()
    val projection =
        arrayOf(MediaStore.Images.Media._ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
    val selection: String?
    val selectionArgs: Array<String>?
    if (album == "All") {
        selection = null
        selectionArgs = null
    } else {
        selection = "${MediaStore.Images.Media.BUCKET_DISPLAY_NAME} = ?"
        selectionArgs = arrayOf(album)
    }
    val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"
    val query = context.contentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        projection,
        selection,
        selectionArgs,
        sortOrder
    )
    query?.use { cursor ->
        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
        while (cursor.moveToNext()) {
            val id = cursor.getLong(idColumn)
            val contentUri = ContentUris.withAppendedId(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                id
            )
            imageUris.add(contentUri)
        }
    }
    return imageUris
}

fun getAlbums(context: Context): List<Album> {
    val albumMap = mutableMapOf<String, Int>()
    val projection = arrayOf(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
    val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"
    val query = context.contentResolver.query(
        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
        projection,
        null,
        null,
        sortOrder
    )
    query?.use { cursor ->
        val bucketColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        while (cursor.moveToNext()) {
            val albumName = cursor.getString(bucketColumn) ?: "Unknown"
            albumMap[albumName] = albumMap.getOrDefault(albumName, 0) + 1
        }
    }
    return albumMap.map { Album(it.key, it.value) }
}

// --- Upload functions using OkHttp ---
// Upload multiple gallery images.
suspend fun uploadImages(context: Context, imageUris: List<Uri>): List<String>? {
    return withContext(Dispatchers.IO) {
        try {
            val client = OkHttpClient()
            val multipartBuilder = MultipartBody.Builder().setType(MultipartBody.FORM)
            imageUris.forEach { uri ->
                val ext = "jpg"
                context.contentResolver.openInputStream(uri)?.use { inputStream ->
                    val bytes = inputStream.readBytes()
                    val mediaType = "image/$ext".toMediaTypeOrNull()
                    val requestBody = bytes.toRequestBody(mediaType)
                    multipartBuilder.addFormDataPart(
                        "images",
                        "image_${System.currentTimeMillis()}.$ext",
                        requestBody
                    )
                }
            }
            val requestBody = multipartBuilder.build()
            val request = Request.Builder()
                .url("https://yourserver.com/upload-images")
                .post(requestBody)
                .build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext null
                val responseBody = response.body?.string()
                responseBody?.let { body ->
                    val json = JSONObject(body)
                    val imagesJsonArray = json.getJSONArray("images")
                    val imageUrls = mutableListOf<String>()
                    for (i in 0 until imagesJsonArray.length()) {
                        imageUrls.add(imagesJsonArray.getString(i))
                    }
                    return@withContext imageUrls
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext null
    }
}

// Upload a camera-captured image.
suspend fun uploadCameraImage(context: Context, bitmap: Bitmap): List<String>? {
    return withContext(Dispatchers.IO) {
        try {
            val client = OkHttpClient()
            val multipartBuilder = MultipartBody.Builder().setType(MultipartBody.FORM)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val bytes = stream.toByteArray()
            val mediaType = "image/jpeg".toMediaTypeOrNull()
            val requestBody = bytes.toRequestBody(mediaType)
            multipartBuilder.addFormDataPart(
                "images",
                "camera_${System.currentTimeMillis()}.jpg",
                requestBody
            )
            val requestBodyBuilt = multipartBuilder.build()
            val request = Request.Builder()
                .url("https://yourserver.com/upload-images")
                .post(requestBodyBuilt)
                .build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext null
                val responseBody = response.body?.string()
                responseBody?.let { body ->
                    val json = JSONObject(body)
                    val imagesJsonArray = json.getJSONArray("images")
                    val imageUrls = mutableListOf<String>()
                    for (i in 0 until imagesJsonArray.length()) {
                        imageUrls.add(imagesJsonArray.getString(i))
                    }
                    return@withContext imageUrls
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext null
    }
}

// Upload a captured video.
suspend fun uploadVideo(context: Context, videoUri: Uri): List<String>? {
    return withContext(Dispatchers.IO) {
        try {
            val client = OkHttpClient()
            val multipartBuilder = MultipartBody.Builder().setType(MultipartBody.FORM)
            val ext = "mp4"
            context.contentResolver.openInputStream(videoUri)?.use { inputStream ->
                val bytes = inputStream.readBytes()
                val mediaType = "video/$ext".toMediaTypeOrNull()
                val requestBody = bytes.toRequestBody(mediaType)
                multipartBuilder.addFormDataPart(
                    "video",
                    "video_${System.currentTimeMillis()}.$ext",
                    requestBody
                )
            }
            val requestBody = multipartBuilder.build()
            // Replace with your video upload endpoint if different.
            val request = Request.Builder()
                .url("https://yourserver.com/upload-images")
                .post(requestBody)
                .build()
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) return@withContext null
                val responseBody = response.body?.string()
                responseBody?.let { body ->
                    val json = JSONObject(body)
                    val videosJsonArray = json.getJSONArray("images")
                    val videoUrls = mutableListOf<String>()
                    for (i in 0 until videosJsonArray.length()) {
                        videoUrls.add(videosJsonArray.getString(i))
                    }
                    return@withContext videoUrls
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return@withContext null
    }
}

@Composable
fun GalleryRadioButton(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    color: Color = MaterialTheme.colorScheme.primary,
    backgroundColor: Color = Color.Transparent
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .padding(1.5.dp)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .border(
                    width = if (isSelected) 6.dp else 2.dp,
                    color = if (isSelected) color else
                        MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.2f),
                    shape = CircleShape
                )
        )
    }
}

@Composable
fun AlbumTabItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    color: Color = MaterialTheme.colorScheme.secondaryContainer,
    album: Album,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .clickable { onClick() }
            .background(
                if (isSelected) color
                else Color.Transparent
            )
            .padding(vertical = 4.dp, horizontal = 10.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "${album.name} (${album.count})",
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            fontSize = if (isSelected) 16.sp else 14.sp,
        )
    }
}

@Composable
fun BottomBarItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean,
    color: Color,
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable { onClick() }
            .background(
                if (isSelected) color
                else MaterialTheme.colorScheme.primaryContainer
            )
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onPrimaryContainer,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}


fun loadBitmapUsingFactory(context: Context, uri: Uri): Bitmap? {
    return try {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            BitmapFactory.decodeStream(inputStream)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * Converts a given [uri] into a [File] stored in the cache directory.
 *
 * @param context The context to access the content resolver and cache directory.
 * @param uri The Uri to be converted.
 * @return A [File] corresponding to the content of the Uri, or null if the conversion fails.
 */
fun uriToFile(context: Context, uri: Uri): File? {
    val fileName = getFileName(context, uri) ?: return null
    val file = File(context.cacheDir, fileName)
    return try {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        file
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

/**
 * Retrieves the display name of the file associated with the given [uri].
 *
 * @param context The context to access the content resolver.
 * @param uri The Uri for which the file name is needed.
 * @return The file name as a String, or null if not found.
 */
fun getFileName(context: Context, uri: Uri): String? {
    var fileName: String? = null
    if (uri.scheme == "content") {
        val cursor: Cursor? = context.contentResolver.query(uri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val columnIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (columnIndex != -1) {
                    fileName = it.getString(columnIndex)
                }
            }
        }
    }
    if (fileName == null) {
        fileName = uri.path
        val cut = fileName?.lastIndexOf('/') ?: -1
        if (cut != -1) {
            fileName = fileName?.substring(cut + 1)
        }
    }
    return fileName
}


//@OptIn(UnstableApi::class)
//@Composable
//fun TrimVideoScreen(
//    videoUri: Uri,
//    onTrimComplete: (trimmedVideoUri: Uri) -> Unit,
//    onCancel: () -> Unit
//) {
//    val context = LocalContext.current
//    var videoDuration by remember { mutableStateOf(0L) }
//    // allowedDuration: the maximum portion to trim (up to 10 sec).
//    var allowedDuration by remember { mutableStateOf(10000L) }
//
//    // Retrieve actual duration once the videoUri is available.
//    LaunchedEffect(videoUri) {
//        val retriever = MediaMetadataRetriever()
//        try {
//            retriever.setDataSource(context, videoUri)
//            val durationStr = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
//            val duration = durationStr?.toLongOrNull() ?: 10000L
//            videoDuration = duration
//            allowedDuration = min(duration, 10000L)
//        } catch (e: Exception) {
//            allowedDuration = 10000L
//        } finally {
//            retriever.release()
//        }
//    }
//
//    // Wait until we have a valid duration (even if it's fallback).
//    if (videoDuration == 0L) {
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            CircularProgressIndicator()
//        }
//        return
//    }
//
//    // Slider positions in milliseconds.
//    var startTime by remember { mutableStateOf(0f) }
//    var endTime by remember { mutableStateOf(allowedDuration.toFloat()) }
//
//    // Generate a thumbnail for preview.
//    val thumbnail by remember(videoUri) { mutableStateOf(getVideoThumbnail(context, videoUri)) }
//
//    var isTrimming by remember { mutableStateOf(false) }
//    var errorMessage by remember { mutableStateOf("") }
//    val scope = rememberCoroutineScope()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        Text("Trim Video", style = MaterialTheme.typography.titleLarge)
//        Spacer(modifier = Modifier.height(16.dp))
//        // Preview thumbnail or placeholder.
//        if (thumbnail != null) {
//            Image(
//                bitmap = thumbnail!!.asImageBitmap(),
//                contentDescription = "Video Preview",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(200.dp)
//                    .clip(RoundedCornerShape(8.dp)),
//                contentScale = ContentScale.Crop
//            )
//        } else {
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(200.dp)
//                    .background(Color.Gray),
//                contentAlignment = Alignment.Center
//            ) {
//                Text("No preview available", color = Color.White)
//            }
//        }
//        Spacer(modifier = Modifier.height(16.dp))
//        Text("Start: ${startTime.toInt()} ms", fontSize = 14.sp, textAlign = TextAlign.Center)
//        Slider(
//            value = startTime,
//            onValueChange = { newValue -> startTime = newValue.coerceIn(0f, endTime) },
//            valueRange = 0f..allowedDuration.toFloat(),
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        Text("End: ${endTime.toInt()} ms", fontSize = 14.sp, textAlign = TextAlign.Center)
//        Slider(
//            value = endTime,
//            onValueChange = { newValue -> endTime = newValue.coerceIn(startTime, allowedDuration.toFloat()) },
//            valueRange = 0f..allowedDuration.toFloat(),
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        if (errorMessage.isNotEmpty()) {
//            Text(text = errorMessage, color = Color.Red)
//        }
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceEvenly
//        ) {
//            OutlinedButton(onClick = onCancel) {
//                Text("Cancel")
//            }
//            Button(onClick = {
//                scope.launch {
//                    try {
//                        // Create a MediaItem with clipping configuration.
//                        val inputMediaItem = MediaItem.Builder()
//                            .setUri(videoUri)
//                            .setClippingConfiguration(
//                                MediaItem.ClippingConfiguration.Builder()
//                                    .setStartPositionMs(0)
//                                    .setEndPositionMs(10000L)
//                                    .build()
//                            )
//                            .build()
//
//                        // Optionally, create an EditedMediaItem if you want to remove audio or add effects.
//                        val editedMediaItem = EditedMediaItem.Builder(inputMediaItem)
//                            .setRemoveAudio(false) // or true if you want to strip audio
//                            // .setEffects(...)
//                            .build()
//
//                        val outputFile = File(context.cacheDir, "trimmed_video_${System.currentTimeMillis()}.mp4")
//
//                        // Build the transformer.
//                        // Make sure you have the Media3 Transformer dependency in your Gradle file:
//                        // implementation "androidx.media3:media3-transformer:<version>"
//                        val transformer = Transformer.Builder(context)
//                            // You can set other transformer options if needed.
//                            .build()
//
//                        // Start the transformation.
//                        // The second parameter is a String for the output file path.
//                        transformer.start(editedMediaItem, outputFile.absolutePath)
//
//                        // If no exception occurs, transformation was successful.
//                        Log.e("video trimmed", "video trimmed successfully")
//                        onTrimComplete(Uri.fromFile(outputFile))
//                    } catch (e: Exception) {
////                        onError(e)
//                        Log.e("video trimmed", "video trim error, ${e.message}")
//                    }
//                }
//            }) {
//                Text(text = "Trim Video")
//            }
//        }
//    }
//}