package com.ah.gizzhq.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ah.gizzhq.R

@Composable
fun CreateNewsFeed(onNavigateToWebView: () -> Unit) {
    Box(
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth(),
        ) {
            items(2) { i ->
                NewsFeedItem(itemCount = i)
            }
        }

        FloatingActionButton(
            onClick = {
                onNavigateToWebView()
            },
            modifier =
                Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 20.dp, bottom = 10.dp),
            shape = CircleShape,
        ) {
            Icon(Icons.Filled.Add, "")
        }
    }
}

@Composable
private fun NewsFeedItem(itemCount: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier =
            Modifier
                .padding(vertical = 14.dp),
    ) {
        AsyncImage(
            model =
                if (itemCount % 2 == 0) {
                    "https://media.npr.org/assets/img/2023/06/20/kglw---petro-press-6_credit-jason-galea_wide-033c8daa6706e9646597158523af72587dc80e32-s1100-c50.jpg"
                } else {
                    "https://upload.wikimedia.org/wikipedia/commons/4/42/King_Gizzard_2019.jpg"
                },
            contentDescription = "King Gizz Image",
            contentScale = ContentScale.Crop,
            placeholder = painterResource(R.drawable.news_feed_placeholder),
            modifier =
                Modifier
                    .padding(horizontal = 10.dp)
                    .heightIn(0.dp, 252.dp)
                    .clip(RoundedCornerShape(10.dp)),
        )
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                )
                Text(
                    text = "Go to post",
                )
            }
        }
    }
}
