package com.aamg.undercontrol.category.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aamg.undercontrol.category.Category
import com.aamg.undercontrol.databinding.ItemCategoryBinding

class CategoryViewHolder(view: View): ViewHolder(view) {

    private val binding = ItemCategoryBinding.bind(view)

    fun render(category: Category, onClickDelete: (Int) -> Unit, onClickEdit: (Int) -> Unit) {
        binding.tvCategoryName.text = category.name
        binding.ibDelete.setOnClickListener { onClickDelete(adapterPosition) }
        binding.ibEdit.setOnClickListener { onClickEdit(adapterPosition) }
    }
}