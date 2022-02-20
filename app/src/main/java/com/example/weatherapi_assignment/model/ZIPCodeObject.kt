package com.example.weatherapi_assignment.model

data class ZIPCodeObject(
    val coord : Location,
    val name : String,
    val sys : SysInfo
)

data class Location(
    val lon : String,
    val lat : String
)

data class SysInfo(
    val country : String
)