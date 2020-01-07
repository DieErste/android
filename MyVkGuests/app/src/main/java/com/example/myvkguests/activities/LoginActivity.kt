package com.example.myvkguests.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myvkguests.R
import com.example.myvkguests.presenters.ILoginPresenter
import com.example.myvkguests.presenters.LoginPresenter
import com.example.myvkguests.views.ILoginView
import com.github.rahatarmanahmed.cpv.CircularProgressView
import com.vk.sdk.VKScope
import com.vk.sdk.VKSdk

class LoginActivity : AppCompatActivity(), ILoginView {

    internal lateinit var loginPresenter: ILoginPresenter

    private lateinit var mBtnLogin: Button
    private lateinit var mCpvWait: CircularProgressView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginPresenter = LoginPresenter(this)

        mBtnLogin = findViewById(R.id.btn_login_enter)
        mCpvWait = findViewById(R.id.cpv_login)

        //
        mBtnLogin.setOnClickListener({
            //loginPresenter.onLogin(isSuccess = true)
            VKSdk.login(this@LoginActivity, VKScope.FRIENDS)
        })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (!loginPresenter.loginVk(requestCode = requestCode, resultCode = resultCode, data = data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun startLoading() {
        mBtnLogin.visibility = View.GONE
        mCpvWait.visibility = View.VISIBLE
    }

    override fun endLoading() {
        mBtnLogin.visibility = View.VISIBLE
        mCpvWait.visibility = View.GONE
    }

    override fun showError(textResource: Int) {
        Toast.makeText(applicationContext, getString(textResource), Toast.LENGTH_SHORT).show()
    }

    override fun openApp() {
        startActivity(Intent(applicationContext, MainActivity::class.java))
    }
}
