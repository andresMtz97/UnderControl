package com.aamg.undercontrol.ui.view.fragments

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import com.aamg.undercontrol.data.local.DataProvider
import com.aamg.undercontrol.ui.view.HomeActivity
import com.aamg.undercontrol.R
import com.aamg.undercontrol.databinding.FragmentSignInBinding
import com.google.android.material.snackbar.Snackbar

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private var userName = ""
    private var password = ""

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
        initListeners()
    }

    private fun initListeners() {
        binding.etUserName.addTextChangedListener { userName = it.toString() }
        binding.etPassword.addTextChangedListener { password = it.toString() }
        binding.btnSignIn.setOnClickListener { signIn() }
        binding.tvSignup.setOnClickListener { showSignUp() }
    }

    private fun signIn() {
        if (validateForm() && validateUser()) {
            DataProvider.actualUser = DataProvider.users[userName]
            navigateToHome()
        }

//            DataProvider.users.containsKey()
    }

    private fun navigateToHome() {
        activity?.finish()
        val intent = Intent(requireContext(), HomeActivity::class.java)
        startActivity(intent)
    }

    private fun showSignUp() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fcMain, SignUpFragment.newInstance())
            .addToBackStack("SignUpFragment")
            .commit()
    }

    private fun validateForm(): Boolean {
        var isValid = true

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

    private fun validateUser(): Boolean {
        var isValid = false
        if (DataProvider.users.containsKey(userName) && DataProvider.users[userName]?.password == password){
            isValid = true
        } else {
            val snackbar = Snackbar.make(binding.root, getString(R.string.signin_failed), Snackbar.LENGTH_LONG )
            snackbar.setBackgroundTint(Color.RED)
            snackbar.show()
        }
        return isValid
    }

    companion object {
        fun newInstance() = SignInFragment()
    }
}