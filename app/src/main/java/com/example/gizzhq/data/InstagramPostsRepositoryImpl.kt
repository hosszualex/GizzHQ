package com.example.gizzhq.data

import com.example.gizzhq.domain.MediaPost
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class InstagramPostsRepositoryImpl(private val restClient: InstagramApiRestClient) {
    suspend fun getStuff(): Result<MediaPost> {
        return withContext(Dispatchers.IO) {
            val response = restClient.getStuff()
            if (response.error != null) // TODO
                Result.failure<MediaPost>(Throwable("fakin error"))
            else
                Result.success(
                     MediaPost(1,"","") // TODO map the response to be a media post
                )
        }
    }

    suspend fun authorize() {

    }

}