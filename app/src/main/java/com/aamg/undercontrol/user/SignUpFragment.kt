package com.aamg.undercontrol.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.aamg.undercontrol.DataProvider
import com.aamg.undercontrol.HomeActivity
import com.aamg.undercontrol.R
import com.aamg.undercontrol.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private var firstName = ""
    private var lastName = ""
    private var userName = ""
    private var password = ""

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
        initListeners()
    }

    private fun initListeners() {
        binding.etFirstName.addTextChangedListener { firstName = it.toString() }
        binding.etLastName.addTextChangedListener { lastName = it.toString() }
        binding.etUserName.addTextChangedListener { userName = it.toString() }
        binding.etPassword.addTextChangedListener { password = it.toString() }
        binding.btnSubmit.setOnClickListener { navigateToHome() }
    }

    private fun navigateToHome() {
        if (validateForm()) {
            DataProvider.users[userName] = User(firstName, lastName, userName, password)
            DataProvider.actualUser = DataProvider.users[userName]

//            val bundle = Bundle().apply {
//                putString("name", name)
//                putString("lastname", lastname)
//                putString("email", email)
//                putString("password", password)
//                putString("gender", gender)
//            }
            activity?.finish()
            val intent = Intent(requireContext(), HomeActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(requireContext(), "Errores en formulario", Toast.LENGTH_SHORT).show()
        }
    }

    private fun validateForm(): Boolean {
        var isValid = true

        if (firstName.isBlank()) {
            isValid = false
            binding.etFirstName.error = getString(R.string.required)
        }
        if (lastName.isBlank()) {
            isValid = false
            binding.etLastName.error = getString(R.string.required)
        }
        if (userName.isBlank()) {
            isValid = false
            binding.etUserName.error = getString(R.string.required)
        }
        if (password.isBlank()) {
            isValid = false
            binding.etPassword.error = getString(R.string.required)
        }
        return isValid
    }

    companion object {
        fun newInstance() = SignUpFragment()
    }
}