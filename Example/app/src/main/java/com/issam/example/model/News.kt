package com.issam.example.model

data class News(
    var ContentNews: String ,
    var titleNews: String ,
    var mNewsPhoto: String ,
    var dateNews: String
) {

    constructor() : this("" , "" , "" , "")
}