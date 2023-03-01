package com.studyolle.domain.account

import com.studyolle.domain.Account

interface AccountStore {
    fun saveNewAccount(account: Account): Account
    fun getAccountByEmail(email: String): Account?
}