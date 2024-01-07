package com.example.app_pelaporan

data class Berita(
    val id: String,
    val judul: String,
    val tanggal: String,
    val deskripsi: String,

){
    constructor():this("","","","")
}
