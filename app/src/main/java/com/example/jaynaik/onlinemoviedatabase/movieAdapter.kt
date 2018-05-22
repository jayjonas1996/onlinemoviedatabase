package com.example.jaynaik.onlinemoviedatabase


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso


/**
 * Created by Jay Naik on 25-02-2018.
 */
class movieAdapter(val mCtx: Context,val layoutResid: Int,val movielist: List<movieShort>) : ArrayAdapter<movieShort>(mCtx,layoutResid,movielist){


    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(mCtx)
        val view : View = layoutInflater.inflate(layoutResid,null)


        val image_view = view.findViewById<ImageView>(R.id.image_view)
        val text_view = view.findViewById<TextView>(R.id.title_text)
        val detailsText = view.findViewById<TextView>(R.id.detailsText)
        val ratingText = view.findViewById<TextView>(R.id.ratingText)
        val movie = movielist[position]


        text_view.text = movie.title
        detailsText.text =  "(" +  movie.releasedate.split('/')[2] +")" + " | " + movie.duration.toString()+" mins"
        ratingText.text = "Rated: "+movie.rating.toString()+"/5"




        Picasso.with(mCtx).load(movie.poster_url).into(image_view)

        return view

    }
}