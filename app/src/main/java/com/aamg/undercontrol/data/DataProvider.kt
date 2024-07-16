package com.aamg.undercontrol.data

import com.aamg.undercontrol.data.remote.model.AccountDto
import com.aamg.undercontrol.data.remote.model.CategoryDto
import com.aamg.undercontrol.data.remote.model.UserDto

object DataProvider {
    var currentUser: UserDto? = null

    var accounts: ArrayList<AccountDto>? = null
    var incomeCategories: ArrayList<CategoryDto>? = null
    var expenseCategories: ArrayList<CategoryDto>? = null

    var accountsNames: ArrayList<String>? = null
    var incomeCategoriesNames: ArrayList<String>? = null
    var expenseCategoriesNames: ArrayList<String>? = null

}