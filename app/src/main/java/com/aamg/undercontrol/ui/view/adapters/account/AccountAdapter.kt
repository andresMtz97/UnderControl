package com.aamg.undercontrol.ui.view.adapters.account

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.aamg.undercontrol.R
import com.aamg.undercontrol.data.remote.model.AccountDto

class AccountAdapter(
    private var accounts: ArrayList<AccountDto>,
    private val onClickEdit: (Int) -> Unit,
    private val onClickDelete: (Int) -> Unit
): Adapter<AccountViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AccountViewHolder(layoutInflater.inflate(R.layout.item_account, parent, false))
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.render(accounts[position], onClickEdit, onClickDelete)
    }

    override fun getItemCount(): Int = accounts.size

    fun updateList(list: ArrayList<AccountDto>) {
        accounts = list
        notifyDataSetChanged()
    }
}