package hu.bme.aut.csigaleves.feature.network

import android.os.Handler
import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.reflect.KFunction1

object NetworkManager {
    private const val SERVICE_URL = "https://trackapi.nutritionix.com/v2/"
    private const val APP_ID = "282a67c2"
    private const val APP_KEY = "0021565d8ef9095ab0226e27809c5035"

    private val nutritionixApi: NutritionixApi

    init {

        val retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        nutritionixApi = retrofit.create(NutritionixApi::class.java)
    }

    private fun <T> runCallOnBackgroundThread(
        call: Call<T>,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val handler = Handler()
        Thread {
            try {
                val response = call.execute().body()!!
                //call.execute().isSuccessful()
                //call.execute().code()
                handler.post { onSuccess(response) }

            } catch (e: Exception) {
                e.printStackTrace()
                handler.post { onError(e) }
            }
        }.start()
    }
    fun getFoodData(
        query: NutrientsQuery,
        onSuccess: (FoodData) -> Unit,
        onError: (Throwable) -> Unit
    ){
        val req = nutritionixApi.getFoodData(query)
        runCallOnBackgroundThread(req, onSuccess, onError)
    }


}
