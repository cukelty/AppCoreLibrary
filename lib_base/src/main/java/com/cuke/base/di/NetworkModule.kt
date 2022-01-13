package com.cuke.base.di

import com.cuke.base.api.ApiConstant
import com.cuke.base.api.RetrofitClient
import com.cuke.base.http.service.LoginService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideLoginService(): LoginService {
        return RetrofitClient().create(ApiConstant.HOST,LoginService::class.java)
    }


}