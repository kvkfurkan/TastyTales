package dev.mindscape.tastytales.activities

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import dev.mindscape.tastytales.data.Meal
import dev.mindscape.tastytales.databinding.ActivityMealBinding
import dev.mindscape.tastytales.db.MealDatabase
import dev.mindscape.tastytales.fragments.HomeFragment
import dev.mindscape.tastytales.viewModel.MealViewModel
import dev.mindscape.tastytales.viewModel.MealViewModelFactory

class MealActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMealBinding
    private lateinit var mealId:String
    private lateinit var mealName:String
    private lateinit var mealThumb:String
    private lateinit var mealMvvm:MealViewModel
    private lateinit var youtubeLink:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)

        mealMvvm = ViewModelProvider(this, viewModelFactory)[MealViewModel::class.java]

        getMealInformation()

        setInformationInViews()

        mealMvvm.getMealDetail(mealId)

        observerMealDetails()

        onYoutubeImageClick()

        onFavoriteClick()
    }

   private fun getMealInformation(){
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

   private fun setInformationInViews(){
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.imgMealDetail)

        binding.collapsingToolbar.title = mealName
        binding.collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE)
        binding.collapsingToolbar.setExpandedTitleColor(Color.WHITE)
    }
    private var mealToSave : Meal? = null
    private fun observerMealDetails(){
        mealMvvm.observerMealDetailsLiveData().observe(this
        ) { value ->
            onResponseCase()
            mealToSave = value

            binding.txtCategory.text = "Category : ${value.strCategory}"
            binding.txtArea.text = "Area : ${value.strArea}"
            binding.txtInstructionSt.text = value.strInstructions

            youtubeLink = value.strYoutube.toString()
        }
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddFav.visibility = View.INVISIBLE
        binding.txtInstruction.visibility = View.INVISIBLE
        binding.txtCategory.visibility = View.INVISIBLE
        binding.txtArea.visibility = View.INVISIBLE
        binding.imgYoutube.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddFav.visibility = View.VISIBLE
        binding.txtInstruction.visibility = View.VISIBLE
        binding.txtCategory.visibility = View.VISIBLE
        binding.txtArea.visibility = View.VISIBLE
        binding.imgYoutube.visibility = View.VISIBLE
    }

    private fun onYoutubeImageClick(){
        binding.imgYoutube.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeLink))
            startActivity(intent)
        }
    }

    private fun onFavoriteClick(){
        binding.btnAddFav.setOnClickListener {
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this, "Meal Saved", Toast.LENGTH_SHORT).show()
            }
        }
    }
}