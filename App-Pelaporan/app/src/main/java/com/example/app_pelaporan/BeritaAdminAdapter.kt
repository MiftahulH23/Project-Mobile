package com.example.app_pelaporan

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class BeritaAdminAdapter(
    val beritaContext: Context,
    val layoutResId: Int,
    val beritaList: List<Berita>
) : ArrayAdapter<Berita>(beritaContext, layoutResId, beritaList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(beritaContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val judul: TextView = view.findViewById(R.id.jdl_berita)
        val tanggal: TextView = view.findViewById(R.id.tgl_berita)
        val deskripsi: TextView = view.findViewById(R.id.desc_berita)
        val imgEdit: ImageView = view.findViewById(R.id.ic_edit_berita)
        val imgHapus: ImageView = view.findViewById(R.id.ic_hapus_berita)

        val berita = beritaList[position]
        tanggal.text = berita.tanggal
        judul.text = berita.judul
        deskripsi.text = berita.deskripsi


        imgEdit.setOnClickListener {
            updateDialog(berita)
        }

        imgHapus.setOnClickListener {
            val dbBerita = FirebaseDatabase.getInstance().getReference("berita")
                .child(berita.id)
            dbBerita.removeValue()

            Toast.makeText(beritaContext, "Data berhasil dihapus", Toast.LENGTH_SHORT)
                .show()
        }
        return view
    }

    private fun updateDialog(berita: Berita) {
        val builder = AlertDialog.Builder(beritaContext)
        builder.setTitle("Update Data")
        val inflater = LayoutInflater.from(beritaContext)
        val view = inflater.inflate(R.layout.edit_berita_layout, null)

        val editJudul = view.findViewById<EditText>(R.id.editjudulberita)
        val editTanggal = view.findViewById<EditText>(R.id.edittglberita)
        val editDeskripsi = view.findViewById<EditText>(R.id.editdeskripsiberita)


        editJudul.setText(berita.judul)
        editTanggal.setText(berita.tanggal)
        editDeskripsi.setText(berita.deskripsi)


        builder.setView(view)

        builder.setPositiveButton("Edit") { _, _ ->
            val dbBerita = FirebaseDatabase.getInstance().getReference("berita")
            val tanggal = editTanggal.text.toString().trim()
            val judul = editJudul.text.toString().trim()
            val deskripsi = editDeskripsi.text.toString().trim()

            if (judul.isEmpty() || tanggal.isEmpty() || deskripsi.isEmpty()) {
                Toast.makeText(beritaContext, "Isi data secara lengkap tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // Proses update data
                val updatedBerita = Berita(berita.id,  judul, tanggal, deskripsi)
                dbBerita.child(berita.id).setValue(updatedBerita)
                Toast.makeText(beritaContext, "Data Berhasil diupdate", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNeutralButton("Batal") { _, _ -> }
        val alerts = builder.create()
        alerts.show()
    }
}