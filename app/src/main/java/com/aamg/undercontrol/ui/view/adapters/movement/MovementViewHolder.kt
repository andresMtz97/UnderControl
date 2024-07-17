package com.aamg.undercontrol.ui.view.adapters.movement

import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aamg.undercontrol.R
import com.aamg.undercontrol.data.DataProvider
import com.aamg.undercontrol.data.remote.model.AccountDto
import com.aamg.undercontrol.data.remote.model.MovementDto
import com.aamg.undercontrol.databinding.ItemMovementBinding
import com.aamg.undercontrol.utils.toCurrency

class MovementViewHolder(view: View): ViewHolder(view) {

    private val binding = ItemMovementBinding.bind(view)

    fun render(movement: MovementDto, onClickEdit: (MovementDto) -> Unit, onClickDelete: (MovementDto) -> Unit) {
        Log.d("render-origin", movement.toString())
        binding.tvDescription.text = movement.description
        binding.tvDate.text = movement.date
        binding.tvAmount.text = movement.amount.toCurrency()
        if (movement.transaction != null) {
            binding.tvAccount.text = movement.account?.name ?: ""
            if (movement.transaction!!.type) {
                binding.ivType.setImageResource(R.drawable.ic_income)
                binding.ivType.setColorFilter(
                    ContextCompat.getColor(binding.root.context, android.R.color.holo_green_dark),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
            } else {
                binding.ivType.setImageResource(R.drawable.ic_expense)
                binding.ivType.setColorFilter(
                    ContextCompat.getColor(binding.root.context, android.R.color.holo_red_dark),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
            }
        } else {
            binding.ivType.setImageResource(R.drawable.ic_transfer)
            binding.ivType.setColorFilter(
                ContextCompat.getColor(binding.root.context, android.R.color.holo_blue_dark),
                android.graphics.PorterDuff.Mode.SRC_IN
            )
            val destinationAccount =
                DataProvider.accounts?.filter { it.id == movement.transfer?.accountId }!![0]

            val originDestination: AccountDto = movement.account
                ?: DataProvider.accounts!!.filter { it.id == movement.accountId }[0]

            Log.d("render", destinationAccount.toString())

            binding.tvAccount.text = binding.root.context.getString(
                R.string.transfer_format,
                originDestination.name,
                destinationAccount.name
            )
        }
//        binding.btnEdit.setOnClickListener { onClickEdit(movement) }
//        binding.btnDelete.setOnClickListener { onClickDelete(movement) }
    }
}