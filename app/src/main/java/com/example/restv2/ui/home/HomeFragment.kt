package com.example.restv2.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
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
    private lateinit var foodAdapter: FoodAdapter
    private var totalPrice = 0.0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val categories = listOf(
            "All", "Italian", "Mexican", "Desserts",
            "Japanese", "Fast Food", "Entrees", "Healthy"
        )

        binding.categoryRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.foodRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

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

        foodAdapter = FoodAdapter(foodItems) { position, increase ->
            val item = foodItems[position]
            if (increase) {
                item.quantity++
            } else if (item.quantity > 0) {
                item.quantity--
            }
            foodAdapter.notifyItemChanged(position)
            updateCartDisplay(foodItems)
        }

        binding.foodRecyclerView.adapter = foodAdapter

        val categoryAdapter = CategoryAdapter(categories) { selectedCategory ->
            val filteredItems = if (selectedCategory == "All") {
                foodItems
            } else {
                foodItems.filter { it.category == selectedCategory }
            }
            foodAdapter.updateItems(filteredItems)
            binding.titleTextView.text = if (selectedCategory == "All") {
                "Popular Menu (All)"
            } else {
                "Popular Menu ($selectedCategory)"
            }
            updateCartDisplay(foodItems)
        }

        binding.categoryRecyclerView.adapter = categoryAdapter

        // Initialize cart displays
        binding.cartLayout.visibility = View.GONE
        updateCartDisplay(foodItems)

        return root
    }

    private fun updateCartDisplay(foodItems: List<FoodItem>) {
        val selectedItems = foodItems.filter { it.quantity > 0 }

        if (selectedItems.isEmpty()) {
            binding.cartLayout.visibility = View.GONE
            return
        }

        binding.cartLayout.visibility = View.VISIBLE

        // Calculate total price
        totalPrice = selectedItems.sumOf { item ->
            item.price.replace("$", "").toDouble() * item.quantity
        }

        // Update total price with improved formatting
        binding.totalPriceTextView.text = String.format("$%.2f", totalPrice)

        // Create styled cart items text
        val cartItemsText = StringBuilder()
        selectedItems.forEach { item ->
            val itemTotal = item.price.replace("$", "").toDouble() * item.quantity
            cartItemsText.append(
                "• ${item.name}\n" +
                        "  ${item.quantity}x • ${item.price} each\n" +
                        "  Subtotal: $${String.format("%.2f", itemTotal)}\n\n"
            )
        }
        binding.cartItemsTextView.text = cartItemsText.toString()

        // Setup checkout button click listener
        binding.checkoutButton.setOnClickListener {
            // Implement checkout logic
            Toast.makeText(context, "Proceeding to checkout...", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}