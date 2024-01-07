package com.example.app_pelaporan

data class Saran(
    val id: String,
    val nama: String,
    val judul: String,
    val tanggal: String,
    val deskripsi: String,

){
    constructor():this("","","","","")
}
