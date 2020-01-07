package com.example.myvkguests.ui.guests

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.myvkguests.R
import com.example.myvkguests.adapters.GuestsAdapter
import com.example.myvkguests.models.GuestModel
import com.example.myvkguests.presenters.GuestsPresenter
import com.example.myvkguests.presenters.IGuestsPresenter
import com.example.myvkguests.views.IGuestsView
import com.github.rahatarmanahmed.cpv.CircularProgressView

class GuestsFragment : Fragment(), IGuestsView {

    internal lateinit var guestsPresenter: IGuestsPresenter

    private lateinit var mEdtSearch: EditText
    private lateinit var mTxtNoGuests: TextView
    private lateinit var mCpvGuests: CircularProgressView
    private lateinit var mRvGuests: RecyclerView
    private lateinit var mAdapter: GuestsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_guests, container, false)

        mEdtSearch = root.findViewById(R.id.edt_guests_search)
        mTxtNoGuests = root.findViewById(R.id.txt_no_guests)
        mCpvGuests = root.findViewById(R.id.cpv_guests)
        mRvGuests = root.findViewById(R.id.recycler_guests)

        mEdtSearch.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mAdapter.filter(s.toString())
            }

        })

        guestsPresenter = GuestsPresenter(iGuestsView = this)
        guestsPresenter.loadGuests()

        mAdapter = GuestsAdapter()
        mRvGuests.adapter = mAdapter
        mRvGuests.layoutManager = LinearLayoutManager(root.context,
            RecyclerView.VERTICAL, false)

        return root
    }

    override fun showError(textResource: Int) {
        mTxtNoGuests.text = getString(textResource)
    }

    override fun setupEmptyList() {
        mRvGuests.visibility = View.GONE
        mTxtNoGuests.visibility = View.VISIBLE
    }

    override fun setupGuestsList(guestsList: ArrayList<GuestModel>) {
        mRvGuests.visibility = View.VISIBLE
        mTxtNoGuests.visibility = View.GONE

        mAdapter.setupGuests(guestsList = guestsList)
    }

    override fun startLoading() {
        mRvGuests.visibility = View.GONE
        mTxtNoGuests.visibility = View.GONE
        mCpvGuests.visibility = View.VISIBLE
    }

    override fun endLoading() {
        mCpvGuests.visibility = View.GONE
    }
}