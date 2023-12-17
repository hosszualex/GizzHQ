package com.ah.gizzhq.presentation

import androidx.lifecycle.ViewModel
import com.ah.gizzhq.data.InstagramApiRestClient
import com.ah.gizzhq.data.InstagramPostsRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _isBusy = MutableStateFlow(false)
    val isBusy = _isBusy.asStateFlow()

    private val _onError = MutableStateFlow("No Error")
    val onError = _onError.asStateFlow()

    private val repositoryImpl: InstagramPostsRepositoryImpl = InstagramPostsRepositoryImpl(
        restClient = InstagramApiRestClient()
    )

}