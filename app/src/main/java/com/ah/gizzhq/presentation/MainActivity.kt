package com.ah.gizzhq.presentation

import android.content.Context
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.ah.gizzhq.presentation.theme.GizzHQTheme
import com.ah.gizzhq.presentation.ui.CreateNewsFeed
import com.ah.gizzhq.presentation.ui.profile.ProfileScreen
import com.ah.gizzhq.presentation.ui.register.RegisterRoute
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var userAuth: UserAuthState by mutableStateOf(UserAuthState())
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userAuthState
                    .onEach {
                        userAuth = it
                    }
                    .collect()
            }
        }

        setContent {
            GizzHQTheme {
                val navController = rememberNavController()
                LaunchedEffect(userAuth.isRegistered) {
                    val route =
                        if (userAuth.isRegistered) {
                            "profileScreen"
                        } else {
                            "registerScreen"
                        }

                    navController.navigate(route) { popUpTo(0) }
                }

                // todo: add loading bar in the Main Activity
                // todo: add error popup in the Main Activity

                NavHost(navController = navController, startDestination = "accountCreation") {
                    buildAccountCreationGraph(
                        this,
                        navController = navController,
                        this@MainActivity,
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
                url = "https://api.instagram.com/oauth/authorize?client_id=1753711138408921&redirect_uri=instagram.redirect.uri/auth&scope=user_profile,user_media&response_type=code",
                // url = "https://github.com"
            )
        }
    }
}

@Composable
private fun LoadWebUrl(
    context: Context,
    url: String,
) {
    AndroidView(factory = {
        WebView(context).apply {
            webViewClient = WebViewClient()
            loadUrl(url)
        }
    })
}
