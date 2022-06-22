package com.example.digikalasample.ui

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.digikalasample.databinding.ActivityMainBinding
import com.example.digikalasample.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val productViewModel: ProductViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.show()
        sharedPreferences = getSharedPreferences("myShare", Context.MODE_PRIVATE)
        if (sharedPreferences.getInt("CustomerId", 0) != 0) {
            productViewModel.mCustomerId = sharedPreferences.getInt("CustomerId", 0)
            Toast.makeText(applicationContext,  productViewModel.mCustomerId.toString(), Toast.LENGTH_SHORT).show()
        }
        if (!sharedPreferences.getStringSet("shoppingCartSet", emptySet())
                .isNullOrEmpty() && !flagIsDataSetFromShared
        ) {
            flagIsDataSetFromShared = true
            val shoppingCartSet =
                sharedPreferences.getStringSet("shoppingCartSet", emptySet())
            val shoppingCartCountSet =
                sharedPreferences.getStringSet("shoppingCartCountSet", emptySet())
            for ((index, element) in shoppingCartSet?.withIndex()!!) {
                lifecycleScope.launch {
                    shoppingCartCountSet?.elementAt(index)?.toInt()?.let {
                        productViewModel.addToShoppingCard(
                            productViewModel.getProductById(element.toInt()),
                            it
                        )
                    }
                }
            }
        }

    }

}




