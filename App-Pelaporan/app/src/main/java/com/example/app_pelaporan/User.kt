package com.example.app_pelaporan

data class User(
    val id: String,
    val email: String,
    val nama: String,
    val alamat: String,
    val password: String,
    val role: String
){
    constructor():this("","","","","","")
}
