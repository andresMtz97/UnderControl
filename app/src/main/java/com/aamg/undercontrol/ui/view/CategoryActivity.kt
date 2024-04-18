package com.aamg.undercontrol.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.aamg.undercontrol.data.local.DataProvider
import com.aamg.undercontrol.R
import com.aamg.undercontrol.ui.view.adapters.category.CategoryAdapter
import com.aamg.undercontrol.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private var menu: Menu? = null
    private lateinit var incomeAdapter: CategoryAdapter
    private lateinit var expenseAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = getString(R.string.categories_text)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()



//        supportFragmentManager.beginTransaction()
//            .add(binding.fragmentCategories.id, CategoriesFragment.newInstance())
//            .commit()
    }

    private fun initRecyclerView() {
        incomeAdapter = CategoryAdapter(
            DataProvider.actualUser!!.incomeCategories,
            { index -> onItemIncomeDeleted(index) },
            { index -> onItemEdited(index) }
        )
        binding.rvIncomeCategories.layoutManager = LinearLayoutManager(this)
        binding.rvIncomeCategories.adapter = incomeAdapter

        expenseAdapter = CategoryAdapter(
            DataProvider.actualUser!!.expenseCategories,
            { index -> onItemExpenseDeleted(index) },
            { index -> onItemEdited(index) }
        )
        binding.rvExpenseCategories.layoutManager = LinearLayoutManager(this)
        binding.rvExpenseCategories.adapter = expenseAdapter
    }

    private fun onItemEdited(index: Int) {
        val intent = Intent(this, EditCategoryActivity::class.java).apply {
            putExtra("EXTRA_EDIT_INDEX", index)
        }
        launchActivityResult.launch(intent)
    }

    private fun onItemIncomeDeleted(index: Int) {
        DataProvider.actualUser!!.incomeCategories.removeAt(index)
        incomeAdapter.notifyItemRemoved(index)
    }

    private fun onItemExpenseDeleted(index: Int) {
        DataProvider.actualUser!!.expenseCategories.removeAt(index)
        expenseAdapter.notifyItemRemoved(index)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
//                supportActionBar?.title = getString(R.string.categories_text)
//                menu?.findItem(R.id.btnAdd)?.isVisible = true

                finish()
            }
            R.id.btnAdd -> {
//                supportActionBar?.title = getString(R.string.add_category)
//                menu?.findItem(R.id.btnAdd)?.isVisible = false
//
//                supportFragmentManager.beginTransaction()
//                    .replace(binding.fragmentCategories.id, AddCategoryFragment.newInstance())
//                    .commit()
                val intent = Intent(this, EditCategoryActivity::class.java)
                launchActivityResult.launch(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val launchActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            if (result.data?.hasExtra("EXTRA_NOTIFY_INSERT_INCOME") == true) {
                result.data?.getIntExtra(
                    "EXTRA_NOTIFY_INSERT_INCOME",
                    DataProvider.actualUser!!.incomeCategories.size -1
                )?.let { incomeAdapter.notifyItemInserted(it) }
            }
            if (result.data?.hasExtra("EXTRA_NOTIFY_INSERT_EXPENSE") == true) {
                result.data?.getIntExtra(
                    "EXTRA_NOTIFY_INSERT_EXPENSE",
                    DataProvider.actualUser!!.expenseCategories.size -1
                )?.let { expenseAdapter.notifyItemInserted(it) }
            }
            if (result.data?.hasExtra("EXTRA_NOTIFY_DELETE_INCOME") == true){
                result.data?.getIntExtra("EXTRA_NOTIFY_DELETE_INCOME", 0)
                    ?.let { incomeAdapter.notifyItemRemoved(it) }
            }
            if (result.data?.hasExtra("EXTRA_NOTIFY_DELETE_EXPENSE") == true){
                result.data?.getIntExtra("EXTRA_NOTIFY_DELETE_EXPENSE", 0)
                    ?.let { expenseAdapter.notifyItemRemoved(it) }
            }
            if (result.data?.hasExtra("EXTRA_NOTIFY_CHANGE_INCOME") == true) {
                result.data?.getIntExtra("EXTRA_NOTIFY_CHANGE_INCOME", 0)
                    ?.let { incomeAdapter.notifyItemChanged(it) }
            }
            if (result.data?.hasExtra("EXTRA_NOTIFY_CHANGE_EXPENSE") == true) {
                result.data?.getIntExtra("EXTRA_NOTIFY_CHANGE_EXPENSE", 0)
                    ?.let { expenseAdapter.notifyItemChanged(it) }
            }
        }
    }
}