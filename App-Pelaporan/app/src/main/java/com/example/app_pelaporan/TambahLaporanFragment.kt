package com.example.app_pelaporan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.app_pelaporan.databinding.FragmentTambahLaporanBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TambahLaporanFragment : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentTambahLaporanBinding
    private lateinit var ref: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTambahLaporanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ref = FirebaseDatabase.getInstance().getReference("laporan")
        binding.btnTambahlpr.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        simpanData()
    }

    private fun simpanData() {
        val nama = binding.inputnamapelapor.text.toString().trim()
        val jenisInfrastruktur = binding.inputjenisinfrastruktur.text.toString().trim()
        val namaKecamatan = binding.inputnamakecamatan.text.toString().trim()
        val faktorKerusakan = binding.inputfaktor.text.toString().trim()
        val tanggal = binding.inputtanggal.text.toString().trim()

        val tingkatKerusakanText = binding.inputtingkatkerusakan.text.toString()
        val tingkatDampakText = binding.inputtingkatdampak.text.toString()

        if (nama.isEmpty() || jenisInfrastruktur.isEmpty() || namaKecamatan.isEmpty() || faktorKerusakan.isEmpty() || tanggal.isEmpty() || tingkatKerusakanText.isEmpty() || tingkatDampakText.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Isi data secara lengkap tidak boleh kosong",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        // Konversi tingkatKerusakan dan tingkatDampak ke dalam tipe data Int
        val tingkatKerusakan = tingkatKerusakanText.toIntOrNull()
        val tingkatDampak = tingkatDampakText.toIntOrNull()

        // Periksa jika tingkatKerusakan atau tingkatDampak null (gagal konversi)
        if (tingkatKerusakan == null || tingkatDampak == null) {
            Toast.makeText(
                requireContext(),
                "Tingkat Kerusakan dan Tingkat Dampak harus berupa angka",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        if (tingkatKerusakan !in 1..7 || tingkatDampak !in 1..7) {
            Toast.makeText(
                requireContext(),
                "Tingkat Kerusakan dan Tingkat Dampak harus di antara 1 sampai 7",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val laporanId = ref.push().key
        val loker = Laporan(laporanId!!, nama, jenisInfrastruktur, namaKecamatan, faktorKerusakan, tanggal, tingkatKerusakan, tingkatDampak)

        laporanId?.let {
            ref.child(it).setValue(loker).addOnCompleteListener { task ->
                if(isAdded) {
                    if (task.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Data berhasil ditambahkan",
                            Toast.LENGTH_SHORT
                        ).show()
                        val halamanLaporan = LaporanFragment()
                        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
                        transaction.replace(R.id.container, halamanLaporan)
                        transaction.commit()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Gagal menambahkan data",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }
}