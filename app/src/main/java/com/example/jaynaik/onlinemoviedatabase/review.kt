package com.example.jaynaik.onlinemoviedatabase

import java.io.Serializable

/**
 * Created by Jay Naik on 10-03-2018.
 */
class review: Serializable {
    var reviewtext = String()
    var name = String()
    var rating = String()
    constructor()
    constructor(text:String,name:String,rating:String)
    {
        this.reviewtext = text
        this.name = name
        this.rating = rating

    }
}