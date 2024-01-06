package dev.mindscape.tastytales.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dev.mindscape.tastytales.data.MealsByCategory
import dev.mindscape.tastytales.data.MealsByCategoryList
import dev.mindscape.tastytales.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CategoryMealsViewModel : ViewModel() {
    val mealsLiveData = MutableLiveData<List<MealsByCategory>>()
    fun getMealsByCategory(categoryName : String){
        RetrofitInstance.api.getPopularItems(categoryName).enqueue(object : Callback<MealsByCategoryList>{
            override fun onResponse(
                call: Call<MealsByCategoryList>,
                response: Response<MealsByCategoryList>
            ) {
                response.body()?.let {mealsList->
                    mealsLiveData.postValue(mealsList.meals)
                }
            }

            override fun onFailure(call: Call<MealsByCategoryList>, t: Throwable) {
                Log.e("CategoryMealsViewModel",t.message.toString())
            }

        })
    }

    fun oberserveMealsLiveData():LiveData<List<MealsByCategory>>{
        return mealsLiveData
    }
}