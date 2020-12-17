package hu.bme.aut.csigaleves.feature.network

import com.google.gson.annotations.SerializedName

data class NutrientsQuery (
    @SerializedName("query") var query: String
)

data class FoodData (
    var foods: List<FoodsItem>?
)

data class FoodsItem (
    var food_name: String,
    var serving_qty: Int,
    var serving_unit: String,
    var serving_weight_grams: Int,
    var nf_calories: Float,
    var nf_total_fat: Float,
    var nf_saturated_fat: Float,
    var nf_cholesterol: Float,
    var nf_sodium: Float,
    var nf_total_carbohydrate: Float,
    var nf_dietary_fiber: Float,
    var nf_sugars: Float,
    var nf_protein: Float,
    var nf_potassium: Float,
    var nf_p: Float
)