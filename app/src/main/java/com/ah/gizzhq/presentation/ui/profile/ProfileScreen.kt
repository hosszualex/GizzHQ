package com.ah.gizzhq.presentation.ui.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ah.gizzhq.R
import com.ah.gizzhq.domain.utils.defaultLog
import com.ah.gizzhq.domain.utils.toBitmap
import com.ah.gizzhq.presentation.theme.GizzHQTheme
import com.ah.gizzhq.presentation.ui.ProfileViewModel
import kotlinx.coroutines.launch


@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    GizzHQTheme {
        ProfileScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: ProfileViewModel = hiltViewModel()) {
    var isSheetOpen by rememberSaveable {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val defaultImageAvatar =
        ContextCompat.getDrawable(context, R.drawable.ic_profile)?.toBitmap()!!.asImageBitmap()
    var bitmap by remember {
        mutableStateOf<ImageBitmap>(defaultImageAvatar)
    }

    val defaultProfileAvatars = remember {
        mutableStateListOf<Int>(
            R.drawable.ic_profile,
            R.drawable.avatar_1,
            R.drawable.avatar_2,
            R.drawable.avatar_3
        )
    }

    val importedProfileAvatars = remember {
        mutableStateListOf<Uri>()
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            importedProfileAvatars.add(it)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                bitmap = bitmap,
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .border(
                        BorderStroke(4.dp, MaterialTheme.colorScheme.secondary),
                        CircleShape
                    )
                    .padding(4.dp)
                    .clickable {
                        isSheetOpen = true
                    },
                contentScale = ContentScale.Crop,
                colorFilter = if (bitmap == defaultImageAvatar) // todo alex: find out why this changes color on first tap
                    ColorFilter.tint(color = MaterialTheme.colorScheme.secondary)
                else
                    null
            )

            Text(text = "Change profile picture")

            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp, end = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Display Name:", modifier = Modifier.width(110.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = "",
                    onValueChange = { },
                    enabled = true,
                    singleLine = true,
                )
            }

        }

        val bottomSheetState = rememberModalBottomSheetState()
        if (isSheetOpen) {
            ModalBottomSheet(
                sheetState = bottomSheetState,
                onDismissRequest = {
                    isSheetOpen = false
                }) {
                LazyVerticalGrid( // todo: play around with these values to find out more how they work
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(defaultProfileAvatars.size) { item ->
                        ImageAvatar(avatar = defaultProfileAvatars[item]) {
                            bitmap = ContextCompat.getDrawable(context, defaultProfileAvatars[item])
                                ?.toBitmap()!!.asImageBitmap()
                            isSheetOpen = false
                        }
                    }
                    items(importedProfileAvatars.size) { item ->
                        ImportedImageAvatar(avatar = importedProfileAvatars[item]) {
                            bitmap =
                                importedProfileAvatars[item].toBitmap(context)!!.asImageBitmap()
                            isSheetOpen = false
                        }
                    }
                    item {
                        ImageAvatar(avatar = R.drawable.ic_add) {
                            launcher.launch("image/*")
                            defaultLog("Clicked on add")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ImageAvatar(avatar: Int, onAvatarClicked: (() -> Unit)? = null) {
    Image(
        painter = painterResource(id = avatar),
        contentDescription = null, modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .border(
                BorderStroke(4.dp, MaterialTheme.colorScheme.secondary),
                CircleShape
            )
            .padding(4.dp)
            .clickable {
                onAvatarClicked?.invoke()
            },
        contentScale = ContentScale.Crop,
        colorFilter = if (avatar == R.drawable.ic_profile || avatar == R.drawable.ic_add) ColorFilter.tint(
            color = MaterialTheme.colorScheme.secondary
        ) else null
    )
}

@Composable
fun ImportedImageAvatar(avatar: Uri, onAvatarClicked: (() -> Unit)? = null) {
    Image(
        painter = rememberAsyncImagePainter(avatar),
        contentDescription = null, modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .border(
                BorderStroke(4.dp, MaterialTheme.colorScheme.secondary),
                CircleShape
            )
            .padding(4.dp)
            .clickable {
                onAvatarClicked?.invoke()
            },
        contentScale = ContentScale.Crop
    )
}