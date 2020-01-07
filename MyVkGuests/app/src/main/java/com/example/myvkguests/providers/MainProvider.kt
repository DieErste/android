package com.example.myvkguests.providers

import com.example.myvkguests.R
import com.example.myvkguests.models.AccountModel
import com.example.myvkguests.presenters.MainPresenter
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import com.vk.sdk.api.*

class MainProvider(var presenter: MainPresenter){

    fun loadAccount() {
        val request = VKApi.users().get(VKParameters.from(
            VKApiConst.FIELDS, "photo_200"
        ))
        request.setPreferredLang("ru")
        request.executeWithListener(object: VKRequest.VKRequestListener() {
            override fun onComplete(response: VKResponse) {
                super.onComplete(response)

                val jsonParser = JsonParser()
                val parsedJson = jsonParser.parse(response.json.toString()).asJsonObject
                val jsonArray = parsedJson.getAsJsonArray("response")
                val accountModel = AccountModel(
                    id = jsonArray[0].asJsonObject.get("id").asInt,
                    name = jsonArray[0].asJsonObject.get("first_name").asString,
                    surname = jsonArray[0].asJsonObject.get("last_name").asString,
                    avatar = jsonArray[0].asJsonObject.get("photo_200").asString
                )
                presenter.accountLoaded(accountModel = accountModel)

            }

            override fun onError(error: VKError?) {
                super.onError(error)
                presenter.showError(textResource = R.string.account_error_loading)
            }
        })
    }

}