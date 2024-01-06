package com.example.app_pelaporan

data class Laporan(
    val id: String,
    val nama: String,
    val jenisInfrastruktur: String,
    val namaKecamatan: String,
    val faktorKerusakan: String,
    val tanggal: String,
    val tingkatKerusakan: Int,
    val tingkatDampak: Int,
){
    constructor():this("","","","","","",0,0)
}
