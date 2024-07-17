package com.aamg.undercontrol.ui.view.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Button
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.DialogFragment
import com.aamg.undercontrol.R
import com.aamg.undercontrol.data.DataProvider
import com.aamg.undercontrol.data.remote.model.AccountDto
import com.aamg.undercontrol.data.remote.model.MovementDto
import com.aamg.undercontrol.data.remote.model.TransactionDto
import com.aamg.undercontrol.data.remote.model.TransferDto
import com.aamg.undercontrol.databinding.DialogEditMovementBinding
import com.aamg.undercontrol.utils.setAdapter
import java.text.SimpleDateFormat
import java.util.Calendar

class MovementDialog(
    private var edit: Boolean = false,
    private var transaction: Boolean = true,
    private var movement: MovementDto,
    private val save: (MovementDto) -> Unit
) : DialogFragment() {
    private var _binding: DialogEditMovementBinding? = null
    private val binding get() = _binding!!

    private lateinit var builder: AlertDialog.Builder
    private lateinit var dialog: Dialog
    private var saveButton: Button? = null

    private var originAccounts: ArrayList<AccountDto> = DataProvider.accounts!!
    private var destinationAccounts: ArrayList<AccountDto> = arrayListOf()
    private var incomeCategories = DataProvider.incomeCategories!!
    private var expenseCategories = DataProvider.expenseCategories!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = DialogEditMovementBinding.inflate(requireActivity().layoutInflater)

        builder = AlertDialog.Builder(requireContext())

        dialog = buildDialog()
        return dialog
    }

    override fun onStart() {
        super.onStart()

        if (transaction) {
            binding.spDestinationAccount.visibility = View.GONE
            binding.tvDestinationAccount.visibility = View.GONE
        } else {
            binding.spCategory.visibility = View.GONE
            binding.tvCategory.visibility = View.GONE
            binding.rgType.visibility = View.GONE
            binding.tvType.visibility = View.GONE
        }

//        if (edit) {
//            binding.etAccountName.setText(account.name)
//            binding.etAccountBalance.setText(account.balance.toString())
//        }

        val alertDialog = dialog as AlertDialog
        saveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
        //saveButton?.isEnabled = validateForm()

        initUI()
    }

    private fun initUI() {
        initSpinners()
        initListeners()
    }

    private fun initSpinners() {
        binding.spCategory.setAdapter(arrayListOf())

        val accounts = originAccounts.map { it.name } as ArrayList<String>
        binding.spOriginAccount.setAdapter(accounts)

        binding.spDestinationAccount.setAdapter(arrayListOf())


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
            requireContext().getString(R.string.edit_movement)
        else
            requireContext().getString(R.string.add_movement)

        return builder.setView(binding.root)
            .setTitle(title)
            .setPositiveButton(positiveBtnText) { _, _ ->
                movement.apply {
                    amount = binding.etAmount.text.toString().toDouble()
                    date = binding.etDate.text.toString()
                    description = binding.etDescription.text.toString()
                    accountId =
                        originAccounts[binding.spOriginAccount.selectedItemPosition - 1].id!!
                }
                if (transaction) setTransaction() else setTransfer()
                save(movement)
            }
            .setNegativeButton(requireContext().getString(R.string.cancel)) { _, _ -> }
            .create()
    }

    private fun validateForm(): Boolean {
        var validation = binding.etAmount.text.isNotBlank() &&
                binding.etDate.text.isNotBlank() &&
                binding.etDescription.text.isNotBlank() &&
                binding.spOriginAccount.selectedItemPosition != 0
        validation = if (transaction) {
            validation && (binding.rbIncome.isChecked || binding.rbExpense.isChecked) &&
                    binding.spCategory.selectedItemPosition != 0
        } else {
            validation && binding.spDestinationAccount.selectedItemPosition != 0
        }

        return validation
    }

    private fun initListeners() {
        binding.etDate.setOnClickListener { showDatePickerDialog() }
        binding.apply {
            etAmount.addTextChangedListener { saveButton?.isEnabled = validateForm() }
            etDate.addTextChangedListener { saveButton?.isEnabled = validateForm() }
            etDescription.addTextChangedListener { saveButton?.isEnabled = validateForm() }
            spOriginAccount.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    Log.d("sporiginaccount", "onItemSelected: $position")
                    if (position != 0) {
                        val idOriginAccount = originAccounts[position - 1].id!!
                        destinationAccounts =
                            originAccounts.filter { it.id != idOriginAccount } as ArrayList<AccountDto>
                        spDestinationAccount.setAdapter(destinationAccounts.map { it.name } as ArrayList<String>)
                    }
                    saveButton?.isEnabled = validateForm()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
            rgType.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.rbIncome -> {
                        val list = incomeCategories.map { it.name } as ArrayList<String>
                        spCategory.setAdapter(list)
                    }

                    R.id.rbExpense -> {
                        val list = expenseCategories.map { it.name } as ArrayList<String>
                        spCategory.setAdapter(list)
                    }
                }
                saveButton?.isEnabled = validateForm()
            }
            spCategory.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    saveButton?.isEnabled = validateForm()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }
            spDestinationAccount.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    saveButton?.isEnabled = validateForm()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}

            }
        }
    }

    private fun setTransaction() {
        val income = binding.rbIncome.isChecked
        val category =
            if (income) incomeCategories[binding.spCategory.selectedItemPosition - 1]
            else expenseCategories[binding.spCategory.selectedItemPosition - 1]
        movement.transaction = TransactionDto(
            type = income,
            categoryId = category.id!!
        )
    }

    private fun setTransfer() {
        movement.transfer = TransferDto(
            accountId = destinationAccounts[binding.spDestinationAccount.selectedItemPosition - 1].id!!
        )
    }

    private fun showDatePickerDialog() {
        val datePicker = DatePickerFragment(binding.etDate.text.toString()) { day, month, year ->
            onDateSelected(day, month, year)
        }
        datePicker.show(parentFragmentManager, "datePicker")
    }

    private fun onDateSelected(day: Int, month: Int, year: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, day)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.YEAR, year)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        binding.etDate.setText(dateFormat.format(calendar.time))
    }
}