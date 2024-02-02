package com.example.temp2.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

class DeliveryRepository {
    private companion object{
        const val BASE_URL = "https://shift-backend.onrender.com"
        const val CONNECT_TIMEOUT = 10L
        const val WRITE_TIMEOUT = 10L
        const val READ_TIME = 10L
    }

    private val gson = GsonBuilder()
        .create()

    private val retrofit = Retrofit.Builder()
        .client(provideOkHttpClientWithProgress())
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private fun provideOkHttpClientWithProgress(): OkHttpClient =
        OkHttpClient().newBuilder()
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIME, TimeUnit.SECONDS)
            .build()

    private val deliveryApi by lazy{
        retrofit.create(DeliveryApi::class.java)
    }

    suspend fun getPoints() : PointResponse = deliveryApi.getPoint()
    suspend fun getTypes() : TypeResponse = deliveryApi.getTypes()
    suspend fun getCalc(query : CalcResponse) : CalcAns = deliveryApi.getCalc(query)
}