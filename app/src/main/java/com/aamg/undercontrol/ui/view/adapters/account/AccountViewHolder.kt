package com.aamg.undercontrol.ui.view.adapters.account

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aamg.undercontrol.data.remote.model.AccountDto
import com.aamg.undercontrol.databinding.ItemAccountBinding
import java.text.NumberFormat
import java.util.Locale

class AccountViewHolder(view: View) : ViewHolder(view) {

    private val binding = ItemAccountBinding.bind(view)

    fun render(account: AccountDto, onClickDelete: (Int) -> Unit, onClickEdit: (Int) -> Unit) {
        binding.tvAccountName.text = account.name
        val formatter = NumberFormat.getCurrencyInstance(Locale.US)
        binding.tvAccountBalance.text = formatter.format(account.balance)

        binding.ibDelete.setOnClickListener { onClickDelete(adapterPosition) }
        binding.ibEdit.setOnClickListener { onClickEdit(adapterPosition) }
    }
}