package com.aamg.undercontrol

import com.aamg.undercontrol.category.Category
import com.aamg.undercontrol.user.User

class DataProvider {
    companion object {
        val users = mutableMapOf<String, User>(
            Pair(
                "andresmtz",
                User("Andrés", "Martínez", "andresmtz", "iopiop890123")
            )
        )
        var actualUser: User? = null
        val categories = arrayListOf<Category>()
    }
}