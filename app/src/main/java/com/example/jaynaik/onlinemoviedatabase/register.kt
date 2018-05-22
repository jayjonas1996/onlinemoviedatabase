package com.example.jaynaik.onlinemoviedatabase

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.widget.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text
import java.util.regex.Pattern

class register : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        title = "Register"


        val date = findViewById<NumberPicker>(R.id.pickDate)
        val month = findViewById<NumberPicker>(R.id.pickMonth)
        val year = findViewById<NumberPicker>(R.id.pickYear)

            date.maxValue = 31
            date.minValue = 1
            month.maxValue = 12
            month.minValue = 1
            year.maxValue = 2018
            year.minValue = 1920




        findViewById<Button>(R.id.btnRegister).setOnClickListener({
            val email = findViewById<EditText>(R.id.emailText)
            val password = findViewById<EditText>(R.id.passwordText)
            val name = findViewById<EditText>(R.id.nameText)

            if(email.text.isEmpty() || password.text.isEmpty() || name.text.isEmpty())
            {
                    Toast.makeText(applicationContext,"insufficient inputs",Toast.LENGTH_SHORT).show()

            }else if(false)//isEmailValid(email.text.toString())
            {
                Toast.makeText(applicationContext,"Invalid email",Toast.LENGTH_SHORT).show()

            }else if(password.text.length < 6)
            {
                Toast.makeText(applicationContext,"password too short",Toast.LENGTH_SHORT).show()
            }else
            {
                val user = user()
                //val name = findViewById<EditText>(R.id.nameText)
                val email = findViewById<EditText>(R.id.emailText)
                //val date = findViewById<NumberPicker>(R.id.pickDate)
                //val month = findViewById<NumberPicker>(R.id.pickMonth)
                //val year = findViewById<NumberPicker>(R.id.pickYear)

                user.name = name.text.toString()
                user.email = email.text.toString()
                user.dob = date.value.toString() +"/"+ month.value.toString()+"/"+year.value.toString()

                val auth = FirebaseAuth.getInstance()
                auth.createUserWithEmailAndPassword(email.text.toString(),password.text.toString()).addOnCompleteListener{ Task: Task<AuthResult> ->

                    if(Task.isSuccessful)
                    {

                        var uid = auth.currentUser!!.uid
                        user.uid = uid
                        val ref = FirebaseDatabase.getInstance().getReference("users/"+uid)
                        ref.setValue(user).addOnSuccessListener {
                            Toast.makeText(applicationContext,"SUCCESS you can login NOW",Toast.LENGTH_LONG).show()
                            val nav = findViewById<NavigationView>(R.id.nav_view)
                            //val head = nav.getHeaderView(0)
                              //  head.findViewById<TextView>(R.id.name).text = user.name
                                //
                            // head.findViewById<TextView>(R.id.textView).text = email.text
                            finish()
                        }

                    }

                }
            }

        })
    }

    fun isEmailValid(email: String): Boolean {
        return Pattern.compile(
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }

}
