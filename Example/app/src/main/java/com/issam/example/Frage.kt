package com.issam.example.com.issam.example

data class Frage(
    val id: String ,
    val title: String ,
    val frage: String ,
    val studiengang: String ,
    val timestamp: String?
) {

    constructor() : this("" , "" , "" , "" , "")
}