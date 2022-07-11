package com.example.digikalasample.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.digikalasample.databinding.ActivitySplashScreenBinding


@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        binding.imageView.alpha = 0f

        // 📌 set application theme
        val sharedPreferences = getSharedPreferences("myShare", Context.MODE_PRIVATE)
        if (sharedPreferences.getBoolean("themeIsDark", false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        } else {
            AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )
        }
        binding.imageView.animate().setDuration(2000).alpha(1f).withEndAction {
            if (checkConnectivity(applicationContext)) {
                goToMainActivity()
            } else {
                binding.animationView.visibility = View.GONE
                binding.group.visibility = View.VISIBLE
            }
        }

        binding.textView.setOnClickListener {
            if (checkConnectivity(applicationContext)) {
                goToMainActivity()
            }

        }
    }


    private fun goToMainActivity() {
        val myIntent = Intent(this, MainActivity::class.java)
        startActivity(myIntent)
        finish()
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }

    @SuppressLint("MissingPermission")
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


