package com.example.jaynaik.onlinemoviedatabase

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class search : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)



        val searchButton = findViewById<Button>(R.id.searchButton)
        val searchText = findViewById<EditText>(R.id.searchText)
        val spinner = findViewById<Spinner>(R.id.spinner)

        val array = arrayOf("Any","Action","Adventure","Sci-fi","Fantasy","Comedy","Romance","Documentry")
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,array)
        spinner.adapter = adapter




        searchButton.setOnClickListener({
            if(!searchText.text.isEmpty()){
            val item = spinner.selectedItem.toString()
            val i = Intent(this, searchResults::class.java)
            i.putExtra("searchText", searchText.text.toString())
            i.putExtra("item", item)
            startActivity(i)
        }
            else{
                Toast.makeText(this,"search field can't be empty",Toast.LENGTH_SHORT).show()
            }
        })
    }
}
