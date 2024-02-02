package com.example.temp2.data

data class TypeResponse(
    val success : Boolean,
    val packages : List<TypePackage>
)

data class TypePackage(
    val id : String,
    val name : String,
    val length : Int,
    val width : Int,
    val height : Int,
    val weight : Int
)