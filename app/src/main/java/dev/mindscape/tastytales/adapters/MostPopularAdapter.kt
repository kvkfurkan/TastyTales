package dev.mindscape.tastytales.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.mindscape.tastytales.data.CategoryMeals
import dev.mindscape.tastytales.databinding.PopularItemsBinding

class MostPopularAdapter(): RecyclerView.Adapter<MostPopularAdapter.PopularMealVH>() {

    private var mealsList = ArrayList<CategoryMeals>()
    lateinit var onItemClick:((CategoryMeals) -> Unit)

    fun setMeals(mealsList: ArrayList<CategoryMeals>){
        this.mealsList = mealsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealVH {
        return PopularMealVH(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PopularMealVH, position: Int) {
        Glide.with(holder.itemView)
            .load(mealsList[position].strMealThumb)
            .into(holder.binding.imgPopularMealItem)

        holder.itemView.setOnClickListener {
            onItemClick.invoke(mealsList[position])
        }
    }

    override fun getItemCount(): Int {
        return mealsList.size
    }

    class PopularMealVH( val binding: PopularItemsBinding):RecyclerView.ViewHolder(binding.root)



}