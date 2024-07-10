package com.aamg.undercontrol.ui.view.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.aamg.undercontrol.data.DataProvider
import com.aamg.undercontrol.data.remote.model.AccountDto
import com.aamg.undercontrol.databinding.DialogEditAccountBinding

class AccountDialog(
    private var account: AccountDto = AccountDto(
        name = "",
        balance = 0.0,
        userId = DataProvider.currentUser?.id
    ),
    private val createAccount: (AccountDto) -> Unit
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
                account.name = binding.etAccountName.text.toString()
                account.balance = binding.etAccountBalance.text.toString().toDouble()
                createAccount(account)
            }
            .setNegativeButton("Cancelar") { _, _ -> }
            .create()
    }

    private fun validateForm(): Boolean = binding.etAccountName.text.isNotBlank() &&
            binding.etAccountBalance.text.isNotBlank()

    private fun initListeners() {
        binding.etAccountName.addTextChangedListener { saveButton?.isEnabled = validateForm() }
        binding.etAccountBalance.addTextChangedListener { saveButton?.isEnabled = validateForm() }
    }
}