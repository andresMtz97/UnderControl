package com.aamg.undercontrol.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aamg.undercontrol.R
import com.aamg.undercontrol.data.remote.model.MovementDto
import com.aamg.undercontrol.databinding.FragmentHomeBinding
import com.aamg.undercontrol.ui.view.adapters.movement.MovementAdapter
import com.aamg.undercontrol.ui.viewmodel.Home
import com.aamg.undercontrol.utils.toCurrency

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: Home by viewModels()

    private lateinit var adapter: MovementAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        viewModel.onCreate()
        initRV()
        initListeners()
        initObservers()
    }

    private fun initRV() {
        adapter = MovementAdapter(ArrayList(), {}, {})
        binding.rvTransactions.adapter = adapter
        binding.rvTransactions.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initListeners() {
        binding.mbAddTransaction.setOnClickListener { displayEditMovement() }
        binding.mbAddTransfer.setOnClickListener { displayEditMovement(transaction = false) }
    }

    private fun initObservers() {
        viewModel.month.observe(viewLifecycleOwner) {
            binding.tvMonthSummary.text = requireContext().getString(R.string.summary_month, it)
        }

        viewModel.name.observe(viewLifecycleOwner) {
            binding.tvWelcome.text = requireContext().getString(R.string.welcome, it)
        }

        viewModel.loading.observe(viewLifecycleOwner) {
            binding.pbLoading.visibility = if (it) View.VISIBLE else View.GONE
        }

        viewModel.movements.observe(viewLifecycleOwner) { movements ->
            if (movements.isEmpty()) {
                binding.tvNoData.visibility = View.VISIBLE
                val zero = (00000.0).toCurrency()
                binding.tvTotalIncome.text = requireContext().getString(R.string.total_income, zero)
                binding.tvTotalExpense.text =
                    requireContext().getString(R.string.total_expenses, zero)
            } else {
                binding.tvNoData.visibility = View.GONE
                adapter.updateList(movements)
                updateSummary(movements)
            }
        }
    }

    private fun updateSummary(movements: ArrayList<MovementDto>) {
        var income = 0.0
        var expense = 0.0

        for (movement in movements) {
            if (movement.transaction !== null) {
                if (movement.transaction!!.type) {
                    income += movement.amount
                } else {
                    expense += movement.amount
                }
            }
        }

        binding.tvTotalIncome.text =
            requireContext().getString(R.string.total_income, income.toCurrency())
        binding.tvTotalExpense.text =
            requireContext().getString(R.string.total_expenses, expense.toCurrency())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun displayEditMovement(
        isEdit: Boolean = false,
        transaction: Boolean = true,
        movementDto: MovementDto = MovementDto(
            amount = 0.0,
            description = "",
            date = "",
            accountId = -1
        ),
        position: Int = -1
    ) {
        val dialog = MovementDialog(isEdit, transaction, movementDto) { movement ->
            if (isEdit) {
                viewModel.update(movement, position)
            } else {
                viewModel.create(movement)
            }
        }
        dialog.show(parentFragmentManager, "editMovement")
    }
}