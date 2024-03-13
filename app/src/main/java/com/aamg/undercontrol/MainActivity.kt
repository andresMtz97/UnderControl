package com.aamg.undercontrol

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aamg.undercontrol.databinding.ActivityMainBinding
import com.aamg.undercontrol.user.SignInFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(R.id.fcMain, SignInFragment.newInstance())
            .commit()

        // binding.btnAccounts.setOnClickListener { navigateToAccounts() }
//        binding.btnCategories.setOnClickListener { navigateToCategories() }
    }

//    override fun onBackPressed() {
//        super.onBackPressed()
//        supportFragmentManager.
//    }

//    private fun navigateToAccounts() {
//        val intent = Intent(this,)
//    }

//    private fun navigateToCategories() {
//        val intent = Intent(this, CategoryActivity::class.java)
//        startActivity(intent)
//    }


}