package com.example.jaynaik.onlinemoviedatabase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

/**
 * Created by Jay Naik on 10-03-2018.
 */
class reviewsAdapter(val mCtx: Context, val layoutResid: Int, val reviewlist: List<review>) : ArrayAdapter<review>(mCtx,layoutResid,reviewlist) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view : View = layoutInflater.inflate(layoutResid,null)
        //val view_last = layoutInflater.inflate(R.layout.layout_view_all_reviews,null)

        val reviewer = view.findViewById<TextView>(R.id.reviewerName)
        val reviewText = view.findViewById<TextView>(R.id.reviewText)
        val rating_text = view.findViewById<TextView>(R.id.rating_text)

        reviewer.text = "By "+reviewlist[position].name
        reviewText.text = reviewlist[position].reviewtext
        rating_text.text = reviewlist[position].rating

        /*if(position == 3)
        {
            return view_last
        }*/












        return view

    }
}