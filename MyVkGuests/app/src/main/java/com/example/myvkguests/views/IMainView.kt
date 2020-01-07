package com.example.myvkguests.views

import com.example.myvkguests.models.AccountModel

interface IMainView {
    fun showError(textResource: Int)
    fun setupAccount(accountModel: AccountModel)
}