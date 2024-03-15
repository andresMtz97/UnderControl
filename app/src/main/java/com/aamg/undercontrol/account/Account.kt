package com.aamg.undercontrol.account

data class Account(
    var name: String
) {
    var balance: Double = 0.0

    fun isValid(): Boolean {
        var isValid = true
        if (this.name.isBlank()) {
            isValid = false
        }
        if (this.balance < 0.0) {
            isValid = false
        }

        return isValid
    }
}