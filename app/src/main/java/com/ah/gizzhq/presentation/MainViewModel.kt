package com.ah.gizzhq.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ah.gizzhq.data.repositories.UserDataRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(
        userDataRepository: UserDataRepositoryImpl,
    ) : ViewModel() {
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
    }

data class UserAuthState(
    val isRegistered: Boolean = false,
    val isLoggedIn: Boolean = false,
)
