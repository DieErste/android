package com.example.myvkguests.presenters

import com.example.myvkguests.R
import com.example.myvkguests.models.GuestModel
import com.example.myvkguests.providers.GuestsProvider
import com.example.myvkguests.views.IGuestsView

class GuestsPresenter(internal var iGuestsView: IGuestsView): IGuestsPresenter {
    override fun loadGuests() {
        iGuestsView.startLoading()
        //GuestsProvider(presenter = this).testLoadGuests(hasGuest = true)
        GuestsProvider(presenter = this).loadGuests()
    }

    override fun guestsLoaded(guestsList: ArrayList<GuestModel>) {
        iGuestsView.endLoading()
        if (guestsList.size == 0) {
            iGuestsView.setupEmptyList()
            iGuestsView.showError(R.string.guests_no_guests)
        } else {
            iGuestsView.setupGuestsList(guestsList = guestsList)
        }
    }

    override fun showError(textResource: Int) {
        iGuestsView.showError(textResource = textResource)
    }

}