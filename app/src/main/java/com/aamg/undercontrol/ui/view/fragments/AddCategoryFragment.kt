package com.aamg.undercontrol.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.aamg.undercontrol.R
import com.aamg.undercontrol.data.local.model.Category
import com.aamg.undercontrol.data.local.model.TransactionType
import com.aamg.undercontrol.databinding.FragmentAddCategoryBinding

class AddCategoryFragment : Fragment() {

    private lateinit var binding: FragmentAddCategoryBinding
    var category = Category("", null)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.etCategoryName.addTextChangedListener { category.name = it.toString() }
        binding.rgCategoryType.setOnCheckedChangeListener { _, i ->
            category.type = when (i) {
                R.id.rbIncome -> TransactionType.INCOME
                R.id.rbExpense -> TransactionType.EXPENSE
                else -> null
            }
        }
        binding.btnSave.setOnClickListener { saveCategory() }
    }

    private fun saveCategory() {
        if (category.isValid()) {
//            parentFragmentManager.beginTransaction()
//                .replace(R.id.fragmentCategories, CategoriesFragment.newInstance())
//                .commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddCategoryFragment()
    }
}