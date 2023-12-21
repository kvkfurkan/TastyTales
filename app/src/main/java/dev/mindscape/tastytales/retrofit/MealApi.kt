package dev.mindscape.tastytales.retrofit

import dev.mindscape.tastytales.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>
}