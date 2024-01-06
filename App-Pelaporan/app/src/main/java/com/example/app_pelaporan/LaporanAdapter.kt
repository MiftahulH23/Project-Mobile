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

class LaporanAdapter(
    val laporanContext: Context,
    val layoutResId: Int,
    val laporanList: List<Laporan>
) : ArrayAdapter<Laporan>(laporanContext, layoutResId, laporanList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layoutInflater: LayoutInflater = LayoutInflater.from(laporanContext)
        val view: View = layoutInflater.inflate(layoutResId, null)

        val nama: TextView = view.findViewById(R.id.nama_pelapor)
        val tanggal: TextView = view.findViewById(R.id.tgl_laporan)
        val jenis: TextView = view.findViewById(R.id.jenis_infrastruktur)
        val faktor: TextView = view.findViewById(R.id.faktor_kerusakan)
        val kecamatan: TextView = view.findViewById(R.id.kecamatan_laporan)
        val tingkatKerusakan: TextView = view.findViewById(R.id.tingkat_kerusakan)
        val tingkatDampak: TextView = view.findViewById(R.id.tingkat_dampak)
        val imgEdit: ImageView = view.findViewById(R.id.ic_edit_laporan)
        val imgHapus: ImageView = view.findViewById(R.id.ic_hapus_laporan)

        val laporan = laporanList[position]
        nama.text = laporan.nama
        tanggal.text = laporan.tanggal
        jenis.text = laporan.jenisInfrastruktur
        faktor.text = laporan.faktorKerusakan
        kecamatan.text = laporan.namaKecamatan
        tingkatKerusakan.text = laporan.tingkatKerusakan.toString()
        tingkatDampak.text = laporan.tingkatDampak.toString()

        imgEdit.setOnClickListener {
            updateDialog(laporan)
        }

        imgHapus.setOnClickListener {
            val dbLaporan = FirebaseDatabase.getInstance().getReference("laporan")
                .child(laporan.id)
            dbLaporan.removeValue()

            Toast.makeText(laporanContext, "Data berhasil dihapus", Toast.LENGTH_SHORT)
                .show()
        }
        return view
    }

    private fun updateDialog(laporan: Laporan) {
        val builder = AlertDialog.Builder(laporanContext)
        builder.setTitle("Update Data")
        val inflater = LayoutInflater.from(laporanContext)
        val view = inflater.inflate(R.layout.edit_laporan_layout, null)

        val editNama = view.findViewById<EditText>(R.id.editnamapelapor)
        val editTanggal = view.findViewById<EditText>(R.id.edittanggallaporan)
        val editJenis = view.findViewById<EditText>(R.id.editjenisinfrastruktur)
        val editFaktor = view.findViewById<EditText>(R.id.editfaktor)
        val editKecamatan = view.findViewById<EditText>(R.id.editnamakecamatan)
        val editTingkatKerusakan = view.findViewById<EditText>(R.id.edittingkatkerusakan)
        val editTingkatDampak = view.findViewById<EditText>(R.id.edittingkatdampak)

        editNama.setText(laporan.nama)
        editTanggal.setText(laporan.tanggal)
        editJenis.setText(laporan.jenisInfrastruktur)
        editFaktor.setText(laporan.faktorKerusakan)
        editKecamatan.setText(laporan.namaKecamatan)
        editTingkatKerusakan.setText(laporan.tingkatKerusakan.toString())
        editTingkatDampak.setText(laporan.tingkatDampak.toString())

        builder.setView(view)

        builder.setPositiveButton("Edit") { _, _ ->
            val dbLaporan = FirebaseDatabase.getInstance().getReference("laporan")
            val nama = editNama.text.toString().trim()
            val tanggal = editTanggal.text.toString().trim()
            val jenis = editJenis.text.toString().trim()
            val faktor = editFaktor.text.toString().trim()
            val kecamatan = editKecamatan.text.toString().trim()
            val tingkaKerusakanText = editTingkatKerusakan.text.toString().trim()
            val tingkaDampakText = editTingkatDampak.text.toString().trim()
            val tingkatKerusakan = tingkaKerusakanText.toIntOrNull()
            val tingkatDampak = tingkaDampakText.toIntOrNull()

            if (nama.isEmpty() || jenis.isEmpty() || tanggal.isEmpty() || faktor.isEmpty() || kecamatan.isEmpty()) {
                Toast.makeText(laporanContext, "Isi data secara lengkap tidak boleh kosong", Toast.LENGTH_SHORT)
                    .show()
            } else if (tingkaKerusakanText.isBlank() || tingkaDampakText.isBlank() || tingkatKerusakan == null || tingkatDampak == null) {
                Toast.makeText(laporanContext, "Isi data dengan angka secara lengkap", Toast.LENGTH_SHORT).show()
            } else {
                // Proses update data
                val updatedLaporan = Laporan(laporan.id, nama, tanggal, jenis, faktor, kecamatan, tingkatKerusakan, tingkatDampak)
                dbLaporan.child(laporan.id).setValue(updatedLaporan)
                Toast.makeText(laporanContext, "Data Berhasil diupdate", Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNeutralButton("Batal") { _, _ -> }
        val alerts = builder.create()
        alerts.show()
    }
}

