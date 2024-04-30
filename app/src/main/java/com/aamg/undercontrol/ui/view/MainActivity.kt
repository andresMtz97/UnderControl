package com.aamg.undercontrol.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.aamg.undercontrol.R
import com.aamg.undercontrol.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupActionBarWithNavController(this, navController)

        initUI()
        // binding.btnAccounts.setOnClickListener { navigateToAccounts() }
//        binding.btnCategories.setOnClickListener { navigateToCategories() }
    }

    private fun initUI() {
//        supportFragmentManager.beginTransaction()
//            .add(R.id.fcMain, SignInFragment.newInstance())
//            .commit()
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
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }


}