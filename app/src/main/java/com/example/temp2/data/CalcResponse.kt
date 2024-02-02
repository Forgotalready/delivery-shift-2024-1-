package com.example.temp2.data

import com.google.gson.annotations.SerializedName

data class CalcResponse(
    @SerializedName("package") val packageType : PackageResponse,
    val senderPoint : PostResponse,
    val receiverPoint : PostResponse
)

data class PackageResponse(
    val length : Int,
    val width: Int,
    val weight: Int,
    val height: Int
)

data class PostResponse(
    val latitude : Float,
    val longitude : Float
)