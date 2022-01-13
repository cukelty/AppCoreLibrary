package com.cuke.base.api

import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.HashMap
import java.util.concurrent.TimeUnit

class RetrofitClient {
//    @Volatile
//    private var instance: RetrofitClient? = null
    private var mOkHttpClient: OkHttpClient? = null

    private var mRetrofits: HashMap<String, Retrofit> = HashMap()

    init {
        initOkHttpClient()
    }

    private fun initOkHttpClient() {
        if (mOkHttpClient == null) {
            synchronized(OkHttpClient::class.java) {
                if (mOkHttpClient == null) {
                    mOkHttpClient = OkHttpClient.Builder()
                        .retryOnConnectionFailure(true)
                        .readTimeout(5, TimeUnit.MINUTES)
                        .connectTimeout(5, TimeUnit.MINUTES)
                        .build()
                }
            }
        }
    }

    fun <T> create(baseUrl: String, mClass: Class<T>?): T {
        var retrofit = mRetrofits[baseUrl]
        if (retrofit == null) {
            retrofit = createRetrofit(baseUrl)
            mRetrofits[baseUrl] = retrofit
        }
        return retrofit.create(mClass)
    }

    private fun createRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(mOkHttpClient)
            //拓展添加RxJava的功能，导入的库：compile 'com.squareup.retrofit2:adapter-rxjava:2.0.2'
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            //使用Gson对Json进行解析，导入的库：compile 'com.squareup.retrofit2:converter-gson:2.0.2'
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}