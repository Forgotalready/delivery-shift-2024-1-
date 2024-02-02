package com.example.temp2.data

data class PointResponse(
    val success : Boolean,
    val points : List<DeliveryPoints>
)

data class DeliveryPoints(
    val id : String,
    val name : String,
    val latitude : Float,
    val longitude : Float
)