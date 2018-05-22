package com.example.jaynaik.onlinemoviedatabase

import android.app.Fragment
import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toolbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.miguelcatalan.materialsearchview.MaterialSearchView

 class  MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<android.support.v7.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        val nav = findViewById<NavigationView>(R.id.nav_view)
        val head =   nav.inflateHeaderView(R.layout.nav_header_main)
        val auth = FirebaseAuth.getInstance()
        if(auth.currentUser != null)
        {


            head.findViewById<TextView>(R.id.name).text = "Welcome"
            head.findViewById<TextView>(R.id.textView).text = auth.currentUser!!.email.toString()
        }
        else
        {
            head.findViewById<TextView>(R.id.name).text = "Welcome"
            head.findViewById<TextView>(R.id.textView).text = "You are not logged in"
        }


        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/




        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
        displayScreen(-1)
    }

   /* override fun onDrawerOpened(drawerView: View?) {
        val nav = findViewById<NavigationView>(R.id.nav_view)
        val head =   nav.inflateHeaderView(R.layout.nav_header_main)
        val auth = FirebaseAuth.getInstance()
        if(auth.currentUser != null)
        {


            head.findViewById<TextView>(R.id.name).text = "Welcome"
            head.findViewById<TextView>(R.id.textView).text = auth.currentUser!!.email.toString()
        }
        else
        {
            head.findViewById<TextView>(R.id.name).text = "Welcome"
            head.findViewById<TextView>(R.id.textView).text = "You are not logged in"
        }
    }*/

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.


       // val view = layoutInflater.inflate(R.layout.fragment_home,null)
/*
        menuInflater.inflate(R.menu.main, menu)
        var item:MenuItem = menu!!.findItem(R.id.action_search)
        homeFragment().initiate()
        homeFragment().searchview.setMenuItem(item)*/
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
       /* when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }*/
       return true
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        if(id == R.id.search)
        {
            val i = Intent(this,search::class.java)
            startActivity(i)
        }
        else(
                displayScreen(item.itemId)
                )




        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
    fun displayScreen(id: Int){
       val fragment =  when(id){
            R.id.nav_home -> {
                    homeFragment()
            }
            R.id.profile -> {
                    val auth = FirebaseAuth.getInstance()
                if(auth.currentUser != null)
                {
                    user_home()
                }else
                {
                    profileFragment()
                }
            }/*
           R.id.search_button ->
           {
               HomePage()
           }*/
            else -> {
                homeFragment()
            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.RelativeLayout, fragment).commit()
    }
}
