package com.aamg.undercontrol.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.aamg.undercontrol.data.local.DataProvider
import com.aamg.undercontrol.R
import com.aamg.undercontrol.data.local.model.Account
import com.aamg.undercontrol.databinding.ActivityEditAccountBinding

class EditAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditAccountBinding
    var account = Account("")
    private var isEdit = false
    private var position = DataProvider.actualUser!!.accounts.size

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.add_account)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityEditAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("EXTRA_EDIT_INDEX")) {
            isEdit = true
            position = intent.getIntExtra("EXTRA_EDIT_INDEX", 0)
            supportActionBar?.title = getString(R.string.edit_account)
            account = DataProvider.actualUser!!.accounts[position]
            binding.etAccountName.setText(account.name)
            binding.etAccountBalance.setText(account.balance.toString())
        }

        initUI()
    }

    private fun initUI() {
//        initIconSelector()
        initListeners()
    }

//    private fun initIconSelector() {
//        binding.etAccountIcon.setCompoundDrawablesWithIntrinsicBounds(null, null, AppCompatResources.getDrawable(this, R.drawable.selector_account_image), null)
//    }

    private fun initListeners() {
        binding.btnSaveAccount.setOnClickListener { saveAccount() }
        binding.etAccountName.addTextChangedListener { account.name = it.toString() }
        binding.etAccountBalance.addTextChangedListener { account.balance = it.toString().toDouble() }
    }

    private fun saveAccount() {
        if (account.isValid()) {
            val resultIntent = Intent()
            var nameExtra = "EXTRA_NOTIFY_INSERT"
            val valueExtra: Int
            if (isEdit) {
                valueExtra = position
                nameExtra = "EXTRA_NOTIFY_CHANGE"
            } else {
                DataProvider.actualUser!!.accounts.add(account)
                valueExtra = DataProvider.actualUser!!.accounts.size - 1
            }
            resultIntent.putExtra(nameExtra, valueExtra)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}