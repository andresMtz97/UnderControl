package com.aamg.undercontrol.ui.view

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.aamg.undercontrol.R
import com.aamg.undercontrol.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController
//    private var actionBarMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHostFragment.navController

        binding.bottomNav.setupWithNavController(navController)
//        setupActionBarWithNavController(navController)


        navController.addOnDestinationChangedListener { _, destination, _ ->
            Log.i("NAV", "destination change")

            when (destination.id) {
                R.id.signInFragment, R.id.signUpFragment -> {
                    binding.bottomNav.visibility = View.GONE
//                    supportActionBar?.hide()
//                    actionBarMenu?.findItem(R.id.btnAdd)?.isVisible = false
//                    actionBarMenu?.findItem(R.id.btnSignOut)?.isVisible = false
                }

                R.id.categoriesFragment -> {
//                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
//                    actionBarMenu?.findItem(R.id.btnAdd)?.isVisible = true
                }

                else -> {
//                    supportActionBar?.setDisplayHomeAsUpEnabled(false)
                    binding.bottomNav.visibility = View.VISIBLE
//                    supportActionBar?.show()
//                    actionBarMenu?.findItem(R.id.btnAdd)?.isVisible = false
//                    actionBarMenu?.findItem(R.id.btnSignOut)?.isVisible = true

                }
            }
        }

        initUI()
        // binding.btnAccounts.setOnClickListener { navigateToAccounts() }
//        binding.btnCategories.setOnClickListener { navigateToCategories() }
    }

    private fun initUI() {
//        supportFragmentManager.beginTransaction()
//            .add(R.id.fcMain, SignInFragment.newInstance())
//            .commit()
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return NavigationUI.navigateUp(navController, null)
//    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_action_bar, menu)
//        actionBarMenu = menu
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId) {
//            R.id.btnSignOut -> {
//                navController.popBackStack(R.id.main_graph, true)
//                navController.navigate(R.id.signInFragment)
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }


}