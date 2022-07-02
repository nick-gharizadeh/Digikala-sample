package com.example.digikalasample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.digikalasample.data.model.review.Review
import com.example.digikalasample.data.model.review.ReviewerAvatarUrls
import com.example.digikalasample.databinding.FragmentReviewBinding
import com.example.digikalasample.ui.adapter.ReviewAdapter
import com.example.digikalasample.viewmodel.ProductViewModel

class ReviewFragment : BaseFragment() {
    private lateinit var binding: FragmentReviewBinding
    val productViewModel: ProductViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReviewBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val reviewAdapter = ReviewAdapter()
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
            if (binding.TextFieldReview.editText?.text?.isNotBlank() == true) {
                productViewModel.mCustomerId?.let { it1 -> productViewModel.getCustomer(it1) }
                productViewModel.mCustomer.observe(viewLifecycleOwner) { customer ->
                    binding.TextFieldReview.editText?.text!!.clear()
                    productViewModel.postReview(
                        Review(
                            product_id = productViewModel.mProduct!!.id,
                            review = binding.TextFieldReview.editText!!.text.toString(),
                            reviewer = "${customer?.first_name} ${customer?.last_name}",
                            reviewer_email = customer!!.email
                        )
                    )
                }
            }
        }

    }

}