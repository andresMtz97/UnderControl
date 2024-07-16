package com.aamg.undercontrol.ui.view.adapters.movement

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.aamg.undercontrol.R
import com.aamg.undercontrol.data.remote.model.CategoryDto
import com.aamg.undercontrol.data.remote.model.MovementDto

class MovementAdapter(
    private var movements: ArrayList<MovementDto>,
    private val onClickEdit: (MovementDto) -> Unit,
    private val onClickDelete: (MovementDto) -> Unit
): Adapter<MovementViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovementViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MovementViewHolder(layoutInflater.inflate(R.layout.item_movement, parent, false))
    }

    override fun getItemCount(): Int = movements.size

    override fun onBindViewHolder(holder: MovementViewHolder, position: Int) {
        holder.render(movements[position], onClickEdit, onClickDelete)
    }

    fun updateList(list: ArrayList<MovementDto>) {
        movements = list
        notifyDataSetChanged()
    }
}