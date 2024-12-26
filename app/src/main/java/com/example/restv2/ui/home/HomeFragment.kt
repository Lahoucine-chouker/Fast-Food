package com.example.restv2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restv2.FoodAdapter
import com.example.restv2.FoodItem
import com.example.restv2.R
import com.example.restv2.databinding.FragmentHomeBinding
import com.example.restv2.CategoryAdapter

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // Declare the foodAdapter at the class level
    private lateinit var foodAdapter: FoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Categories list with "All" category
        val categories = listOf(
            "All", "Italian", "Mexican", "Desserts",
            "Japanese", "Fast Food", "Entrees", "Healthy"
        )

        // Set up category RecyclerView (Horizontal Layout)
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        // Set up food RecyclerView (Grid Layout)
        binding.foodRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        // Create food items for the fragment
        val foodItems = mutableListOf(
            FoodItem(R.drawable.burger_image, "$12.99", "Cheeseburger", "Fast Food", 0),
            FoodItem(R.drawable.images, "$18.99", "Honey-Apple Chicken", "Entrees", 0),
            FoodItem(R.drawable.gyro_image, "$14.99", "Best Gyros", "Fast Food", 0),
            FoodItem(R.drawable.fries_image, "$9.99", "French Fries", "Fast Food", 0),
            FoodItem(R.drawable.pizza_image, "$15.99", "Pepperoni Pizza", "Fast Food", 0),
            FoodItem(R.drawable.sushi_image, "$22.99", "Fresh Sushi Platter", "Japanese", 0),
            FoodItem(R.drawable.salad_image, "$10.99", "Greek Salad", "Healthy", 0),
            FoodItem(R.drawable.steak_image, "$25.99", "Grilled Ribeye Steak", "Entrees", 0),
            FoodItem(R.drawable.taco_image, "$8.99", "Spicy Beef Tacos", "Mexican", 0),
            FoodItem(R.drawable.ice_cream_image, "$5.99", "Vanilla Ice Cream", "Desserts", 0),
            FoodItem(R.drawable.pasta_image, "$14.49", "Spaghetti Carbonara", "Italian", 0),
            FoodItem(R.drawable.chicken_wings_image, "$16.99", "Buffalo Chicken Wings", "Fast Food", 0),
            FoodItem(R.drawable.burrito_image, "$12.49", "Beef Burrito", "Mexican", 0),
            FoodItem(R.drawable.donut_image, "$3.99", "Chocolate Donut", "Desserts", 0)
        )

        // Set up food adapter
        foodAdapter = FoodAdapter(foodItems) { position, increase ->
            val item = foodItems[position]
            if (increase) {
                item.quantity++
            } else if (item.quantity > 0) {
                item.quantity--
            }
            foodAdapter.notifyItemChanged(position) // Notify the adapter that an item has changed
        }

        // Set foodAdapter to RecyclerView
        binding.foodRecyclerView.adapter = foodAdapter

        // Category selection logic
        val categoryAdapter = CategoryAdapter(categories) { selectedCategory ->
            val filteredItems = if (selectedCategory == "All") {
                foodItems
            } else {
                foodItems.filter { it.category == selectedCategory }
            }
            foodAdapter.updateItems(filteredItems) // Update the food list
            binding.titleTextView.text = if (selectedCategory == "All") {
                "Popular Menu (All)"
            } else {
                "Popular Menu ($selectedCategory)"
            }
        }

        // Set category adapter
        binding.categoryRecyclerView.adapter = categoryAdapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
