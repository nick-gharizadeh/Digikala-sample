package com.example.digikalasample.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.digikalasample.R
import com.example.digikalasample.databinding.FragmentAccountBinding
import com.example.digikalasample.databinding.FragmentMainBinding
import com.example.digikalasample.viewmodel.ProductViewModel


class AccountFragment : BaseFragment() {
    private lateinit var binding: FragmentAccountBinding
    val productViewModel: ProductViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       if (productViewModel.mCustomerId==null){
           Toast.makeText(requireContext(), "برای مشاهده حساب کاربری، ابتدا ثبت نام کنید", Toast.LENGTH_SHORT).show()
           findNavController().navigate(R.id.action_accountFragment_to_registerFragment)
       }
        else
       {
           Glide.with(requireContext())
               .load(productViewModel.mCustomer.value?.avatar_url)
               .placeholder(R.drawable.place_holder)
               .circleCrop()
               .into(binding.imageViewAvatar)
           binding.textViewName.text = "${productViewModel.mCustomer.value?.first_name} ${productViewModel.mCustomer.value?.last_name}"
           binding.textViewEmail.text=productViewModel.mCustomer.value?.email
           binding.buttonLogOut.setOnClickListener {
               sharedPreferences.edit().remove("CustomerId").apply()
               productViewModel.mCustomerId=null
               productViewModel.mCustomer.value=null
           }
       }

    }
}