package com.aamg.undercontrol.ui.view.adapters.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.aamg.undercontrol.R
import com.aamg.undercontrol.data.remote.model.CategoryDto

class CategoryAdapter(
    private var categories: ArrayList<CategoryDto>,
    private val onClickEdit: (Int) -> Unit,
    private val onClickDelete: (Int) -> Unit
): Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CategoryViewHolder(layoutInflater.inflate(R.layout.item_category, parent, false))
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.render(categories[position], onClickEdit, onClickDelete)
    }

    fun updateList(list: ArrayList<CategoryDto>) {
        categories = list
        notifyDataSetChanged()
    }
}