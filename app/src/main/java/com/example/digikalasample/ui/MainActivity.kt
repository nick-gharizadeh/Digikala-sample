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
        }
        if (!sharedPreferences.getString("shoppingCartString", "")
                .isNullOrEmpty() && !flagIsDataSetFromShared
        ) {
            flagIsDataSetFromShared = true
            val shoppingCartList = sharedPreferences.getString("shoppingCartString", "")
                ?.let { convertStringToList(it) }
            val shoppingCartCountList = sharedPreferences.getString("shoppingCartCountString", "")
                ?.let { convertStringToList(it) }
            if (!shoppingCartList.isNullOrEmpty()) {
                val size = shoppingCartList.size.minus(2)
                for (index in 0..size) {
                    lifecycleScope.launch {
                        shoppingCartCountList?.get(index)?.let {
                            productViewModel.addToShoppingCard(
                                productViewModel.getProductById(shoppingCartList[index].toInt()),
                                it.toInt()
                            )
                        }

                    }

                }
            }

        }

    }


    private fun convertStringToList(string: String) = string.split(";")

}




