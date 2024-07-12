package com.aamg.undercontrol.ui.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aamg.undercontrol.data.DataProvider
import com.aamg.undercontrol.data.remote.model.CategoryDto
import com.aamg.undercontrol.databinding.FragmentCategoriesBinding
import com.aamg.undercontrol.ui.view.adapters.category.CategoryAdapter
import com.aamg.undercontrol.ui.viewmodel.Categories
import com.aamg.undercontrol.utils.showErrorDialog

class CategoriesFragment : Fragment() {

    private var _binding: FragmentCategoriesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: Categories by viewModels()

    private lateinit var incomeAdapter: CategoryAdapter
    private lateinit var expenseAdapter: CategoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onCreate()
        initUI()
//        binding.rvCategories.adapter = CategoryAdapter(DataProvider.categories)
//        binding.rvCategories.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.VERTICAL, false)
    }

    private fun initUI() {
        initRV()
        initListeners()
        initObservers()
    }

    private fun initRV() {
        incomeAdapter = CategoryAdapter(
            ArrayList(),
            { onEditBtnTapped(it, true) },
            { onDeleteBtnTapped(it, true) }
        )
        binding.rvIncomeCategories.adapter = incomeAdapter
        binding.rvIncomeCategories.layoutManager = LinearLayoutManager(requireContext())

        expenseAdapter = CategoryAdapter(
            ArrayList(),
            { onEditBtnTapped(it, false) },
            { onDeleteBtnTapped(it, false) }
        )
        binding.rvExpenseCategories.adapter = expenseAdapter
        binding.rvExpenseCategories.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initListeners() {
        binding.fabAddCategory.setOnClickListener { displayEditCategory() }
    }

    private fun initObservers() {
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.incomeCategories.observe(viewLifecycleOwner) {
            Log.i("CategoriesFragment", "incomeCategories: $it")
            if (it.isEmpty()) {
                binding.tvIncomeNoData.visibility = View.VISIBLE
            } else {
                incomeAdapter.updateList(it)
                binding.tvIncomeNoData.visibility = View.GONE
            }
        }

        viewModel.expenseCategories.observe(viewLifecycleOwner) {
            Log.i("CategoriesFragment", "expenseCategories: $it")
            //binding.rvExpenseCategories.adapter = CategoryAdapter(it, {}, {})
            if (it.isEmpty()) {
                binding.tvExpenseNoData.visibility = View.VISIBLE
            } else {
                expenseAdapter.updateList(it)
                binding.tvExpenseNoData.visibility = View.GONE
            }
        }

        viewModel.errors.observe(viewLifecycleOwner) { validationErrors ->
            var message = ""
            validationErrors.forEach { validationError ->
                message += validationError.messages.joinToString("\n")
                message += "\n"
            }
            showErrorDialog(requireContext(), message)
        }
    }

    private fun displayEditCategory(
        isEdit: Boolean = false,
        categoryDto: CategoryDto = CategoryDto(
            name = "",
            income = true,
            userId = DataProvider.currentUser?.id
        ),
        position: Int = -1
    ) {
        val dialog = CategoryDialog(isEdit, categoryDto) { category ->
            if (isEdit) {
                viewModel.updateCategory(category, position)
            } else {
                viewModel.createCategory(category)
            }
        }
        dialog.show(parentFragmentManager, "editCategory")
    }

    private fun onEditBtnTapped(position: Int, isIncome: Boolean) {
        Log.i("CategoriesFragment", "onEditBtnTapped: $position")
        val category =
            if (isIncome) viewModel.incomeCategories.value?.get(position)
            else viewModel.expenseCategories.value?.get(position)

        if (category != null) {
            displayEditCategory(true, category, position)
        }
    }

    private fun onDeleteBtnTapped(position: Int, isIncome: Boolean) {
        val category =
            if (isIncome) viewModel.incomeCategories.value?.get(position)
            else viewModel.expenseCategories.value?.get(position)
        if (category != null) {
            viewModel.deleteCategory(category, position)
        }
    }
}