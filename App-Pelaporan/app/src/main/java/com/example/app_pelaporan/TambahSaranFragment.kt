package com.example.app_pelaporan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.app_pelaporan.databinding.FragmentTambahSaranBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TambahSaranFragment : Fragment(), View.OnClickListener {

    lateinit var binding: FragmentTambahSaranBinding
    private lateinit var ref: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTambahSaranBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ref = FirebaseDatabase.getInstance().getReference("saran")
        binding.btnTambahsaran.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        simpanData()
    }

    private fun simpanData() {
        val nama = binding.inputnamasaran.text.toString().trim()
        val judul = binding.inputjudulsaran.text.toString().trim()
        val tanggal = binding.inputtglsaran.text.toString().trim()
        val deskripsi = binding.inputdeskripsi.text.toString().trim()

        if (nama.isEmpty() || judul.isEmpty() || tanggal.isEmpty() || deskripsi.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Isi data secara lengkap tidak boleh kosong",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val saranId = ref.push().key
        val saran = Saran(saranId!!, nama, judul, tanggal, deskripsi)

        saranId?.let {
            ref.child(it).setValue(saran).addOnCompleteListener { task ->
                if(isAdded) {
                    if (task.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Data berhasil ditambahkan",
                            Toast.LENGTH_SHORT
                        ).show()
                        val halamanSaran = SaranFragment()
                        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
                        transaction.replace(R.id.container, halamanSaran)
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