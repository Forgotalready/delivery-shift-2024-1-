package com.example.temp2.data

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface DeliveryApi {
    @GET("/delivery/points")
    suspend fun getPoint() : PointResponse

    @GET("/delivery/package/types")
    suspend fun getTypes() : TypeResponse

    @POST("/delivery/calc")
    suspend fun getCalc(@Body query : CalcResponse) : CalcAns
}