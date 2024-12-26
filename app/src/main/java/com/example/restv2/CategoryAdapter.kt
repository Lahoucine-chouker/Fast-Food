package com.example.restv2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(
    private val categories: List<String>,
    private val onCategorySelected: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private var selectedPosition = -1 // Track the selected position

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category, position)
    }

    override fun getItemCount(): Int = categories.size

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val categoryTextView: TextView = itemView.findViewById(R.id.categoryTextView)

        fun bind(category: String, position: Int) {
            categoryTextView.text = category

            // Set background and text color based on whether the category is selected or not
            if (position == selectedPosition) {
                categoryTextView.setBackgroundResource(R.drawable.selected_category_background)
                categoryTextView.setTextColor(android.graphics.Color.WHITE) // Set text color to white
            } else {
                categoryTextView.setBackgroundResource(R.drawable.unselected_category_background)
                categoryTextView.setTextColor(android.graphics.Color.BLACK) // Set text color to black
            }

            // Handle click to select/deselect category
            itemView.setOnClickListener {
                if (selectedPosition == position) {
                    // Deselect if the item is already selected
                    selectedPosition = -1
                } else {
                    // Select the new category
                    selectedPosition = position
                }
                notifyDataSetChanged() // Notify that the data has changed
                onCategorySelected(category) // Inform the activity/fragment
            }
        }
    }
}
