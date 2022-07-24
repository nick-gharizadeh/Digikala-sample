package com.example.digikalasample.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.digikalasample.R
import com.example.digikalasample.databinding.FragmentAccountBinding
import com.example.digikalasample.viewmodel.CustomerViewModel
import com.example.digikalasample.viewmodel.ProductViewModel


class AccountFragment : BaseFragment() {
    private lateinit var binding: FragmentAccountBinding
    val productViewModel: ProductViewModel by activityViewModels()
    val customerViewModel: CustomerViewModel by activityViewModels()


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
        if (customerViewModel.mCustomerId == null) {
            Toast.makeText(
                requireContext(),
                "برای مشاهده حساب کاربری، ابتدا ثبت نام کنید",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_accountFragment_to_registerFragment)
        } else {
            Glide.with(requireContext())
                .load(customerViewModel.mCustomer.value?.avatar_url)
                .placeholder(R.drawable.place_holder)
                .circleCrop()
                .into(binding.imageViewAvatar)
            binding.textViewName.text =
                "${customerViewModel.mCustomer.value?.first_name} ${customerViewModel.mCustomer.value?.last_name}"
            binding.textViewEmail.text = customerViewModel.mCustomer.value?.email
            binding.buttonLogOut.setOnClickListener {
                sharedPreferences.edit().remove("CustomerId").apply()
                customerViewModel.mCustomerId = null
                customerViewModel.mCustomer.value = null
                findNavController().navigate(R.id.action_accountFragment_to_mainFragment)

            }
        }

    }
}