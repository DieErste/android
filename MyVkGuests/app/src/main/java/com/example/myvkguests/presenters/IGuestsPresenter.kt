package com.example.myvkguests.presenters

import com.example.myvkguests.models.GuestModel

interface IGuestsPresenter {
    fun loadGuests()
    fun guestsLoaded(guestsList: ArrayList<GuestModel>)
    fun showError(textResource: Int)
}