package com.example.app_pelaporan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class SaranAdminAdapter(
    val saranContext: Context,
    val layoutResId: Int,
    val saranList: List<Saran>
) : ArrayAdapter<Saran>(saranContext, layoutResId, saranList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(saranContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val nama: TextView = view.findViewById(R.id.nama_saran_adm)
        val judul: TextView = view.findViewById(R.id.jdl_saran_adm)
        val tanggal: TextView = view.findViewById(R.id.tgl_saran_adm)
        val deskripsi: TextView = view.findViewById(R.id.desc_saran_adm)

        val saran = saranList[position]
        nama.text = saran.nama
        tanggal.text = saran.tanggal
        judul.text = saran.judul
        deskripsi.text = saran.deskripsi

        return view
    }


}