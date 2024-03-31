package com.example.editfiledemo

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.example.editfiledemo.databinding.FragmentDialogBinding
import java.io.File

class Dialog : DialogFragment() {
    private lateinit var binding: FragmentDialogBinding
    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var itemChoose: AudioModel

    @SuppressLint("UseGetLayoutInflater")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = FragmentDialogBinding.inflate(LayoutInflater.from(context))

        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)

        initData()
        bindView()
        setOnClickListener()

        val dialog = builder.create()
        dialog.window!!.setGravity(Gravity.CENTER)
        return dialog
    }

    private fun initData() {
        itemChoose = mainViewModel.itemChoose
    }

    private fun bindView() {
        binding.edtFileName.setText(itemChoose.name)
    }

    private fun setOnClickListener() {
        binding.tvCancel.setOnClickListener {
            dismiss()
        }

        binding.tvSave.setOnClickListener {
            val oldName = itemChoose.name
            val newName = binding.edtFileName.text.toString()

            if (newName != oldName && newName.isNotEmpty()) {
                val file = File(itemChoose.path)
            }
            dismiss()
        }

        binding.tvDelete.setOnClickListener {
            deleteFile(itemChoose.path)
            dismiss()
        }
    }

    private fun deleteFile(path: String) {
        val fileToDelete = File(path)

        if (fileToDelete.exists()) {
            val deleted = fileToDelete.delete()
            if (deleted) {
                Toast.makeText(requireContext(), "Complete", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "UnComplete", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(requireContext(), "File is not exist", Toast.LENGTH_SHORT).show()
        }
    }
}