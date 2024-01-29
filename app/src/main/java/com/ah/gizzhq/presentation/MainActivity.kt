package com.ah.gizzhq.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.ah.gizzhq.presentation.theme.GizzHQTheme
import com.ah.gizzhq.presentation.ui.CreateNewsFeed
import com.ah.gizzhq.presentation.ui.ProfileScreen
import com.ah.gizzhq.presentation.ui.register.RegisterRoute
import com.ah.gizzhq.presentation.ui.register.RegisterScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        GizzHQTheme {
            ProfileScreen()
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
                NavHost(navController = navController, startDestination = "accountCreation") {
                    buildAccountCreationGraph(
                        this,
                        navController = navController,
                        this@MainActivity
                    )
                }
            }
        }
    }
}


private fun buildAccountCreationGraph(
    navGraphBuilder: NavGraphBuilder,
    navController: NavController,
    context: MainActivity,
) {
    navGraphBuilder.navigation(route = "accountCreation", startDestination = "registerScreen") {
        composable("registerScreen") {
            RegisterRoute()
        }
        composable("profileScreen") {
            ProfileScreen()
        }
        composable("newsFeedListScreen") {
            CreateNewsFeed {
                navController.navigate("webview") // should go to a webview in theory
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
private fun LoadWebUrl(context: Context, url: String) {
    AndroidView(factory = {
        WebView(context).apply {
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    })
}