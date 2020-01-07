package com.example.myvkguests.views

import com.example.myvkguests.models.GuestModel

interface IGuestsView {
    fun showError(textResource: Int)
    fun setupEmptyList()
    fun setupGuestsList(guestsList: ArrayList<GuestModel>)
    fun startLoading()
    fun endLoading()
}