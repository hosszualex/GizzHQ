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

    companion object {
        const val INSTAGRAM_API_URL = "https://graph.instagram.com/v18.0/"
        const val INSTAGRAM_AUTH_URL = "https://api.instagram.com/v18.0/"

        const val ACCESS_TOKEN = "IGQVJVQnhZAX1lPUG1IU3VNTFNnWm1EUXBGMU4wT1ZALRmYyTVdDNGFqczR1YkU0b19fUzFqY05zVU9GZAXZA1d0cwdTduNG1VZAWRRdC1rSGU0YzdZAVzM1VnZAfeHlXUmh4OFBRcFRZAT2djazlSMGg4SV9XWAZDZD"
        const val GRANT_TYPE = "ig_refresh_token"
        const val POST_FIELD = "id,caption,media_type,media_url,permalink,thumbnail_url,username,timestamp,children"
        const val CHILDREN_FIELD = "id,media_type,media_url,permalink,thumbnail_url,username,timestamp"
        const val CLIENT_TOKEN = "6a94fd40307738ac03af3f056522ed64"
    }
}