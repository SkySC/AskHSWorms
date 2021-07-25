package com.issam.example.com.issam.example.model

data class News(
    var ContenuNews: String ,
    var titleNews: String ,
    var mNewsPhoto: String ,
    var dateNews: String
) {

    constructor() : this("" , "" , "" , "")
}