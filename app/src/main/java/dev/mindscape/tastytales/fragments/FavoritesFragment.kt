package dev.mindscape.tastytales.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dev.mindscape.tastytales.activities.MainActivity
import dev.mindscape.tastytales.adapters.MealsAdapter
import dev.mindscape.tastytales.databinding.FragmentFavoritesBinding
import dev.mindscape.tastytales.viewModel.HomeViewModel

class FavoritesFragment : Fragment() {

    private lateinit var binding : FragmentFavoritesBinding
    private lateinit var viewModel : HomeViewModel
    private lateinit var favoritesAdapter : MealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareRecyclerView()
        observeFavorites()

        val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val deletedMeal = favoritesAdapter.differ.currentList[position]
                viewModel.deleteMeal(deletedMeal)

                Snackbar.make(requireView(),"Meal Deleted",Snackbar.LENGTH_LONG).setAction(
                    "Undo"
                ) {
                    viewModel.insertMeal(deletedMeal)
                }.show()
            }

        }

        ItemTouchHelper(itemTouchHelper).attachToRecyclerView(binding.recyclerFavorites)

    }

    private fun observeFavorites() {
        viewModel.observeFavoriteMealsLiveData().observe(viewLifecycleOwner) { meals ->
            favoritesAdapter.differ.submitList(meals)
        }
    }

    private fun prepareRecyclerView(){
        favoritesAdapter = MealsAdapter()
        binding.recyclerFavorites.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = favoritesAdapter
        }
    }


}