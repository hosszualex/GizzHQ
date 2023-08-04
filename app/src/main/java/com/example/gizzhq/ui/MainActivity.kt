package com.example.gizzhq.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.gizzhq.ui.theme.GizzHQTheme

class MainActivity : ComponentActivity() {

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
                val navController = rememberNavController()
                // A surface container using the 'background' color from the theme
                NavHost(navController = navController, startDestination = "news") {
                    NewsFeedGraph(this)
                }
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    Greeting("Android")
//                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}
private fun NewsFeedGraph(navGraphBuilder: NavGraphBuilder) {
    navGraphBuilder.navigation(route = "news", startDestination = "newsFeedList") {
        composable("newsFeedList") {

        }
        composable("newsFeedDetails") {

        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GizzHQTheme {
        Greeting("Android")
    }
}