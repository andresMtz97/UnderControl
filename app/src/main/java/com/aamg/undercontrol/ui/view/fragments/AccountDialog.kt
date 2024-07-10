package com.aamg.undercontrol.ui.view.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.aamg.undercontrol.R
import com.aamg.undercontrol.data.DataProvider
import com.aamg.undercontrol.data.remote.model.AccountDto
import com.aamg.undercontrol.databinding.DialogEditAccountBinding

class AccountDialog(
    private var edit: Boolean = false,
    private var account: AccountDto,
    private val save: (AccountDto) -> Unit
) : DialogFragment() {

    private var _binding: DialogEditAccountBinding? = null
    private val binding get() = _binding!!

    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialog: Dialog
    private var saveButton: Button? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogEditAccountBinding.inflate(requireActivity().layoutInflater)

        builder = AlertDialog.Builder(requireContext())

        dialog = buildDialog()
        return dialog
    }

    override fun onStart() {
        super.onStart()

        if (edit) {
            binding.etAccountName.setText(account.name)
            binding.etAccountBalance.setText(account.balance.toString())
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
        val positiveBtnText = if (edit)
            requireContext().getString(R.string.update)
        else
            requireContext().getString(R.string.save)

        val title = if (edit)
            requireContext().getString(R.string.edit_account)
        else
            requireContext().getString(R.string.create_account)

        return builder.setView(binding.root)
            .setTitle(title)
            .setPositiveButton(positiveBtnText) { _, _ ->
                account.name = binding.etAccountName.text.toString()
                account.balance = binding.etAccountBalance.text.toString().toDouble()
                save(account)
            }
            .setNegativeButton(requireContext().getString(R.string.cancel)) { _, _ -> }
            .create()
    }

    private fun validateForm(): Boolean = binding.etAccountName.text.isNotBlank() &&
            binding.etAccountBalance.text.isNotBlank()

    private fun initListeners() {
        binding.etAccountName.addTextChangedListener { saveButton?.isEnabled = validateForm() }
        binding.etAccountBalance.addTextChangedListener { saveButton?.isEnabled = validateForm() }
    }
}