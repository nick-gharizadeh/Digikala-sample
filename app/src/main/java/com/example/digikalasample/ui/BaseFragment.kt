package com.example.digikalasample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.digikalasample.R
import com.example.digikalasample.data.model.statusLiveData
import com.google.android.material.dialog.MaterialAlertDialogBuilder


open class BaseFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        statusLiveData.observe(viewLifecycleOwner){
            if (it!=null)
            showDialog(it)
        }
    }


    private fun showDialog(message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.error_String))
            .setMessage(message)
            .setPositiveButton(getString(R.string.okay_dialog_button)) { dialog, which ->
            }
            .show()
        statusLiveData.value = null
    }
}