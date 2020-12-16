package hu.bme.aut.csigaleves.feature.network

import android.os.Handler
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
                //val response = call.execute().body()!!

                val aa = call.execute()

                android.util.Log.i("AAAAAAAAAAAAAAAAAAAAAA2",aa.isSuccessful().toString())
                android.util.Log.i("AAAAAAAAAAAAAAAAAAAAAA3",aa.code().toString())
                android.util.Log.i("AAAAAAAAAAAAAAAAAAAAAA","1")
                val response = aa.body()!!


                handler.post { onSuccess(response) }

            } catch (e: Exception) {
                e.printStackTrace()
                handler.post { onError(e) }
            }
        }.start()
    }
    fun getFoodData(
        nameAndAmount: String?,
        onSuccess: KFunction1<@ParameterName(name = "foodData") FoodData, Unit>,
        onError: (Throwable) -> Unit
    ){
        val req = nutritionixApi.getFoodData(nameAndAmount)
        runCallOnBackgroundThread(req, onSuccess, onError)
    }


}
