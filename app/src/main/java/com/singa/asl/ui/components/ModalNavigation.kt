package com.singa.asl.ui.components

import android.Manifest
import android.content.Context
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import com.singa.asl.R
import com.singa.asl.ui.theme.Color1

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ModalNavigation(
    context: Context = LocalContext.current,
    navigateToRealtimeCamera: () -> Unit,
    navigateToConversation: () -> Unit
) {
    val storagePermissionState = rememberPermissionState(Manifest.permission.READ_EXTERNAL_STORAGE)
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            navigateToRealtimeCamera()
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    val readStoragePermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            navigateToRealtimeCamera()
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }

    var conversationDialog by remember { mutableStateOf(false) }
    var conversationTitle by remember { mutableStateOf("") }

    Column(
        Modifier
            .fillMaxHeight(0.3f)
            .fillMaxWidth()
            .padding(12.dp)
    ) {
        Button(
            onClick = {
                when {
                    cameraPermissionState.hasPermission -> {
                        navigateToRealtimeCamera()
                    }

                    cameraPermissionState.shouldShowRationale -> {
                        Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                    }

                    storagePermissionState.hasPermission -> {
                        navigateToRealtimeCamera()
                    }

                    storagePermissionState.shouldShowRationale -> {
                        Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
                    }

                    else -> {
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        readStoragePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }
                }
            },
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color1,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_video_camera_front_24),
                contentDescription = stringResource(id = R.string.upload_video),
                modifier = Modifier.size(36.dp),
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = stringResource(R.string.upload_video),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(
            onClick = {
                conversationDialog = true
            },
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = Color.White,
                contentColor = Color1,

                ),
            border = BorderStroke(2.dp, Color1),
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_people_alt_24),
                contentDescription = stringResource(id = R.string.start_a_conversation),
                modifier = Modifier.size(36.dp),
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.start_a_conversation),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
    }

    if(conversationDialog){
        PopupInputAlertDialog(
            title = "Create a conversation",
            value = conversationTitle,
            onValueChange = { conversationTitle = it },
            onDismissRequest = { conversationDialog = false },
            confirmButton = { conversationDialog = false }
        )
    }
}
