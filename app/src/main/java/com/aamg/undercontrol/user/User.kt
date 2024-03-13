package com.aamg.undercontrol.user

import com.aamg.undercontrol.account.Account
import com.aamg.undercontrol.category.Category

data class User(
    var firstName: String,
    var lastName: String,
    var userName: String,
    var password: String,
) {
    val categories: ArrayList<Category> = arrayListOf()
    val accounts: ArrayList<Account> = arrayListOf()
}
