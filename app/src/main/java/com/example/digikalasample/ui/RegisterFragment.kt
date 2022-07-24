package com.example.digikalasample.ui

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.digikalasample.R
import com.example.digikalasample.databinding.FragmentRegisterBinding
import com.example.digikalasample.viewmodel.CustomerViewModel
import com.example.digikalasample.viewmodel.ProductViewModel
import com.google.android.material.textfield.TextInputLayout


class RegisterFragment : BaseFragment() {
    private lateinit var binding: FragmentRegisterBinding
    val productViewModel: ProductViewModel by activityViewModels()
    val customerViewModel: CustomerViewModel by activityViewModels()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRegister.setOnClickListener {
            setErrorNull(binding.editTextFirstname)
            setErrorNull(binding.editTextLastname)
            setErrorNull(binding.editTextEmail)
            if (validate()) {
                customerViewModel.createCustomer(
                    binding.editTextFirstname.editText?.text.toString(),
                    binding.editTextLastname.editText?.text.toString(),
                    binding.editTextEmail.editText?.text.toString()
                )

                Toast.makeText(requireContext(), "ثبت نام با موفقیت انجام شد", Toast.LENGTH_SHORT)
                    .show()
                findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
            }
        }

    }

    private fun validate(): Boolean {
        if (binding.editTextFirstname.editText?.text.toString().isBlank()) {
            binding.editTextFirstname.error = " نام خود را وارد کنید  "
            return false
        }
        if (binding.editTextLastname.editText?.text.toString().isBlank()) {
            binding.editTextLastname.error = "نام خانوادگی خود را وارد کنید   "
            return false
        }

        if (binding.editTextEmail.editText?.text.toString().isBlank()) {
            binding.editTextEmail.error = "ایمیل خود را وارد کنید  "
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(binding.editTextEmail.editText?.text.toString())
                .matches()
        ) {
            binding.editTextEmail.error = "ایمیل معتبر نیست "
            return false
        }

        return true
    }

    private fun setErrorNull(view: TextInputLayout) {
        view.error = null
    }
}