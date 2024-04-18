package com.aamg.undercontrol.data.local

import com.aamg.undercontrol.data.local.model.Category
import com.aamg.undercontrol.data.local.model.User

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