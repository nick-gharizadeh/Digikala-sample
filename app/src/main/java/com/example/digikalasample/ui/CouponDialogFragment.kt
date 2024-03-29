package com.example.digikalasample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.digikalasample.R
import com.example.digikalasample.viewmodel.ProductViewModel
import com.google.android.material.textfield.TextInputLayout


class CouponDialogFragment : DialogFragment() {
    val productViewModel: ProductViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return inflater.inflate(R.layout.fragment_dialog_coupon, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productViewModel.getCoupons()
        val buttonSubmit = view.findViewById<Button>(R.id.button_submit_coupon)
        val textViewCoupon = view.findViewById<TextInputLayout>(R.id.editText_coupon)
        buttonSubmit.setOnClickListener {
            if (textViewCoupon.editText?.text.toString().isNotBlank())
                if (productViewModel.isItExistsInTheCoupons(
                        textViewCoupon.editText?.text.toString().trim().lowercase()
                    ) && !productViewModel.flagOnceUseCoupon
                ) {
                    productViewModel.flagOnceUseCoupon = true
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.valid_coupon_message),
                        Toast.LENGTH_SHORT
                    ).show()
                } else
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.invalid_coupon_message),
                        Toast.LENGTH_SHORT
                    ).show()
            else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.enter_coupon_message),
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }
}