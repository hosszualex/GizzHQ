package com.example.gizzhq.data

import com.example.gizzhq.domain.BaseResponse
import retrofit2.http.GET

class InstagramApiRestClient {
    companion object {
        const val INSTAGRAM_API_URL = "https://graph.instagram.com/v18.0/"
        const val INSTAGRAM_AUTH_URL = "https://api.instagram.com/"

        const val ACCESS_TOKEN =
            "IGQVJVQnhZAX1lPUG1IU3VNTFNnWm1EUXBGMU4wT1ZALRmYyTVdDNGFqczR1YkU0b19fUzFqY05zVU9GZAXZA1d0cwdTduNG1VZAWRRdC1rSGU0YzdZAVzM1VnZAfeHlXUmh4OFBRcFRZAT2djazlSMGg4SV9XWAZDZD"
        const val GRANT_TYPE = "ig_refresh_token"
        const val POST_FIELD =
            "id,caption,media_type,media_url,permalink,thumbnail_url,username,timestamp,children"
        const val CHILDREN_FIELD =
            "id,media_type,media_url,permalink,thumbnail_url,username,timestamp"
        const val CLIENT_TOKEN = "6a94fd40307738ac03af3f056522ed64"
    }

    private val apiRestClient: InstagramApiRestInterface = Retrofit.createRetrofitInstance(
        INSTAGRAM_API_URL
    ).create(InstagramApiRestInterface::class.java)

    private val authRestClient: InstagramAuthRestInterface = Retrofit.createRetrofitInstance(
        INSTAGRAM_AUTH_URL
    ).create(InstagramAuthRestInterface::class.java)

    suspend fun getStuff(): BaseResponse {
        return apiRestClient.getAllAddressesCoroutines()
    }

    interface InstagramApiRestInterface {
        @GET("/api/v1/users/{external_user_id}/addresses") // TODO
        suspend fun getAllAddressesCoroutines(): BaseResponse
    }

    interface InstagramAuthRestInterface {

    }
}