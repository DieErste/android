package com.example.myvkguests.views

interface ILoginView {
    fun startLoading()
    fun endLoading()
    fun showError(textResource: Int)
    fun openApp()
}