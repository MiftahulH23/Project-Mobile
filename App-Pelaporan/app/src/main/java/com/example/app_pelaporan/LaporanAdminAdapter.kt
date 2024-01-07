package com.example.app_pelaporan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class LaporanAdminAdapter (
    val laporanContext: Context,
    val layoutResId: Int,
    val laporanList: List<Laporan>
) : ArrayAdapter<Laporan>(laporanContext, layoutResId, laporanList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(laporanContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val nama: TextView = view.findViewById(R.id.nama_pelapor_adm)
        val tanggal: TextView = view.findViewById(R.id.tgl_laporan_adm)
        val jenis: TextView = view.findViewById(R.id.jenis_infrastruktur_adm)
        val faktor: TextView = view.findViewById(R.id.faktor_kerusakan_adm)
        val kecamatan: TextView = view.findViewById(R.id.kecamatan_laporan_adm)
        val tingkatKerusakan: TextView = view.findViewById(R.id.tingkat_kerusakan_adm)
        val tingkatDampak: TextView = view.findViewById(R.id.tingkat_dampak_adm)

        val laporan = laporanList[position]
        nama.text = laporan.nama
        tanggal.text = laporan.tanggal
        jenis.text = laporan.jenisInfrastruktur
        faktor.text = laporan.faktorKerusakan
        kecamatan.text = laporan.namaKecamatan
        tingkatKerusakan.text = laporan.tingkatKerusakan.toString()
        tingkatDampak.text = laporan.tingkatDampak.toString()

        return view
    }


}