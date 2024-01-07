package com.example.app_pelaporan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.app_pelaporan.databinding.FragmentBeritaDetilBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class BeritaDetilFragment : Fragment() {
    lateinit var binding: FragmentBeritaDetilBinding
    private lateinit var ref: DatabaseReference
    private var isFragmentAttached = false
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBeritaDetilBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ref = FirebaseDatabase.getInstance().getReference("berita")
        val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
        isFragmentAttached = true

        displayUserInfo()

    }

    private fun displayUserInfo() {
        val beritaId = getArguments()?.getString("id");

        beritaId?.let {
            ref.child(it).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (isFragmentAttached) { // Pastikan fragment masih terpasang sebelum menggunakan konteks
                        if (snapshot.exists()) {
                            val email = snapshot.child("email").getValue(String::class.java)
                            val username = snapshot.child("nama").getValue(String::class.java)

                            val judul = snapshot.child("judul").getValue(String::class.java)
                            val tanggal = snapshot.child("tanggal").getValue(String::class.java)
                            val deskripsi = snapshot.child("deskripsi").getValue(String::class.java)

                            binding.detilJudul.setText(judul)
                            binding.detilTgl.setText(tanggal)
                            binding.detilDeskripsi.setText(deskripsi)
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }
}