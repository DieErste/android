package com.example.myvkguests.presenters

import android.content.Intent
import android.os.Handler
import com.example.myvkguests.R
import com.example.myvkguests.views.ILoginView
import com.vk.sdk.VKAccessToken
import com.vk.sdk.VKCallback
import com.vk.sdk.VKSdk
import com.vk.sdk.api.VKError

class LoginPresenter(internal var iLoginView: ILoginView):ILoginPresenter {

    override fun loginVk(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        if (!VKSdk.onActivityResult(requestCode, resultCode, data, object :
                VKCallback<VKAccessToken> {
                override fun onResult(res: VKAccessToken?) {
                    iLoginView.openApp()
                }

                override fun onError(error: VKError?) {
                    iLoginView.showError(textResource = R.string.login_error)
                }
            })) {
            return false
        }

        return true
    }

    //test
    override fun onLogin(isSuccess: Boolean) {
        iLoginView.startLoading()
        Handler().postDelayed({
            iLoginView.endLoading()
            if (isSuccess) {
                iLoginView.openApp()
            } else {
                iLoginView.showError(textResource = R.string.login_error)
            }
        }, 500)
    }

}