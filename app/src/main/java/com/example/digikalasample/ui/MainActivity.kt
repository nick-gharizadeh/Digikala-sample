package com.example.digikalasample.ui

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
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
        supportActionBar?.hide()
        binding.imageView.alpha = 0f
        binding.imageView.animate().setDuration(2000).alpha(1f).withEndAction {
            if (checkConnectivity(applicationContext)) {
                fadeSplashScreenComponents()
            } else {
                binding.animationView.visibility = View.GONE
                binding.group.visibility = View.VISIBLE
            }
        }

        binding.textView.setOnClickListener {
            if (checkConnectivity(applicationContext)) {
                productViewModel.callServices()
                fadeSplashScreenComponents()
            }

        }
    }


    private fun fadeSplashScreenComponents() {
        binding.imageView.visibility = View.GONE
        binding.group.visibility = View.GONE
        binding.animationView.visibility = View.GONE
        supportActionBar?.show()
        binding.constraint.setBackgroundColor(Color.TRANSPARENT)
        binding.fragmentContainerView.visibility = View.VISIBLE
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    private fun checkConnectivity(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        return if (activeNetwork?.isConnected != null) {
            activeNetwork.isConnected
        } else {
            false
        }
    }

}




