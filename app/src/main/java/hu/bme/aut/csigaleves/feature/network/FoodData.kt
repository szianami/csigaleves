package hu.bme.aut.csigaleves.feature.network

data class FoodData (
    var foods: List<FoodsItem>?
)

data class FoodsItem (
    var food_name: String,
    var nf_calories: Int
)