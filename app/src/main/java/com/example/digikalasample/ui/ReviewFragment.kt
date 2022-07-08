package com.example.digikalasample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.digikalasample.data.model.review.Review
import com.example.digikalasample.databinding.FragmentReviewBinding
import com.example.digikalasample.ui.adapter.ReviewAdapter
import com.example.digikalasample.viewmodel.ProductViewModel


var flagUserWantToEditReview = false

class ReviewFragment : BaseFragment() {
    private lateinit var binding: FragmentReviewBinding
    private var reviewId: Int? = null
    val productViewModel: ProductViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReviewBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("review", binding.TextFieldReview.editText?.text.toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState != null) {
            binding.TextFieldReview.editText?.setText(savedInstanceState.getString("review"))
        }

        val reviewAdapter =
            ReviewAdapter({ deleteReview(it) }, { editReview(it) }, productViewModel.customerEmail)
        binding.recyclerViewReviews.adapter = reviewAdapter
        productViewModel.reviewsList.observe(viewLifecycleOwner) {
            if (it != null)
                for (review in it) {
                    review?.review = review?.review?.let { it1 ->
                        RemoveHTMLTags.removeHTMLTagsFromString(
                            it1
                        )
                    }.toString()
                }
            reviewAdapter.submitList(it)
        }

        binding.buttonSendReview.setOnClickListener {
            if (flagUserWantToEditReview) {
                productViewModel.updateReview(
                    reviewId!!,
                    binding.TextFieldReview.editText?.text.toString()
                )
                binding.TextFieldReview.editText?.text?.clear()
                reviewId = null
                flagUserWantToEditReview = false
                return@setOnClickListener
            }
            if (binding.TextFieldReview.editText?.text?.isNotBlank() == true) {
                if (productViewModel.mCustomerId != null) {
                    val review = Review(
                        product_id = productViewModel.mProduct!!.id,
                        review = binding.TextFieldReview.editText?.text.toString(),
                        reviewer = "${productViewModel.mCustomer.value?.first_name} ${productViewModel.mCustomer.value?.last_name}",
                        reviewer_email = productViewModel.mCustomer.value!!.email

                    )
                    productViewModel.postReview(
                        review
                    )
                    Toast.makeText(requireContext(), "با موفقیت ثبت شد", Toast.LENGTH_SHORT)
                        .show()
                    binding.TextFieldReview.editText!!.text.clear()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "برای ثبت نظر ابتدا ثبت نام کنید",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(requireContext(), "نظر خود را وارد کنید ! ", Toast.LENGTH_SHORT)
                    .show()

            }
        }

    }

    private fun deleteReview(review: Review) {
        productViewModel.deleteReview(review.id)
    }

    private fun editReview(review: Review) {
        binding.TextFieldReview.editText?.setText(review.review)
        flagUserWantToEditReview = true
        reviewId = review.id
    }

}