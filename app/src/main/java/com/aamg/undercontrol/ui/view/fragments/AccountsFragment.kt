package com.aamg.undercontrol.ui.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.aamg.undercontrol.databinding.FragmentAccountsBinding
import com.aamg.undercontrol.ui.view.adapters.account.AccountAdapter
import com.aamg.undercontrol.ui.viewmodel.Accounts

class AccountsFragment : Fragment() {

    private var _binding: FragmentAccountsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: Accounts by viewModels()

    private lateinit var accountsAdapter: AccountAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAccountsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onCreate()
        initUI()
    }

    private fun initUI() {
        initRV()
        initListeners()
        initObservers()
    }

    private fun initRV() {
        accountsAdapter = AccountAdapter(ArrayList(), {}, {})
        binding.rvAccounts.adapter = accountsAdapter
        binding.rvAccounts.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun initListeners() {
        binding.fabAddAccount.setOnClickListener { displayAddAccount() }
    }

    private fun initObservers() {
        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            binding.pbLoading.visibility = if (loading) View.VISIBLE else View.GONE
        }

        viewModel.accounts.observe(viewLifecycleOwner) { accounts ->
            if (accounts.isEmpty()) {
                binding.tvNoData.visibility = View.VISIBLE
            } else {
                Log.i("AccountsFragment", "accounts: $accounts")
                binding.tvNoData.visibility = View.GONE
                accountsAdapter.updateList(accounts)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun displayAddAccount() {
        val dialog = AccountDialog { account -> viewModel.createAccount(account) }
        dialog.show(parentFragmentManager, "addAccount")
    }
}