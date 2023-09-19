package com.example.gizzhq.presentation

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import coil.compose.AsyncImage
import com.example.gizzhq.R
import com.example.gizzhq.presentation.theme.GizzHQTheme

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GizzHQTheme {
                CreateNewsFeed()
            }
        }
    }
}

@Composable
fun CreateNewsFeed() {
    LazyColumn(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        items(2) { i ->
            NewsFeedItem(itemCount = i)
        }
    }
}

@Composable
fun NewsFeedItem(itemCount: Int) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .padding(vertical = 14.dp)
    ) {
        AsyncImage(
            model = if (itemCount % 2 == 0)
                "https://media.npr.org/assets/img/2023/06/20/kglw---petro-press-6_credit-jason-galea_wide-033c8daa6706e9646597158523af72587dc80e32-s1100-c50.jpg"
            else
                "https://upload.wikimedia.org/wikipedia/commons/4/42/King_Gizzard_2019.jpg",
            contentDescription = "King Gizz Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .clip(RoundedCornerShape(10.dp)),
            placeholder = painterResource(R.drawable.news_feed_placeholder),
        )
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(onClick = {}) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null
                )
                Text(
                    text = "Go to post"
                )
            }
        }
    }
}

@Composable
fun NavigationComponent() {
    // Navigation Component
    val navController = rememberNavController()
    // A surface container using the 'background' color from the theme
    NavHost(navController = navController, startDestination = "news") {
        NewsFeedGraph(this)
    }
}

private fun NewsFeedGraph(navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.navigation(route = "news", startDestination = "newsFeedList") {
        composable("newsFeedList") {
            CreateNewsFeed()
        }
        composable("newsFeedDetails") {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GizzHQTheme {
        CreateNewsFeed()
    }
}