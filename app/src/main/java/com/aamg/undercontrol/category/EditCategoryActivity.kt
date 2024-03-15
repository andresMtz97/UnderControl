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
    private var position: Int = 0
    private var initialType: TransactionType? = null

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
            category = DataProvider.actualUser!!.categories[position]
            initialType = category.type
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
            if (isEdit) {
                if (category.type != initialType) {
                    if (category.type == TransactionType.INCOME) {
                        DataProvider.actualUser!!.expenseCategories.removeAt(position)
                        DataProvider.actualUser!!.incomeCategories.add(category)
                        resultIntent.putExtra("EXTRA_NOTIFY_DELETE_EXPENSE", position)
                        resultIntent.putExtra("EXTRA_NOTIFY_INSERT_INCOME", position)
                    } else {
                        DataProvider.actualUser!!.incomeCategories.removeAt(position)
                        DataProvider.actualUser!!.expenseCategories.add(category)
                        resultIntent.putExtra("EXTRA_NOTIFY_DELETE_INCOME", position)
                        resultIntent.putExtra("EXTRA_NOTIFY_INSERT_EXPENSE", position)
                    }
                } else {
                    if (category.type == TransactionType.INCOME) {
                        resultIntent.putExtra("EXTRA_NOTIFY_CHANGE_INCOME", position)
                    } else {
                        resultIntent.putExtra("EXTRA_NOTIFY_CHANGE_EXPENSE", position)
                    }
                }
            } else {
                if (category.type == TransactionType.INCOME) {
                    DataProvider.actualUser!!.incomeCategories.add(category)
                    resultIntent.putExtra(
                        "EXTRA_NOTIFY_INSERT_INCOME",
                        DataProvider.actualUser!!.incomeCategories.size - 1
                    )
                } else {
                    DataProvider.actualUser!!.expenseCategories.add(category)
                    resultIntent.putExtra(
                        "EXTRA_NOTIFY_INSERT_EXPENSE",
                        DataProvider.actualUser!!.expenseCategories.size - 1
                    )
                }
            }
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
