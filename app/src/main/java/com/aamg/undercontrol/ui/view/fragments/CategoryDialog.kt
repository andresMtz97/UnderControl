package com.aamg.undercontrol.ui.view.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.aamg.undercontrol.R
import com.aamg.undercontrol.data.DataProvider
import com.aamg.undercontrol.data.remote.model.CategoryDto
import com.aamg.undercontrol.databinding.DialogEditCategoryBinding

class CategoryDialog(
    private var edit: Boolean = false,
    private var category: CategoryDto,
    private val createCategory: (CategoryDto) -> Unit
) : DialogFragment() {

    private var _binding: DialogEditCategoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialog: Dialog
    private var saveButton: Button? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogEditCategoryBinding.inflate(requireActivity().layoutInflater)

        builder = AlertDialog.Builder(requireContext())

        dialog = buildDialog()
        return dialog
    }

    override fun onStart() {
        super.onStart()

        if (edit) {
            binding.etCategoryName.setText(category.name)
            binding.rgCategoryType.visibility = View.GONE
            binding.tvCategoryType.visibility = View.GONE
        }

        val alertDialog = dialog as AlertDialog
        saveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        saveButton?.isEnabled = validateForm()

        initListeners()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun buildDialog(): Dialog {
        val positiveBtnText = if (edit) requireContext().getString(R.string.update) else requireContext().getString(R.string.save)
        val title = if (edit) requireContext().getString(R.string.edit_category) else requireContext().getString(R.string.add_category)

        return builder.setView(binding.root)
            .setTitle(title)
            .setPositiveButton(positiveBtnText) { _, _ ->
                if (!edit) {
                    category.income = binding.rbIncome.isChecked
                }
                category.name = binding.etCategoryName.text.toString()
                createCategory(category)
            }
            .setNegativeButton(requireContext().getString(R.string.cancel)) { _, _ -> }
            .create()
    }

    private fun validateForm(): Boolean {
        return if (edit) {
            binding.etCategoryName.text.isNotBlank()
        } else {
            binding.etCategoryName.text.isNotBlank() &&
                    (binding.rbIncome.isChecked || binding.rbExpense.isChecked)
        }
    }

    private fun initListeners() {
        binding.etCategoryName.addTextChangedListener { saveButton?.isEnabled = validateForm() }
        binding.rgCategoryType.setOnCheckedChangeListener { _, _ ->
            saveButton?.isEnabled = validateForm()
        }
    }
}