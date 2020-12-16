package hu.bme.aut.csigaleves.feature.network

import retrofit2.Call
import retrofit2.http.*

interface NutritionixApi {
    @Headers( "Accept: application/json",
        "x-app-id: 282a67c2",
        "x-app-key: 0021565d8ef9095ab0226e27809c5035")
    @POST("/natural/nutrients")
    fun getFoodData(
        @Query("query") nameAndAmount: String?
    ): Call<FoodData>
}