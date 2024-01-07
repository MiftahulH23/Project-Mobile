package com.example.app_pelaporan

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.FirebaseDatabase


class TambahAdminFragment : Fragment(R.layout.fragment_tambah_admin) {
    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        // Inisialisasi elemen-elemen UI
        val namaEditText: EditText = view.findViewById(R.id.tambah_nama)
        val emailEditText: EditText = view.findViewById(R.id.tambah_email)
        val passwordEditText: EditText = view.findViewById(R.id.tambah_password)
        val alamatdEditText: EditText = view.findViewById(R.id.tambah_alamat)
        val btnTambah: Button = view.findViewById(R.id.btn_tambah)

        // Set listener untuk tombol daftar
        btnTambah.setOnClickListener {
            val nama = namaEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val alamat = alamatdEditText.text.toString().trim()

            // Validasi input sebelum pendaftaran
            if (nama.isEmpty() || email.isEmpty()  || password.isEmpty()|| alamat.isEmpty()) {
                Toast.makeText(requireContext(), "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Proses pendaftaran ke Firebase Authentication
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Pendaftaran berhasil
                        val user = auth.currentUser
                        val userId = user?.uid

                        // Menyimpan informasi pengguna ke Realtime Database
                        val database = FirebaseDatabase.getInstance()
                        val reference = database.getReference("user")

                        val userData = User(userId!!, email, nama, password,alamat, role= "admin")

                        userId?.let {
                            reference.child(it).setValue(userData)
                                .addOnSuccessListener {
                                    // Data pengguna berhasil disimpan ke database
                                    Toast.makeText(requireContext(), "user berhasil di tambah", Toast.LENGTH_SHORT).show()
                                    val transaction : FragmentTransaction = requireFragmentManager().beginTransaction()
                                    transaction.replace(R.id.container_adm, AdminFragment())
                                    transaction.commit()

                                }
                                .addOnFailureListener { e ->
                                    // Gagal menyimpan data pengguna ke database
                                    Toast.makeText(requireContext(), "Gagal menyimpan data: ${e.message}", Toast.LENGTH_SHORT).show()
                                }
                        }
                    } else {
                        // Pendaftaran gagal, tampilkan pesan kesalahan
                        val exception = task.exception
                        Toast.makeText(requireContext(), "Pendaftaran gagal: ${exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }

        }

    }
}