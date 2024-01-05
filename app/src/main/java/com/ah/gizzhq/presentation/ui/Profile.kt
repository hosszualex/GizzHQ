package com.ah.gizzhq.presentation.ui

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ah.gizzhq.R
import com.ah.gizzhq.presentation.MainViewModel
import com.ah.gizzhq.presentation.theme.GizzHQTheme


@Preview(showBackground = true)
@Composable
fun ProfilePreview() {
    GizzHQTheme {
        ProfileScreen(MainViewModel())
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(viewModel: MainViewModel) {
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
            ImageAvatar {
                isSheetOpen = true
            }
            Text(text = "Change profile picture")
        }

        val sheetState = rememberModalBottomSheetState()

        viewModel.firebaseStorage()

        if (isSheetOpen) {
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
                    items(20) { item ->
                        ImageAvatar {
                            // todo: on click, select this as new avatar
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ImageAvatar(onAvatarClicked: (() -> Unit)? = null) {
    val imageUri = rememberSaveable { mutableStateOf("") }
    val painter = rememberAsyncImagePainter(
        imageUri.value.ifBlank { R.drawable.ic_profile }
    )
    Image(
        painter = painter, contentDescription = null, modifier = Modifier
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
        colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.secondary)
    )
}
