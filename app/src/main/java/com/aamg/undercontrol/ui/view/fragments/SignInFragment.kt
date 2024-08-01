package com.aamg.undercontrol.ui.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aamg.undercontrol.R
import com.aamg.undercontrol.data.remote.model.SignInData
import com.aamg.undercontrol.databinding.FragmentSignInBinding
import com.aamg.undercontrol.ui.viewmodel.SignIn
import com.aamg.undercontrol.utils.showErrorDialog

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignIn by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.btnSignIn.isEnabled = validateForm()
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding.etUserName.addTextChangedListener { binding.btnSignIn.isEnabled = validateForm() }
        binding.etPassword.addTextChangedListener { binding.btnSignIn.isEnabled = validateForm() }
        binding.btnSignIn.setOnClickListener { sendData() }
        binding.tvSignup.setOnClickListener { showSignUp() }
    }

    private fun initObservers() {
        viewModel.success.observe(viewLifecycleOwner) {success ->
            if (success) {
                findNavController().navigate(R.id.action_signInFragment_to_homeFragment)
            }
        }

        viewModel.error.observe(viewLifecycleOwner) {
            showErrorDialog(requireContext(), it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.btnSignIn.isEnabled = !isLoading
            binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    private fun sendData() {
        val data = SignInData(
            binding.etUserName.text.toString(),
            binding.etPassword.text.toString()
        )
        viewModel.signIn(data)
    }

    private fun showSignUp() {
        findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
//        parentFragmentManager.beginTransaction()
//            .replace(R.id.fcMain, SignUpFragment.newInstance())
//            .addToBackStack("SignUpFragment")
//            .commit()
    }

    private fun validateForm(): Boolean = binding.etUserName.text.isNotBlank() &&
            binding.etPassword.text.isNotBlank()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}