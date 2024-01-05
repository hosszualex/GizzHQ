package com.ah.gizzhq.presentation

import androidx.lifecycle.ViewModel
import com.ah.gizzhq.data.InstagramApiRestClient
import com.ah.gizzhq.data.InstagramPostsRepositoryImpl
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {

    private val _isBusy = MutableStateFlow(false)
    val isBusy = _isBusy.asStateFlow()

    private val _onError = MutableStateFlow("No Error")
    val onError = _onError.asStateFlow()

    private val _onGetAvatarImages = MutableStateFlow<List<String>>(listOf())
    val onGetAvatarImages = _onGetAvatarImages.asStateFlow()

    private val repositoryImpl: InstagramPostsRepositoryImpl = InstagramPostsRepositoryImpl(
        restClient = InstagramApiRestClient()
    )

    fun firebaseStorage() {
        val storage = Firebase.storage
        val storageReference = storage.reference
        storageReference.listAll()
            .addOnSuccessListener { items ->
                var asd = 0

            }
            .addOnFailureListener {
                var dsa = 0
                // Uh-oh, an error occurred!
            }
    }

}