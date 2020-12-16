package hu.bme.aut.csigaleves.feature.network

data class FoodData (
    var weather: List<String>?,
    var base: String,
    var timezone: Int,
    var id: Int,
    var name: String,
    var cod: Int
    )