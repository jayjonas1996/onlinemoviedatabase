package com.example.jaynaik.onlinemoviedatabase

import android.support.annotation.Keep
import java.io.Serializable

/**
 * Created by Jay Naik on 25-02-2018.
 */
class movieShort : Serializable {
    var title = String()
    var poster_url = String()
    var releasedate = String()

    var id = String()
    var duration = String()
    var rating = String()
    var description = String()
    var casts = arrayListOf<String>()
    var genres = arrayListOf<String>()
    var review = arrayListOf(review())
    var trailer_url = String()

    var budget = String()
    var gross = String()
    var director = String()
    var pgrating = String()
    var language = String()



    constructor(name: String,url: String, date:String)
    {
        this.title = name
        this.poster_url = url
        this.releasedate = date




    }

    constructor(){

    }
}