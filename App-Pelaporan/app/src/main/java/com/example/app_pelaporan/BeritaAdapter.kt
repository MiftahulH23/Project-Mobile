package com.example.app_pelaporan

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction

class BeritaAdapter(
    val beritaContext: Context,
    val layoutResId: Int,
    val beritaList: List<Berita>
) : ArrayAdapter<Berita>(beritaContext, layoutResId, beritaList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(beritaContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val judul: TextView = view.findViewById(R.id.jdl_berita_adm)
        val tanggal: TextView = view.findViewById(R.id.tgl_berita_adm)
        val deskripsi: TextView = view.findViewById(R.id.desc_berita_adm)
        val cardBerita: ConstraintLayout = view.findViewById(R.id.cardBerita)

        val berita = beritaList[position]
        tanggal.text = berita.tanggal
        judul.text = berita.judul
        deskripsi.text = berita.deskripsi

        cardBerita.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("id", berita.id)

            val detilFragment = BeritaDetilFragment()
            detilFragment.arguments = bundle

            val fragmentManager = (beritaContext as FragmentActivity).supportFragmentManager
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()

            transaction.replace(R.id.container, detilFragment)
            transaction.addToBackStack(null)  // Untuk menambahkan ke tumpukan kembali
            transaction.commit()
        }

        return view
    }

}