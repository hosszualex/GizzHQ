package com.ah.gizzhq.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ah.gizzhq.data.repositories.UserDataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    userDataRepository: UserDataRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    val userAuthState: StateFlow<UserAuthState> =
        userDataRepository.userData
            .map { userData ->
                UserAuthState(
                    isRegistered = userData.isRegistered,
                    isLoggedIn = userData.isLoggedIn,
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = UserAuthState(false, false),
            )

    fun onEvent(event: MainUiEvent) {
        when(event) {
            is MainUiEvent.OnError -> setError(event.exception)
            is MainUiEvent.OnErrorDismissed -> setError(null)
            is MainUiEvent.OnLoading -> updateLoadingState(event.visibility)
            else -> Unit
        }
    }

    private fun setError(throwable: Throwable?) {
        _uiState.update {
            it.copy(error = throwable)
        }
    }

    private fun updateLoadingState(visibility: Boolean) {
        _uiState.update {
            it.copy(isLoading = visibility)
        }
    }
}
data class MainUiState(
    val isLoading: Boolean = false,
    val error: Throwable? = null
)
sealed interface MainUiEvent {
    data class OnLoading(val visibility: Boolean) : MainUiEvent

    data class OnError(val exception: Throwable) : MainUiEvent
    data object OnErrorDismissed : MainUiEvent
}


data class UserAuthState(
    val isRegistered: Boolean = false,
    val isLoggedIn: Boolean = false,
)
