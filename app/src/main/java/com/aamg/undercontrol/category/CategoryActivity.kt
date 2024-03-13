package com.aamg.undercontrol.category

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.aamg.undercontrol.DataProvider
import com.aamg.undercontrol.R
import com.aamg.undercontrol.category.adapter.CategoryAdapter
import com.aamg.undercontrol.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoryBinding
    private var menu: Menu? = null
    private lateinit var adapter: CategoryAdapter

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
        adapter = CategoryAdapter(
            DataProvider.categories,
            { index -> onItemDeleted(index) },
            { index -> onItemEdited(index) }
        )
        binding.rvCategories.layoutManager = LinearLayoutManager(this)
        binding.rvCategories.adapter = adapter
    }

    private fun onItemEdited(index: Int) {
        val intent = Intent(this, EditCategoryActivity::class.java).apply {
            putExtra("EXTRA_EDIT_INDEX", index)
        }
        launchActivityResult.launch(intent)
    }

    private fun onItemDeleted(index: Int) {
        DataProvider.categories.removeAt(index)
        adapter.notifyItemRemoved(index)
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
            if (result.data?.hasExtra("EXTRA_NOTIFY_INSERT") == true) {
                result.data?.getIntExtra(
                    "EXTRA_NOTIFY_INSERT",
                    DataProvider.categories.size -1
                )?.let { adapter.notifyItemInserted(it) }
            }
            if (result.data?.hasExtra("EXTRA_NOTIFY_CHANGE") == true) {
                result.data?.getIntExtra("EXTRA_NOTIFY_CHANGE", 0)
                    ?.let { adapter.notifyItemChanged(it) }
            }
        }
    }
}