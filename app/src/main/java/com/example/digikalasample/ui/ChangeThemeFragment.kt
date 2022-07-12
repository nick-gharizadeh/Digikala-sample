package com.example.digikalasample.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.digikalasample.databinding.FragmentChangeThemeBinding


class ChangeThemeFragment : Fragment() {
    private lateinit var binding: FragmentChangeThemeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentChangeThemeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val editor = sharedPreferences.edit()
        changeVisibilityOfAnimations()
        binding.extendedFabDay.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            editor.putBoolean(
                "themeIsDark", false
            )
            editor.apply()
            changeVisibilityOfAnimations()
        }
        binding.extendedFabNight.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            editor.putBoolean(
                "themeIsDark", true
            )
            editor.apply()
            changeVisibilityOfAnimations()
        }


    }

    fun changeVisibilityOfAnimations() {
        val nightModeFlags = requireContext().resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK
        when (nightModeFlags) {
            Configuration.UI_MODE_NIGHT_YES -> {
                binding.animationViewLightMode.visibility = View.GONE
                binding.animationViewDarkMode.visibility = View.VISIBLE
            }
            Configuration.UI_MODE_NIGHT_NO -> {
                binding.animationViewLightMode.visibility = View.VISIBLE
                binding.animationViewDarkMode.visibility = View.GONE
            }
            Configuration.UI_MODE_NIGHT_UNDEFINED -> {
            }
        }
    }

}