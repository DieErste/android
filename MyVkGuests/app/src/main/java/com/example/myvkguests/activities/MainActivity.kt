package com.example.myvkguests.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myvkguests.R
import com.example.myvkguests.models.AccountModel
import com.example.myvkguests.presenters.MainPresenter
import com.example.myvkguests.views.IMainView
import com.google.android.material.navigation.NavigationView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class MainActivity : AppCompatActivity(), IMainView {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navView: NavigationView
    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // menu

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // slide menu layout
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        // slide menu items
        navView = findViewById(R.id.nav_view)
        // main fragment window
        val navController = findNavController(R.id.nav_host_fragment)
        // set menu items
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_guests
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        mainPresenter = MainPresenter(iMainView = this)
        mainPresenter.loadAccount()
    }

    // menu create
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        //menuInflater.inflate(R.menu.main, menu)
        return true
    }

    // menu actions
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun showError(textResource: Int) {
        Toast.makeText(applicationContext, getString(textResource), Toast.LENGTH_SHORT).show()
    }

    override fun setupAccount(accountModel: AccountModel) {

        val mCivAvatar: CircleImageView = navView.findViewById(R.id.account_civ_avatar)
        val mTxtUsername: TextView = navView.findViewById(R.id.account_txt_username)
        accountModel.avatar?.let {
                url -> Picasso.with(navView.context).load(url).into(mCivAvatar)
        }
        mTxtUsername.text = accountModel.name + " " + accountModel.surname
    }
}
