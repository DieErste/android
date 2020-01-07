package com.example.myvkguests.presenters

import com.example.myvkguests.models.AccountModel
import com.example.myvkguests.providers.MainProvider
import com.example.myvkguests.views.IMainView

class MainPresenter(internal var iMainView: IMainView): IMainPresenter {
    override fun loadAccount() {
        MainProvider(presenter = this).loadAccount()
    }

    override fun accountLoaded(accountModel: AccountModel) {
        iMainView.setupAccount(accountModel = accountModel)
    }

    override fun showError(textResource: Int) {
        iMainView.showError(textResource = textResource)
    }
}