package com.aamg.undercontrol.ui.view.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.aamg.undercontrol.data.DataProvider
import com.aamg.undercontrol.data.remote.model.CategoryDto
import com.aamg.undercontrol.databinding.DialogEditCategoryBinding

class CategoryDialog(
//    private var edit: Boolean = false,
    private var category: CategoryDto = CategoryDto(
        name = "",
        income = true,
        userId = DataProvider.currentUser?.id
    ),
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
        return builder.setView(binding.root)
            .setTitle("Categoría")
            .setPositiveButton("Guardar") { _, _ ->
                category.income = binding.rbIncome.isChecked
                category.name = binding.etCategoryName.text.toString()
                createCategory(category)
            }
            .setNegativeButton("Cancelar") { _, _ -> }
            .create()
    }

    private fun validateForm(): Boolean = binding.etCategoryName.text.isNotBlank() &&
            (binding.rbIncome.isChecked || binding.rbExpense.isChecked)

    private fun initListeners() {
        binding.etCategoryName.addTextChangedListener { saveButton?.isEnabled = validateForm() }
        binding.rgCategoryType.setOnCheckedChangeListener { _, _ ->
            saveButton?.isEnabled = validateForm()
        }
    }
}