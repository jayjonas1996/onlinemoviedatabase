package com.example.jaynaik.onlinemoviedatabase

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.io.Console

class searchResults : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        var searchText = intent.getSerializableExtra("searchText") as String
        var item = intent.getSerializableExtra("item") as String

        val ref = FirebaseDatabase.getInstance().getReference("movies")

        val listView = findViewById<ListView>(R.id.movielist)

        var movieList: MutableList<movieShort>
        movieList         = mutableListOf()

        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot?){
                if(p0!!.exists())
                {
                    for(m in p0.children)
                    {
                        val mov = m.getValue(movieShort::class.java)
                        if(mov!!.title.contains(searchText,true))
                        {
                            print(mov)


                            var c = m.child("cast").children
                            var g = m.child("genre").children
                            var r = m.child("reviews").children

                            var  castList = arrayListOf<String>()
                            var  genreList = arrayListOf<String>()
                            var reviewList = arrayListOf<review>()

                            for(cast in c)
                            {
                                castList.add(cast.value.toString())
                            }
                            mov!!.casts = castList

                            for(genre in g)
                            {
                                genreList.add(genre.value.toString())
                            }
                            mov!!.genres = genreList

                            for(review in r)
                            {
                                val temp:review  =  com.example.jaynaik.onlinemoviedatabase.review(review.child("reviewtext").value.toString() ,review.child("name").value.toString(),review.child("rating").value.toString())
                                reviewList.add(temp)
                            }
                            mov.review = reviewList

                            if(item != "Any")
                            {
                                if(mov!!.genres.contains(item))
                                {
                                    movieList.add(mov!!)
                                }
                            }else
                            {
                                movieList.add(mov!!)
                            }


                        }
                    }

                }
            }
        })

        val movieadapter_object = movieAdapter(this,R.layout.movieshortlayout,movieList)
        listView.adapter = movieadapter_object

        listView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            val movie = movieList[position]
            val i = Intent(this,movie_details::class.java)
            i.putExtra("movie",movie)
            startActivity(i)
        }
    }
}
