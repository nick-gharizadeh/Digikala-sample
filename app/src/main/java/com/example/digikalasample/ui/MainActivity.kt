package com.example.digikalasample.ui

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.digikalasample.databinding.ActivityMainBinding
import com.example.digikalasample.viewmodel.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint


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
            productViewModel.mCustomerId?.let { it1 -> productViewModel.getCustomer(it1) }
        }
        if (!sharedPreferences.getString("shoppingCartString", "")
                .isNullOrEmpty() && !flagOnceDataSet
        ) {
            flagOnceDataSet = true
            val shoppingCartList = sharedPreferences.getString("shoppingCartString", "")
                ?.let { convertStringToList(it) }
            val shoppingCartCountList = sharedPreferences.getString("shoppingCartCountString", "")
                ?.let { convertStringToList(it) }
            if (!shoppingCartList.isNullOrEmpty()) {
                val size = shoppingCartList.size.minus(2)
                for (index in 0..size) {
                    shoppingCartCountList?.get(index)?.let {
                        productViewModel.getProductById(
                            shoppingCartList[index].toInt(),
                            it.toInt()
                        )
                    }

                }
            }
        }

    }


    private fun convertStringToList(string: String) = string.split(";")

}




