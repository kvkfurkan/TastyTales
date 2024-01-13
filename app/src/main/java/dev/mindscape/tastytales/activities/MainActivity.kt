package dev.mindscape.tastytales.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import dev.mindscape.tastytales.R
import dev.mindscape.tastytales.databinding.ActivityMainBinding
import dev.mindscape.tastytales.db.MealDatabase
import dev.mindscape.tastytales.viewModel.HomeViewModel
import dev.mindscape.tastytales.viewModel.HomeViewModelFactory

private lateinit var binding : ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val viewModel : HomeViewModel by lazy {
        val mealDatabase = MealDatabase.getInstance(this)
        val homeViewModelProviderFactory = HomeViewModelFactory(mealDatabase)
        ViewModelProvider(this,homeViewModelProviderFactory)[HomeViewModel::class.java]
    }
    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
        Thread.sleep(1500)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val bottomNav: BottomNavigationView = binding.btmNav
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.btmNav, navHostFragment.navController)

    }

}