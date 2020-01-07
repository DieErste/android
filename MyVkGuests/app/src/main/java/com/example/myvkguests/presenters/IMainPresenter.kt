package com.example.myvkguests.presenters

import com.example.myvkguests.models.AccountModel

interface IMainPresenter {
    fun loadAccount()
    fun accountLoaded(account: AccountModel)
    fun showError(textResource: Int)
}