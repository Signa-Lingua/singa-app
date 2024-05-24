package com.singa.asl.ui.screen.profile_detail

import android.Manifest
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import com.singa.asl.R
import com.singa.asl.ui.components.InputForm
import com.singa.asl.ui.theme.Color1
import com.singa.asl.ui.theme.ColorBackgroundWhite

@Composable
fun ProfileDetailScreen() {
    ProfileDetailContent()
}

@Composable
fun ProfileDetailContent() {
    var imageUri by remember {
        mutableStateOf<Uri>(Uri.EMPTY)
    }
    val context = LocalContext.current

    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if(uri !== null){
            imageUri = uri
        }
    }


    val galleryPermission = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {
        if (it) {
            launcher.launch("image/*")
        } else {
            Toast.makeText(context, "Permission denied", Toast.LENGTH_SHORT).show()
        }
    }


    Box(Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = 100.dp,
                ),
            colors = CardDefaults.cardColors(
                containerColor = ColorBackgroundWhite,
            ),
            shape = RoundedCornerShape(
                topStart = 40.dp,
                topEnd = 40.dp,
            )
        ) {
            Column(
                Modifier
                    .padding(top = 140.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column(Modifier.padding(16.dp)) {
                    InputForm(
                        title = "Username",
                        icon = R.drawable.baseline_people_alt_24,
                        value = "",
                        onChange = {})
                    InputForm(
                        title = "Email",
                        icon = R.drawable.baseline_email_24,
                        value = "",
                        onChange = {})
                }
                Button(
                    onClick = {

                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color1,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(text = "Save", fontSize = 24.sp, color = Color.White)
                }
            }
        }
        Box(
            Modifier
                .fillMaxWidth()
                .offset(y = 30.dp),
            contentAlignment = Alignment.Center
        ) {
            imageUri.let { uri ->
                SubcomposeAsyncImage(
                    model = imageUri,
                    loading = {},
                    contentDescription = "profile",
                    modifier = Modifier
                        .size(180.dp)
                        .clip(RoundedCornerShape(20.dp)),
                    contentScale = ContentScale.FillBounds
                )
            }
        }
        Box(
            Modifier.offset(x = 180.dp, y = 92.dp)
        ) {
            IconButton(
                modifier = Modifier
                    .size(48.dp)
                    .offset(x = 76.dp, y = 76.dp),
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color1,
                    contentColor = Color.White
                ),
                onClick = {
                    galleryPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                )
            }
        }

    }
}