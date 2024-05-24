package com.singa.asl.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.singa.asl.R
import com.singa.asl.ui.navigation.Screen
import com.singa.asl.ui.theme.Color1
import com.singa.core.utils.DateConverter
import java.util.Locale

@Composable
fun TopBar(
    currentRoute: String?,
    name: String,
    avatarUrl: String,
    navigateToProfile: () -> Unit,
    navigateBack: () -> Unit
) {
    when (currentRoute) {
        Screen.Home.route -> TopBarProfile(
            name = name,
            avatarUrl = avatarUrl,
            navigateToProfile,
        )

        else -> TopBarLeftIcon(
            navigateBack = navigateBack,
            // replace second parameter to uppercase
            route = currentRoute.toString()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarLeftIcon(
    route: String,
    navigateBack: () -> Unit
) {
    val colorPaint: Color =
        if (route == Screen.Login.route || route == "register") Color.Black else Color.White

    val listOfShowBackButton = listOf(
        Screen.Conversation.route,
        Screen.Login.route,
        Screen.Register.route,
        Screen.ProfileDetail.route,
        Screen.ChangePassword.route
    )

    val showBackButton = listOfShowBackButton.contains(route.lowercase())

    val modifiedText = route
        .replace("_", " ")
        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        .split(" ").joinToString(" ") {
            it.replaceFirstChar { char -> if (char.isLowerCase()) char.titlecase(Locale.getDefault()) else char.toString() }
        }



    CenterAlignedTopAppBar(
        modifier = Modifier.padding(horizontal = 16.dp),
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Color.Transparent
        ),
        title = {
            Text(
                text = modifiedText,
                fontWeight = FontWeight.Bold,
                color = colorPaint,
                fontSize = 28.sp
            )
        },
        navigationIcon = {
            if (showBackButton) {
                IconButton(
                    onClick = {
                        navigateBack()
                    }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "back",
                        tint = colorPaint
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarProfile(
    name: String,
    avatarUrl: String,
    navigateToProfile: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color1
        ),
        modifier = Modifier.padding(top = 16.dp),
        title = {
            Column {
                Text(
                    text = DateConverter(System.currentTimeMillis()),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color(0xFFD5F4FC)
                )
                Text(
                    text = "Welcome, $name",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        },
        actions = {
            Surface(
                color = Color(0xFFFFB9A7),
                modifier = Modifier
                    .width(100.dp)
                    .height(60.dp)
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(20),
                onClick = {
                    navigateToProfile()
                }
            ) {
                SubcomposeAsyncImage(
                    model = avatarUrl,
                    contentDescription = "profile",
                    modifier = Modifier
                        .size(30.dp)
                        .padding(top = 10.dp)
                ) {
                    when (this.painter.state) {
                        is AsyncImagePainter.State.Loading -> {
                            Box(
                                modifier = Modifier
                                    .background(
                                        shimmerBrush(
                                            targetValue = 1300f,
                                            showShimmer = true
                                        )
                                    )
                                    .size(30.dp)
                                    .padding(top = 10.dp)
                            )
                        }

                        is AsyncImagePainter.State.Success -> {
                            SubcomposeAsyncImageContent()
                        }

                        is AsyncImagePainter.State.Error -> {
                            Image(
                                painter = painterResource(id = R.drawable.boy_1),
                                contentDescription = "profile",
                            )
                        }

                        is AsyncImagePainter.State.Empty -> {
                            Image(
                                painter = painterResource(id = R.drawable.boy_1),
                                contentDescription = "profile",
                            )
                        }
                    }
                }
            }
        }
    )
}