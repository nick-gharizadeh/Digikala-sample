package com.example.digikalasample.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.digikalasample.R
import com.example.digikalasample.databinding.FragmentRegisterBinding
import com.example.digikalasample.databinding.FragmentReviewBinding
import com.example.digikalasample.ui.adapter.ReviewAdapter
import com.example.digikalasample.viewmodel.ProductViewModel

class ReviewFragment : Fragment() {
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

    }

}