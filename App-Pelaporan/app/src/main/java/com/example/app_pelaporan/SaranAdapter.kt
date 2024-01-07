package com.example.app_pesaran

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
import com.example.app_pelaporan.R
import com.example.app_pelaporan.Saran
import com.google.firebase.database.FirebaseDatabase

class SaranAdapter(
    val saranContext: Context,
    val layoutResId: Int,
    val saranList: List<Saran>
) : ArrayAdapter<Saran>(saranContext, layoutResId, saranList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(saranContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val nama: TextView = view.findViewById(R.id.nama_saran)
        val judul: TextView = view.findViewById(R.id.jdl_saran)
        val tanggal: TextView = view.findViewById(R.id.tgl_saran)
        val deskripsi: TextView = view.findViewById(R.id.desc_saran)
        val imgEdit: ImageView = view.findViewById(R.id.ic_edit_saran)
        val imgHapus: ImageView = view.findViewById(R.id.ic_hapus_saran)

        val saran = saranList[position]
        nama.text = saran.nama
        tanggal.text = saran.tanggal
        judul.text = saran.judul
        deskripsi.text = saran.deskripsi


        imgEdit.setOnClickListener {
            updateDialog(saran)
        }

        imgHapus.setOnClickListener {
            val dbSaran = FirebaseDatabase.getInstance().getReference("saran")
                .child(saran.id)
            dbSaran.removeValue()

            Toast.makeText(saranContext, "Data berhasil dihapus", Toast.LENGTH_SHORT)
                .show()
        }
        return view
    }

    private fun updateDialog(saran: Saran) {
        val builder = AlertDialog.Builder(saranContext)
        builder.setTitle("Update Data")
        val inflater = LayoutInflater.from(saranContext)
        val view = inflater.inflate(R.layout.edit_saran_layout, null)

        val editNama = view.findViewById<EditText>(R.id.editnamasaran)
        val editJudul = view.findViewById<EditText>(R.id.editjudulsaran)
        val editTanggal = view.findViewById<EditText>(R.id.edittglsaran)
        val editDeskripsi = view.findViewById<EditText>(R.id.editdeskripsi)


        editNama.setText(saran.nama)
        editJudul.setText(saran.judul)
        editTanggal.setText(saran.tanggal)
        editDeskripsi.setText(saran.deskripsi)


        builder.setView(view)

        builder.setPositiveButton("Edit") { _, _ ->
            val dbSaran = FirebaseDatabase.getInstance().getReference("saran")
            val nama = editNama.text.toString().trim()
            val tanggal = editTanggal.text.toString().trim()
            val judul = editJudul.text.toString().trim()
            val deskripsi = editDeskripsi.text.toString().trim()

            if (nama.isEmpty() || judul.isEmpty() || tanggal.isEmpty() || deskripsi.isEmpty()) {
                Toast.makeText(saranContext, "Isi data secara lengkap tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            } else {
                // Proses update data
                val updatedSaran = Saran(saran.id, nama, judul, tanggal, deskripsi)
                dbSaran.child(saran.id).setValue(updatedSaran)
                Toast.makeText(saranContext, "Data Berhasil diupdate", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNeutralButton("Batal") { _, _ -> }
        val alerts = builder.create()
        alerts.show()
    }
}