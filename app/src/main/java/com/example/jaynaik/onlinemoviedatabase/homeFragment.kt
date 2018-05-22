package com.example.jaynaik.onlinemoviedatabase

import android.app.Application
import android.content.Intent
import android.graphics.Movie
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.AdapterView
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.nav_header_main.view.*
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toolbar
import com.miguelcatalan.materialsearchview.MaterialSearchView
import kotlinx.android.synthetic.main.app_bar_main.*


/**
 * Created by Jay Naik on 25-02-2018.
 */
class homeFragment : Fragment() {

    lateinit var ref: DatabaseReference
    lateinit var movieList: MutableList<movieShort>
    lateinit var listView : ListView
    lateinit var searchview:MaterialSearchView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
           /* val auth = FirebaseAuth.getInstance()
            if(!auth.currentUser?.uid?.isEmpty()!!)
            {
                val head:View  = NavigationView.inflate(context,R.layout.nav_header_main,null)



                head.name.text = "Welcome"
                head.textView.text = auth.currentUser!!.email.toString()
            }*/



            ref = FirebaseDatabase.getInstance().getReference("movies")
            movieList = mutableListOf()
            val view = inflater?.inflate(R.layout.fragment_home,null)
            activity.setTitle("Movies")

            var toolbar = view!!.findViewById<Toolbar>(R.id.toolbar)
        activity.setActionBar(toolbar)


            searchview = view!!.findViewById<MaterialSearchView>(R.id.search_view)

        listView = view!!.findViewById(R.id.listView1)
            listView.isClickable = true

            ref.addValueEventListener(object : ValueEventListener
            {
                override fun onCancelled(p0: DatabaseError?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

                override fun onDataChange(p0: DataSnapshot?)
                {
                movieList.clear()
                if(p0!!.exists())
                {
                    for(m in p0.children)
                    {
                        val mov = m.getValue(movieShort::class.java)
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
                        movieList.add(mov!!)
                    }
                    val movieadapter_object = movieAdapter(context,R.layout.movieshortlayout,movieList)
                    listView.adapter = movieadapter_object
                }
                }
            })

        listView.onItemClickListener = OnItemClickListener { parent, view, position, id ->

            val movie = movieList[position]
            val i = Intent(context,movie_details::class.java)
            i.putExtra("movie",movie)
            startActivity(i)
        }



            return view

    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.main,menu)
        var item:MenuItem = menu!!.findItem(R.id.action_search)
        searchview.setMenuItem(item)
        return
    }
    public fun initiate() {

        val view = layoutInflater.inflate(R.layout.fragment_home,null)
        searchview = view!!.findViewById<MaterialSearchView>(R.id.search_view)
        return
    }
}