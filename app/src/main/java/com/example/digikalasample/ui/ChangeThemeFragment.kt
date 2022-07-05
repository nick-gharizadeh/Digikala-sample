package com.example.digikalasample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.digikalasample.databinding.FragmentChangeThemeBinding


enum class Theme {
    Dark,
    Light
}

class ChangeThemeFragment : Fragment() {
    private lateinit var binding: FragmentChangeThemeBinding
    var themeMode = Theme.Light
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
        binding.extendedFabDay.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            editor.putBoolean(
                "themeIsDark", false
            )
            editor.apply()
        }
        binding.extendedFabNight.setOnClickListener {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            editor.putBoolean(
                "themeIsDark", true
            )
            editor.apply()
        }


    }

}