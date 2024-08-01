package com.aamg.undercontrol.data

import com.aamg.undercontrol.data.remote.model.AccountDto
import com.aamg.undercontrol.data.remote.model.CategoryDto
import com.aamg.undercontrol.data.remote.model.UserDto

object DataProvider {
    var currentUser: UserDto? = null

    var accounts: ArrayList<AccountDto>? = null
    var incomeCategories: ArrayList<CategoryDto>? = null
    var expenseCategories: ArrayList<CategoryDto>? = null
}