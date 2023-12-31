package dev.mindscape.tastytales.retrofit

import dev.mindscape.tastytales.data.CategoryList
import dev.mindscape.tastytales.data.MealsByCategoryList
import dev.mindscape.tastytales.data.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal(): Call<MealList>

    @GET("lookup.php")
    fun getMealDetails(@Query("i") id:String) : Call<MealList>

    @GET("filter.php")
    fun getPopularItems(@Query("c") categoryName:String) : Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories() : Call<CategoryList>

    @GET("search.php")
    fun searchMeals(@Query("s") searchQuery: String) : Call<MealList>
}