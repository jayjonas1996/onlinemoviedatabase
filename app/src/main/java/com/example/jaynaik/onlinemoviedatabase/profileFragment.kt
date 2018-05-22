package com.example.jaynaik.onlinemoviedatabase

import android.content.Intent
import android.os.Bundle
import android.support.constraint.solver.widgets.Snapshot
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.nav_header_main.*

/**
 * Created by Jay Naik on 25-02-2018.
 */
class profileFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater?.inflate(R.layout.fragment_profile,null)
        activity.title = "Login"

        val auth =  FirebaseAuth.getInstance()








        view!!.findViewById<Button>(R.id.btnLogin).setOnClickListener({
            val emailBox = view.findViewById<EditText>(R.id.emailText)
            val passwordBox = view.findViewById<EditText>(R.id.passwordText)

            val email:String = emailBox.text.toString()
            val password = passwordBox.text.toString()

            var name = String()

            if(!email.isEmpty() && !password.isEmpty())
            {
                auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                       val uid =  auth.currentUser!!.uid
                    print(uid)
                    /*val user = FirebaseDatabase.getInstance().getReference("users/"+uid)
                    user.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(p0: DatabaseError?) {
                            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                        }
                        override fun onDataChange(p0: DataSnapshot?) {

                            if(p0!!.exists())
                            {
                                name = p0.child("name") as String

                            }
                        }
                    })*/
                    //val nav = view!!.findViewById<NavigationView>(R.id.nav_view)
                    //val head = nav.getHeaderView(0)
                    //head.findViewById<TextView>(R.id.name).text = name
                    //head.findViewById<TextView>(R.id.textView).text = auth.currentUser!!.email


                    val someFragment = user_home()
                    val transaction = fragmentManager.beginTransaction()
                    transaction.replace(this.id, someFragment) // give your fragment container id in first parameter
                    transaction.addToBackStack(null)  // if written, this transaction will be added to backstack
                    transaction.commit()

                }
            }
            else
            {
                Toast.makeText(view.context,"Insufficient input",Toast.LENGTH_SHORT).show()
            }



        })


        view!!.findViewById<Button>(R.id.btnRegister).setOnClickListener({
            val i = Intent(context,register::class.java)
            startActivity(i)
        })


        return view
    }
}