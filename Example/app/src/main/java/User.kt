package com.issam.example

data class User(
    val fullname: String , val email: String , val tel: String , val password: String ,
    val userPower: String , val profilBild: String
) {

    constructor() : this("" , "" , "" , "" , "" , "")
}