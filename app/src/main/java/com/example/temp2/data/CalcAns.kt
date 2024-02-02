package com.example.temp2.data

data class CalcAns(
    val success : Boolean,
    val options : List<DeliveryOption>
)

data class DeliveryOption(
    val id : String,
    val price : Int,
    val days : Int,
    val name : String,
    val type : String
)