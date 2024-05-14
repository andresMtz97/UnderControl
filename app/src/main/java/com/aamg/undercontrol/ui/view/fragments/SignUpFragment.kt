package com.aamg.undercontrol.ui.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.aamg.undercontrol.R
import com.aamg.undercontrol.data.remote.model.UserDto
import com.aamg.undercontrol.databinding.FragmentSignUpBinding
import com.aamg.undercontrol.ui.viewmodel.SignUp
import com.aamg.undercontrol.utils.showErrorDialog
import com.google.android.material.snackbar.Snackbar

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SignUp by viewModels()

//    private var user = User(null,"", "", "", "")

//    private var firstName = ""
//    private var lastName = ""
//    private var userName = ""
//    private var password = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        Log.i("BTN_ENABLED", validateForm().toString())
        binding.btnSubmit.isEnabled = validateForm()
        initListeners()
        initObservers()
    }

    private fun initListeners() {
        binding.etFirstName.addTextChangedListener { binding.btnSubmit.isEnabled = validateForm() }
        binding.etLastName.addTextChangedListener { binding.btnSubmit.isEnabled = validateForm() }
        binding.etUserName.addTextChangedListener { binding.btnSubmit.isEnabled = validateForm() }
        binding.etPassword.addTextChangedListener { binding.btnSubmit.isEnabled = validateForm() }
        binding.btnSubmit.setOnClickListener { sendData() } //viewModel.signUp(user)
    }

    private fun initObservers() {
        viewModel.successMsg.observe(viewLifecycleOwner) {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
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

    private fun sendData() {
        val user = UserDto(
            name = binding.etFirstName.text.toString(),
            lastName = binding.etLastName.text.toString(),
            username = binding.etUserName.text.toString(),
            password = binding.etPassword.text.toString()
        )
        viewModel.signUp(user)
    }

    private fun validateForm(): Boolean = binding.etFirstName.text.isNotBlank() &&
            binding.etLastName.text.isNotBlank() &&
            binding.etUserName.text.isNotBlank() &&
            binding.etPassword.text.isNotBlank()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}