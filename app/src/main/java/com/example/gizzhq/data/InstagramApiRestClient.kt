package com.example.gizzhq.data

import com.example.gizzhq.domain.BaseResponse
import retrofit2.http.GET

class InstagramApiRestClient {

    private val restInterface: InstagramApiRestInterface = Retrofit.createRetrofitInstance("").create(
        InstagramApiRestInterface::class.java)
    suspend fun getStuff(): BaseResponse {
        return restInterface.getAllAddressesCoroutines()
    }
    interface InstagramApiRestInterface {
        @GET("/api/v1/users/{external_user_id}/addresses") // TODO
        suspend fun getAllAddressesCoroutines(): BaseResponse
    }
}