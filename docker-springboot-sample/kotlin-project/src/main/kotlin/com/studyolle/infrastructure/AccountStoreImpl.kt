package com.studyolle.infrastructure

import com.studyolle.domain.Account
import com.studyolle.domain.account.AccountCommand
import com.studyolle.domain.account.AccountStore
import org.springframework.stereotype.Component

@Component
class AccountStoreImpl(
    private val accountRepository: AccountRepository
): AccountStore {
    override fun saveNewAccount(account: Account): Account {
        return accountRepository.save(account)
    }

    override fun getAccountByEmail(email: String): Account? {
        return accountRepository.findByEmail(email)
    }
}