package com.example.jaynaik.onlinemoviedatabase

import android.app.Application
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

/**
 * Created by Jay Naik on 26-02-2018.
 */
class user_home: Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        activity.title = "Welcome"

       val view =   inflater?.inflate(R.layout.user_home,null)
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        val user = FirebaseDatabase.getInstance().getReference("users/"+uid)
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
                            view!!.findViewById<TextView>(R.id.userName).text = m.value.toString()
                        }
                    }

                }
            }
        })









        view!!.findViewById<Button>(R.id.btnLogout).setOnClickListener({
            val auth = FirebaseAuth.getInstance()
            auth.signOut()

            Toast.makeText(context,"You are logged out", Toast.LENGTH_SHORT).show()


           // val nav = view!!.findViewById<NavigationView>(R.id.nav_view)
           // val head = nav.getHeaderView(0)
            //head.findViewById<TextView>(R.id.name).text = "Welcome"
            //head.findViewById<TextView>(R.id.textView).text = "You are not logged in"

            val someFragment = profileFragment()
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(this.id, someFragment) // give your fragment container id in first parameter
            transaction.addToBackStack(null)  // if written, this transaction will be added to backstack
            transaction.commit()


        })
        return  view





    }
}