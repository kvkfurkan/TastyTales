package dev.mindscape.tastytales.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import dev.mindscape.tastytales.adapters.CategoryMealsAdapter
import dev.mindscape.tastytales.databinding.ActivityCategoryMealsBinding
import dev.mindscape.tastytales.fragments.HomeFragment
import dev.mindscape.tastytales.viewModel.CategoryMealsViewModel

class CategoryMealsActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCategoryMealsBinding
    private lateinit var categoryMealsViewModel : CategoryMealsViewModel
    private lateinit var categoryMealsAdapter : CategoryMealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoryMealsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prepareRecyclerView()

        categoryMealsViewModel = ViewModelProvider(this)[CategoryMealsViewModel::class.java]
        val mealName = intent.getStringExtra(HomeFragment.CATEGORY_NAME)!!
        categoryMealsViewModel.getMealsByCategory(mealName)

        categoryMealsViewModel.oberserveMealsLiveData().observe(this) { mealsList ->
            binding.txtCategoryName.text = mealName
            binding.txtCategoryCount.text = mealsList.size.toString()
            categoryMealsAdapter.setMealsList(mealsList)
        }

        onItemClick()
    }

    private fun prepareRecyclerView(){
        categoryMealsAdapter = CategoryMealsAdapter()
        binding.recylerMeals.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = categoryMealsAdapter
        }
    }

    private fun onItemClick(){
        categoryMealsAdapter.onItemClick = {meal->
            val intent = Intent(this, MealActivity::class.java)
            intent.putExtra(HomeFragment.MEAL_ID,meal.idMeal)
            intent.putExtra(HomeFragment.MEAL_NAME,meal.strMeal)
            intent.putExtra(HomeFragment.MEAL_THUMB,meal.strMealThumb)
            startActivity(intent)

        }
    }
}