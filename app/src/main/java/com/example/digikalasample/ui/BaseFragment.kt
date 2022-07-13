package com.example.digikalasample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.digikalasample.R
import com.example.digikalasample.data.model.statusLiveData
import com.google.android.material.dialog.MaterialAlertDialogBuilder


open class BaseFragment : Fragment() {
    var dialog: androidx.appcompat.app.AlertDialog? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        statusLiveData.observe(viewLifecycleOwner) {
            if (it != null)
                showDialog(it)
        }
    }


    private fun showDialog(message: String) {
        if (dialog == null) {
            dialog = buildDialog(message)
        } else if (!dialog!!.isShowing) {
            dialog = buildDialog(message)
        }
        statusLiveData.value = null
    }

    private fun buildDialog(message: String): AlertDialog? {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.error_String))
            .setMessage(message)
            .setPositiveButton(getString(R.string.okay_dialog_button)) { dialog, which ->
            }
            .show()
    }
}