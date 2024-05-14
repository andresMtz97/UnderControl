package com.aamg.undercontrol.ui.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.aamg.undercontrol.data.local.DataProvider
import com.aamg.undercontrol.R
import com.aamg.undercontrol.ui.view.adapters.account.AccountAdapter
import com.aamg.undercontrol.databinding.ActivityAccountBinding

class AccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountBinding
    private var menu: Menu? = null
    private lateinit var adapter: AccountAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    private fun initUI() {
        initActionBar()
        initRecyclerView()
//        initListeners()
    }

    private fun initActionBar() {
        supportActionBar?.title = getString(R.string.accounts_text)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initRecyclerView() {
        adapter = AccountAdapter(
            DataProvider.actualUser!!.accounts,
            { index -> onItemDeleted(index) },
            { index -> onItemEdited(index) }
        )
        binding.rvAccounts.layoutManager = LinearLayoutManager(this)
        binding.rvAccounts.adapter = adapter
    }

    private fun onItemEdited(index: Int) {
        val intent = Intent(this, EditAccountActivity::class.java).apply {
            putExtra("EXTRA_EDIT_INDEX", index)
        }
        launchActivityResult.launch(intent)
    }

    private fun onItemDeleted(index: Int) {
        DataProvider.actualUser!!.accounts.removeAt(index)
        adapter.notifyItemRemoved(index)
    }

    private fun initListeners() {

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        this.menu = menu
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when(item.itemId) {
//            android.R.id.home -> { onBackPressed() }
//            R.id.btnAdd -> {
//                val intent = Intent(this, EditAccountActivity::class.java)
//                launchActivityResult.launch(intent)
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

    private val launchActivityResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {result ->
        if (result.resultCode == RESULT_OK) {
            if (result.data?.hasExtra("EXTRA_NOTIFY_INSERT") == true) {
                result.data?.getIntExtra(
                    "EXTRA_NOTIFY_INSERT",
                    DataProvider.actualUser!!.accounts.size -1
                )?.let { adapter.notifyItemInserted(it) }
            }
            if (result.data?.hasExtra("EXTRA_NOTIFY_CHANGE") == true) {
                result.data?.getIntExtra("EXTRA_NOTIFY_CHANGE", 0)
                    ?.let { adapter.notifyItemChanged(it) }
            }
        }
    }
}