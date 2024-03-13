package com.aamg.undercontrol.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.widget.addTextChangedListener
import com.aamg.undercontrol.DataProvider
import com.aamg.undercontrol.R
import com.aamg.undercontrol.databinding.ActivityEditCategoryBinding

class EditCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditCategoryBinding
    var category = Category("", null)
    private var isEdit = false
    private var position = DataProvider.categories.size

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.title = getString(R.string.add_category)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityEditCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("EXTRA_EDIT_INDEX")) {
            isEdit = true
            position = intent.getIntExtra("EXTRA_EDIT_INDEX", 0)
            supportActionBar?.title = getString(R.string.edit_category)
            category = DataProvider.categories[position]
            binding.etCategoryName.setText(category.name)
            when(category.type) {
                TransactionType.EXPENSE -> binding.rgCategoryType.check(binding.rbExpense.id)
                TransactionType.INCOME -> binding.rgCategoryType.check(binding.rbIncome.id)
                else -> {}
            }
            binding.btnSave.text = getString(R.string.edit_category)
        }

        binding.etCategoryName.addTextChangedListener { category.name = it.toString() }
        binding.rgCategoryType.setOnCheckedChangeListener { _, i ->
            category.type = when (i) {
                R.id.rbIncome -> TransactionType.INCOME
                R.id.rbExpense -> TransactionType.EXPENSE
                else -> null
            }
        }
        binding.btnSave.setOnClickListener { saveCategory() }
    }

    private fun saveCategory() {
        if (category.isValid()) {
            val resultIntent = Intent()
            var nameExtra = "EXTRA_NOTIFY_INSERT"
            val valueExtra: Int
            if (isEdit) {
                valueExtra = position
                nameExtra = "EXTRA_NOTIFY_CHANGE"
            } else {
                DataProvider.categories.add(category)
                valueExtra = DataProvider.categories.size - 1
            }
            resultIntent.putExtra(nameExtra, valueExtra)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> { onBackPressedDispatcher.onBackPressed() }
        }

        return super.onOptionsItemSelected(item)
    }
}
