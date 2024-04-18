package com.aamg.undercontrol.data.local.model

data class Category(
    var name: String,
    var type: TransactionType?
) {

    fun isValid(): Boolean {
        var isValid = true
        if (this.name.isBlank()) {
            isValid = false
        }
        if (this.type == null) {
            isValid = false
        }

        return isValid
    }
}

enum class TransactionType {
    INCOME,
    EXPENSE
}