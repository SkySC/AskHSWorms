package com.issam.example.model

data class Frage(
    val id: String ,
    val title: String ,
    val frage: String ,
    val studiengang: String ,
    val timestamp: String?
) {

    constructor() : this("" , "" , "" , "" , "")
}