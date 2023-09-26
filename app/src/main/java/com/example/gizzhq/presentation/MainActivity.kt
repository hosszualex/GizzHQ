package com.example.gizzhq.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import coil.compose.AsyncImage
import com.example.gizzhq.R
import com.example.gizzhq.presentation.theme.GizzHQTheme

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        GizzHQTheme {
            CreateNewsFeed {

            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        intent?.data?.toString()?.let { uri ->
            if (uri.contains("instagram.redirect.uri/auth")) {
                // TODO
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GizzHQTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "news") {
                    createNewsFeedGraph(this, navController = navController, this@MainActivity)
                }
            }
        }
    }
}

private fun createNewsFeedGraph(
    navGraphBuilder: NavGraphBuilder,
    navController: NavController,
    context: MainActivity
) {
    navGraphBuilder.navigation(route = "news", startDestination = "newsFeedList") {

        composable("newsFeedList") {
            CreateNewsFeed {
                navController.navigate("webview")
            }
        }
        composable("webview") {
            LoadWebUrl(
                context = context,
                url = "https://api.instagram.com/oauth/authorize?client_id=1753711138408921&redirect_uri=instagram.redirect.uri/auth&scope=user_profile,user_media&response_type=code"
                //url = "https://github.com"
            )
        }
    }
}

@Composable
fun CreateNewsFeed(onNavigateToWebView: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            items(2) { i ->
                NewsFeedItem(itemCount = i)
            }
        }

        FloatingActionButton(
            onClick = {
                onNavigateToWebView()
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 20.dp, bottom = 10.dp),
            shape = CircleShape
        ) {
            Icon(Icons.Filled.Add, "")
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
            placeholder = painterResource(R.drawable.news_feed_placeholder),
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .heightIn(0.dp, 252.dp)
                .clip(RoundedCornerShape(10.dp))
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
private fun LoadWebUrl(context: Context, url: String) {
    AndroidView(factory = {
        WebView(context).apply {
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    })
}