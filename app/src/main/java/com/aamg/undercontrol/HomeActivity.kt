package com.aamg.undercontrol

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.aamg.undercontrol.account.AccountActivity
import com.aamg.undercontrol.category.CategoryActivity
import com.aamg.undercontrol.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
//    private var menu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = "Home"

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        binding.tvHome.text = "Bienvenido ${DataProvider.actualUser?.firstName}"
        initListeners()
    }

    private fun initListeners() {
        binding.btnCategories.setOnClickListener { navigateToCategories() }
        binding.btnAccounts.setOnClickListener { navigateToAccounts() }
    }

    private fun navigateToAccounts() {
        val intent = Intent(this, AccountActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToCategories() {
        val intent = Intent(this, CategoryActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        this.menu = menu
        menuInflater.inflate(R.menu.menu_sign_out, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.btnSignOut -> signOut()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun signOut() {
        val intent = Intent(this, MainActivity::class.java)
        DataProvider.actualUser = null
        finish()
        startActivity(intent)
    }
}