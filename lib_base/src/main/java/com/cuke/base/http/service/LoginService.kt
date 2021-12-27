package com.cuke.base.http.service

import com.cuke.base.http.entity.ApiResponse
import com.cuke.base.http.entity.LoginData
import retrofit2.http.GET
import retrofit2.http.Query

interface LoginService {

    @GET("user/login")
    suspend fun login(
        @Query("name") name: String,
        @Query("password") password: String
    ): ApiResponse<LoginData>
}