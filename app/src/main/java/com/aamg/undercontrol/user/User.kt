package com.aamg.undercontrol.user

import com.aamg.undercontrol.account.Account
import com.aamg.undercontrol.category.Category

data class User(
    var firstName: String,
    var lastName: String,
    var userName: String,
    var password: String,
) {
    val categories: List<Category> get(): List<Category> = incomeCategories + expenseCategories
    val accounts: ArrayList<Account> = arrayListOf()
    val incomeCategories: ArrayList<Category> = arrayListOf()
    val expenseCategories: ArrayList<Category> = arrayListOf()
}
