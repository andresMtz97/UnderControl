package com.aamg.undercontrol.ui.view.adapters.account

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.aamg.undercontrol.R
import com.aamg.undercontrol.data.local.model.Account

class AccountAdapter(
    private val accounts: ArrayList<Account>,
    private val onClickDelete: (Int) -> Unit,
    private val onClickEdit: (Int) -> Unit
): Adapter<AccountViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AccountViewHolder(layoutInflater.inflate(R.layout.item_account, parent, false))
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.render(accounts[position], onClickDelete, onClickEdit)
    }

    override fun getItemCount(): Int = accounts.size
}