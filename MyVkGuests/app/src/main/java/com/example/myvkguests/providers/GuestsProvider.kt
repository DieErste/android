package com.example.myvkguests.providers

import android.os.Handler
import com.example.myvkguests.R
import com.example.myvkguests.models.GuestModel
import com.example.myvkguests.presenters.GuestsPresenter
import com.google.gson.JsonParser
import com.vk.sdk.api.*

class GuestsProvider(var presenter: GuestsPresenter) {

    fun loadGuests() {
        val request = VKApi.friends().get(VKParameters.from(
            /*VKApiConst.COUNT, 300, */
            VKApiConst.FIELDS, "sex, bdate, city, country, photo_100, online"
        ))
        request.setPreferredLang("ru")
        request.executeWithListener(object: VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse) {
                super.onComplete(response)

                val jsonParser = JsonParser()
                val parsedJson = jsonParser.parse(response.json.toString()).asJsonObject
                val guestsList: ArrayList<GuestModel> = ArrayList()

                parsedJson.get("response").asJsonObject.getAsJsonArray("items").forEach {
                    val city = if (it.asJsonObject.get("city") == null) {
                        null
                    } else {
                        it.asJsonObject.get("city").asJsonObject.get("title").asString
                    }

                    val guest = GuestModel(
                        name = it.asJsonObject.get("first_name").asString,
                        surname = it.asJsonObject.get("last_name").asString,
                        city = city,
                        avatar = it.asJsonObject.get("photo_100").asString,
                        isOnline = it.asJsonObject.get("online").asInt == 1)
                    guestsList.add(guest)
                }

                presenter.guestsLoaded(guestsList = guestsList)
            }

            override fun onError(error: VKError?) {
                super.onError(error)
                presenter.showError(textResource = R.string.guests_error_loading)
            }
        })
    }

    fun testLoadGuests(hasGuest: Boolean) {

        Handler().postDelayed({
            val guestsList: ArrayList<GuestModel> = ArrayList()
            if (hasGuest) {
                val guest1 = GuestModel(
                    name = "Ivan",
                    surname = "Petrov",
                    city = null,
                    avatar = "https://upload.wikimedia.org/wikipedia/ru/8/86/Иван_Иванович_Петров_%28певец%29.jpg",
                    isOnline = true
                    )
                val guest2 = GuestModel(
                    name = "Alexey",
                    surname = "Gladkov",
                    city = "Tomsk",
                    avatar = "https://pp.userapi.com/c837723/v837723005/5eca5/T7p2k_hYvqw.jpg",
                    isOnline = false
                )
                val guest3 = GuestModel(
                    name = "Egor",
                    surname = "Sidorov",
                    city = "Moscow",
                    avatar = "https://www.nihilist.li/wp-content/uploads/2019/06/Naruto_Part_1-800x600.png",
                    isOnline = false
                )
                guestsList.add(guest1)
                guestsList.add(guest2)
                guestsList.add(guest3)
            }
            presenter.guestsLoaded(guestsList = guestsList)
        }, 2000)

    }

}