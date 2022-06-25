package com.example.digikalasample.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.digikalasample.R
import com.example.digikalasample.data.errorThatOccur
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException


open class BaseFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        errorThatOccur.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is UnknownHostException -> {
                        showDialog(resources.getString(R.string.error_not_connected))
                    }
                    is retrofit2.HttpException -> showDialog(resources.getString(R.string.error_404))
                    is SocketTimeoutException -> showDialog(resources.getString(R.string.error_not_connected))
                    is ConnectException -> showDialog(resources.getString(R.string.error_server))
                    is SocketException -> showDialog(resources.getString(R.string.error_server))
                    else -> it.message?.let { it1 -> showDialog(it1) }

                }

            }
        }
    }


    private fun showDialog(message: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.error_String))
            .setMessage(message)
            .setPositiveButton(getString(R.string.okay_dialog_button)) { dialog, which ->
            }
            .show()
        errorThatOccur.value = null
    }
}