package com.example.digikalasample.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.digikalasample.R
import com.example.digikalasample.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()
        binding.imageView.alpha=0f
        binding.imageView.animate().setDuration(2000).alpha(1f).withEndAction{
            binding.imageView.visibility= View.GONE
            binding.animationView.visibility= View.GONE
            supportActionBar?.show()
            binding.constraint.setBackgroundColor(Color.TRANSPARENT)
            binding.fragmentContainerView.visibility= View.VISIBLE
            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
        }
        }
}
