package com.example.app_pelaporan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.app_pelaporan.databinding.FragmentTambahBeritaBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class TambahBeritaFragment : Fragment(), View.OnClickListener {
    lateinit var binding: FragmentTambahBeritaBinding
    private lateinit var ref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTambahBeritaBinding.inflate(inflater, container, false)
        binding.imgUser.setOnClickListener{
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_adm,AkunAdminFragment())
            transaction.commit()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ref = FirebaseDatabase.getInstance().getReference("berita")
        binding.btnTambahberita.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        simpanData()
    }

    private fun simpanData() {
        val judul = binding.inputjudulberita.text.toString().trim()
        val tanggal = binding.inputtanggalberita.text.toString().trim()
        val deskripsi = binding.inputdeskripsiberita.text.toString().trim()

        if (judul.isEmpty() || tanggal.isEmpty() || deskripsi.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "Isi data secara lengkap tidak boleh kosong",
                Toast.LENGTH_SHORT
            ).show()
            return
        }

        val beritaId = ref.push().key
        val berita = Berita(beritaId!!,judul, tanggal, deskripsi)

        beritaId?.let {
            ref.child(it).setValue(berita).addOnCompleteListener { task ->
                if(isAdded) {
                    if (task.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Data berhasil ditambahkan",
                            Toast.LENGTH_SHORT
                        ).show()
                        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
                        transaction.replace(R.id.container_adm, BerandaAdminFragment())
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