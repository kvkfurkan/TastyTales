package dev.mindscape.tastytales.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import dev.mindscape.tastytales.activities.MainActivity
import dev.mindscape.tastytales.adapters.FavoriteMealsAdapter
import dev.mindscape.tastytales.databinding.FragmentFavoritesBinding
import dev.mindscape.tastytales.viewModel.HomeViewModel

class FavoritesFragment : Fragment() {

    private lateinit var binding : FragmentFavoritesBinding
    private lateinit var viewModel : HomeViewModel
    private lateinit var favoritesAdapter : FavoriteMealsAdapter
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
    }

    private fun observeFavorites() {
        viewModel.observeFavoriteMealsLiveData().observe(viewLifecycleOwner) { meals ->
            favoritesAdapter.differ.submitList(meals)
        }
    }

    private fun prepareRecyclerView(){
        favoritesAdapter = FavoriteMealsAdapter()
        binding.recylerFavorites.apply {
            layoutManager = GridLayoutManager(context,2,GridLayoutManager.VERTICAL,false)
            adapter = favoritesAdapter
        }
    }


}