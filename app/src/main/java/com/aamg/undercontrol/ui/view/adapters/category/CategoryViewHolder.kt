package com.aamg.undercontrol.ui.view.adapters.category

import android.view.View
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aamg.undercontrol.data.remote.model.CategoryDto
import com.aamg.undercontrol.databinding.ItemCategoryBinding

class CategoryViewHolder(view: View): ViewHolder(view) {

    private val binding = ItemCategoryBinding.bind(view)

    fun render(category: CategoryDto, onClickEdit: (Int) -> Unit, onClickDelete: (Int) -> Unit) {
        binding.tvCategoryName.text = category.name
        binding.ibDelete.setOnClickListener { onClickDelete(adapterPosition) }
        binding.ibEdit.setOnClickListener { onClickEdit(adapterPosition) }
    }
}