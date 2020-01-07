package com.example.myvkguests.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myvkguests.R
import com.example.myvkguests.models.GuestModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class GuestsAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mSourceList: ArrayList<GuestModel> = ArrayList()
    private var mGuestsList: ArrayList<GuestModel> = ArrayList()

    fun setupGuests(guestsList: ArrayList<GuestModel>) {
        mSourceList.clear()
        mSourceList.addAll(guestsList)
        filter(query = "")
    }

    fun filter(query: String) {
        mGuestsList.clear()
        mSourceList.forEach({
            if (it.name.contains(query, ignoreCase = true) || it.surname.contains(query, ignoreCase = true)) {
                mGuestsList.add(it)
            } else {
                it.city?.let { city -> if (city.contains(query, ignoreCase = true)) {
                    mGuestsList.add(it)
                }}
            }
        })
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val itemView = layoutInflater.inflate(R.layout.cell_guest, parent, false)
        return GuestsViewHolder(itemView = itemView)
    }

    override fun getItemCount(): Int {
        return mGuestsList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GuestsViewHolder) {
            holder.bind(guestModel = mGuestsList[position])
        }
    }

    class GuestsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private var mCivAvatar: CircleImageView = itemView.findViewById(R.id.guest_civ_avatar)
        private var mTxtUsername: TextView = itemView.findViewById(R.id.guest_txt_username)
        private var mTxtCity: TextView = itemView.findViewById(R.id.guest_txt_city)
        private var mImgOnline: View = itemView.findViewById(R.id.guest_img_online)

        @SuppressLint("SetTextI18n")
        fun bind(guestModel: GuestModel) {
            guestModel.avatar?.let {
                url -> Picasso.with(itemView.context).load(url).into(mCivAvatar)
            }
            mTxtUsername.text = "${guestModel.name} ${guestModel.surname}"
            mTxtCity.text = itemView.context.getString(R.string.guests_no_city)
            guestModel.city?.let {
                city -> mTxtCity.text = city
            }
            if (guestModel.isOnline) {
                mImgOnline.visibility = View.VISIBLE
            } else {
                mImgOnline.visibility = View.GONE
            }
        }

    }

}