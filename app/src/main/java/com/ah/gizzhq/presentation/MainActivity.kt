package com.ah.gizzhq.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
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
import com.ah.gizzhq.presentation.ui.ErrorDialog
import com.ah.gizzhq.presentation.ui.ProgressIndicator
import com.ah.gizzhq.presentation.ui.phoneRegister.PhoneRegisterRoute
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
                val uiState by viewModel.uiState.collectAsState()

                val navController = rememberNavController()
/*                LaunchedEffect(userAuth.isRegistered) {
                    val route =
                        if (userAuth.isRegistered) {
                            "profileScreen"
                        } else {
                            "registerScreen"
                        }

                    navController.navigate(route) { popUpTo(0) }
                }*/

                NavHost(navController = navController, startDestination = "accountCreation") {
                    buildAccountCreationGraph(
                        this,
                        navController = navController,
                        this@MainActivity,
                        viewModel::onEvent
                    )
                }

                // Alert Dialog Error
                uiState.error?.let { error ->
                    ErrorDialog(errorMessage = error.message.toString(), errorCode = error.cause?.message.toString()) {
                        viewModel.onEvent(MainUiEvent.OnErrorDismissed)
                    }
                }

                // Loading
                AnimatedVisibility(
                    visible = uiState.isLoading,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    ProgressIndicator()
                }
            }
        }
    }
}

private fun buildAccountCreationGraph(
    navGraphBuilder: NavGraphBuilder,
    navController: NavController,
    context: MainActivity,
    onEvent: (MainUiEvent) -> Unit
) {
    navGraphBuilder.navigation(route = "accountCreation", startDestination = "phoneNumberRegisterScreen") {
        composable("phoneNumberRegisterScreen") {
            PhoneRegisterRoute(onError = {
                onEvent(MainUiEvent.OnError(it))
            },
                onLoading = {
                    onEvent(MainUiEvent.OnLoading(it))
                })
        }
        composable("registerScreen") {
            RegisterRoute()
        }
        composable("profileScreen") {
            ProfileScreen()
        }
    }
}
