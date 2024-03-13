package com.aamg.undercontrol.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aamg.undercontrol.R
import com.aamg.undercontrol.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

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
        binding.tvSignup.setOnClickListener { showSignUp() }
    }

    private fun showSignUp() {
        parentFragmentManager.beginTransaction()
            .replace(R.id.fcMain, SignUpFragment.newInstance())
            .addToBackStack("SignUpFragment")
            .commit()
    }

    companion object {
        fun newInstance() = SignInFragment()
    }
}