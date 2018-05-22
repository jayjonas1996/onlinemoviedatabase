package com.example.jaynaik.onlinemoviedatabase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class Activity_showReview : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_review)
        title = "Reviews"

        var movie = intent.getSerializableExtra("movie") as movieShort

        val reviews_listView =  findViewById<ListView>(R.id.showReviews_listView)

        val adap = reviewsAdapter(this,R.layout.reviews_layout,movie.review)

        reviews_listView.adapter = adap

    }
}
