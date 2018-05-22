package com.example.jaynaik.onlinemoviedatabase

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.util.zip.Inflater

class movie_details : AppCompatActivity() {

   override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_details)
        title = "Movie"

        val trailerImg = findViewById<ImageButton>(R.id.trailerImg)
        val posterImg = findViewById<ImageView>(R.id.posterImg)
        val titleText = findViewById<TextView>(R.id.titleText)
        val rat_durText = findViewById<TextView>(R.id.rat_durText)
        val genreText = findViewById<TextView>(R.id.genreText)
        val descriptionText = findViewById<TextView>(R.id.descriptionText)
        val castText = findViewById<TextView>(R.id.castText)

        val languageText = findViewById<TextView>(R.id.language_Text)
        val pgratingText = findViewById<TextView>(R.id.pgrating_text)
        val directorText = findViewById<TextView>(R.id.director_Text)
        val releasedateText = findViewById<TextView>(R.id.releasedate_Text)
        val grossText = findViewById<TextView>(R.id.gross_Text)
        val budgetText = findViewById<TextView>(R.id.budget_Text)



        val reviewBtn = findViewById<Button>(R.id.writeReview)
        val showReview_btn = findViewById<Button>(R.id.showReview)
       // val reviewListview = findViewById<ListView>(R.id.reviewsListview)

        var i=0;
        var movie = intent.getSerializableExtra("movie") as movieShort



        val user = FirebaseDatabase.getInstance().getReference("users/"+FirebaseAuth.getInstance().currentUser?.uid)
        var name = String()
        user.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
            override fun onDataChange(p0: DataSnapshot?) {

                if(p0!!.exists())
                {
                    for(m in p0.children)
                    {
                        if(m.key == "name")
                        {
                            name = m.value.toString()
                        }
                    }

                }
            }
        })


        Picasso.with(applicationContext).load(movie.poster_url).into(posterImg)
        var lis = movie.trailer_url.split('/')
        var url = Uri.parse("https://img.youtube.com/vi/"+ lis[3].toString() +"/maxresdefault.jpg")
        //https://img.youtube.com/vi/<insert-youtube-video-id-here>/maxresdefault.jpg
        Picasso.with(applicationContext).load(url).into(trailerImg)


        titleText.text = movie.title + " ("+ movie.releasedate.split('/')[2].toString() + ")"
        rat_durText.text = "rated: "+movie.rating.toString() + "/"+ "5  |  "+ movie.duration.toString() + " mins"

        languageText.text = movie.language
        pgratingText.text = movie.pgrating
        directorText.text = movie.director
        releasedateText.text = movie.releasedate
        grossText.text = "USD "+movie.gross
        budgetText.text = "USD "+movie.budget

        descriptionText.text = movie.description

        for(cast in movie.casts)
        {
            castText.text = castText.text.toString() + cast + "\n"
        }
        for(genre in movie.genres)
        {
            genreText.text = genreText.text.toString() + genre + " |"
        }
        genreText.text = genreText.text.removeSuffix("|")

        reviewBtn.setOnClickListener({
            val auth  = FirebaseAuth.getInstance()

            if(auth.currentUser?.uid == null )
            {
                Toast.makeText(applicationContext,"You're not logged in",Toast.LENGTH_LONG).show()
            }
            else
            {
                val builder = AlertDialog.Builder(this)
                    builder.setTitle("Write a review")
                val inflater = LayoutInflater.from(this)
                val view = inflater.inflate(R.layout.layout_dialog_review,null)

                val reviewText = view.findViewById<EditText>(R.id.reviewText)
                var ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)
                builder.setView(view)

                builder.setPositiveButton("Post"){ p0,p1 ->
                    val ref = FirebaseDatabase.getInstance().getReference("movies/"+movie.id+"/reviews/"+auth.currentUser!!.uid)
                    val user = name

                    val newReview = review(reviewText.text.toString(),user,ratingBar.rating.toString())

                    if(!reviewText.text.isEmpty())
                    {
                        ref.setValue(newReview).addOnCompleteListener {
                            Toast.makeText(applicationContext,"Posted",Toast.LENGTH_SHORT).show()
                        }
                    }


                }
                builder.setNegativeButton("Cancel"){p0,p1 ->

                }

                val alert = builder.create()
                alert.show()
            }
        })

        showReview_btn.setOnClickListener({


            val i = Intent(this,Activity_showReview::class.java)
            i.putExtra("movie",movie)
            startActivity(i)

        })

        trailerImg.setOnClickListener({

            val i = Intent(this,Trailer::class.java)
            i.putExtra("link",movie.trailer_url)
            startActivity(i)
        })


    }



}
