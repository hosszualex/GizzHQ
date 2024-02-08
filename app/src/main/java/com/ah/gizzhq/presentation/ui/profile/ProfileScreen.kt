package com.ah.gizzhq.presentation.ui.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.ah.gizzhq.R
import com.ah.gizzhq.presentation.theme.GizzHQTheme
import com.ah.gizzhq.presentation.ui.ProfileViewModel


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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(8.dp)
    ) {
        var isSheetOpen by rememberSaveable {
            mutableStateOf(false)
        }

        val imageUri = rememberSaveable { mutableStateOf("") }
        val painter = rememberAsyncImagePainter(
            imageUri.value.ifBlank { R.drawable.ic_profile }
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageAvatar(R.drawable.ic_profile) {
                isSheetOpen = true
            }
            Text(text = "Change profile picture")
        }

        val sheetState = rememberModalBottomSheetState()

        //viewModel.firebaseStorage()

        if (isSheetOpen) {
            val profileAvatars = listOf(
                R.drawable.ic_profile,
                R.drawable.avatar_1,
                R.drawable.avatar_2,
                R.drawable.avatar_3,
                R.drawable.ic_add
            )
            ModalBottomSheet(
                sheetState = sheetState,
                onDismissRequest = {
                    isSheetOpen = false
                }) {
                LazyVerticalGrid( // todo: play around with these values to find out more how they work
                    columns = GridCells.Fixed(3),
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(profileAvatars.size) { item ->
                        ImageAvatar(avatar = profileAvatars[item]) {
                            // todo: on click, select this as new avatar
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
        colorFilter = if (avatar == R.drawable.ic_profile || avatar == R.drawable.ic_add) ColorFilter.tint(color = MaterialTheme.colorScheme.secondary) else null
    )
}
