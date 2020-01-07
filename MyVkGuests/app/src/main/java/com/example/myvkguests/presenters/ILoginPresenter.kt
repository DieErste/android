package com.example.myvkguests.presenters

import android.content.Intent


interface ILoginPresenter {
    fun loginVk(requestCode: Int, resultCode: Int, data: Intent?): Boolean
    fun onLogin(isSuccess:Boolean)
}