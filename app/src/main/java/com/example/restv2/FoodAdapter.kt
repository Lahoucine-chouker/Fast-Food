package com.example.restv2


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodAdapter(
    private var foodItems: List<FoodItem>,
    private val onQuantityChanged: (position: Int, increase: Boolean) -> Unit
) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_food_card, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val foodItem = foodItems[position]

        holder.foodPrice.text = "$${foodItem.price}"
        holder.foodDescription.text = foodItem.name
        holder.foodImage.setImageResource(foodItem.image)
        holder.quantityText.text = foodItem.quantity.toString()

        holder.increaseQuantityButton.setOnClickListener {
            onQuantityChanged(position, true)
        }

        holder.decreaseQuantityButton.setOnClickListener {
            onQuantityChanged(position, false)
        }
    }
    override fun getItemCount(): Int {
        return foodItems.size
    }

    fun updateItems(newItems: List<FoodItem>) {
        this.foodItems = newItems
        notifyDataSetChanged() // Refresh entire list if needed
    }

    // ViewHolder class to hold the references to the views
    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val foodImage: ImageView = itemView.findViewById(R.id.foodImage)
        val foodPrice: TextView = itemView.findViewById(R.id.foodPrice)
        val foodDescription: TextView = itemView.findViewById(R.id.foodDescription)
        val quantityText: TextView = itemView.findViewById(R.id.quantityText)
        val increaseQuantityButton: ImageView = itemView.findViewById(R.id.increaseQuantityButton)
        val decreaseQuantityButton: ImageView = itemView.findViewById(R.id.decreaseQuantityButton)
    }


}
