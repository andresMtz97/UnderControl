package com.aamg.undercontrol.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.aamg.undercontrol.R
import com.aamg.undercontrol.databinding.FragmentHomeBinding
import com.aamg.undercontrol.ui.viewmodel.Home

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: Home by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val activity = requireActivity() as? AppCompatActivity
//        activity?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

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
        initListeners()
        initObservers()
    }

    private fun initListeners() {

    }

    private fun initObservers() {
        viewModel.name.observe(viewLifecycleOwner) {
            binding.tvWelcome.text = requireContext().getString(R.string.welcome, it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}