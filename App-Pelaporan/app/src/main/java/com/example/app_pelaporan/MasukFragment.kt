package com.example.app_pelaporan

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class MasukFragment : Fragment(R.layout.fragment_masuk) {

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth

        // Inisialisasi elemen-elemen UI
        val telpEditText: EditText = view.findViewById(R.id.ms_telp)
        val passwordEditText: EditText = view.findViewById(R.id.ms_password)
        val btnMasuk: Button = view.findViewById(R.id.btn_masuk)
        val tvDaftar: TextView = view.findViewById(R.id.tv_daftar)

        // Set listener untuk tombol masuk
        btnMasuk.setOnClickListener {
            val telp = telpEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            // Validasi input sebelum masuk
            if (telp.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Harap isi semua kolom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Proses masuk dengan Firebase Authentication
            // ...
            auth.signInWithEmailAndPassword(telp, password)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // ...
                        val user = auth.currentUser
                        val userId = user?.uid

                        val database = FirebaseDatabase.getInstance()
                        val reference = database.getReference("User")

                        userId?.let {
                            reference.child(it).get().addOnSuccessListener { snapshot ->
                                val role = snapshot.child("role").value.toString()

                                if (role == "admin") {
                                    // Redirect ke MainActivity jika rolenya adalah admin
                                    val intent = Intent(requireContext(), MainActivity::class.java)
                                    startActivity(intent)
                                    requireActivity().finish() // Tutup activity saat ini agar tidak kembali ke halaman masuk
                                } else {
                                    // Redirect ke SplashActivity jika rolenya bukan admin
                                    val intent = Intent(requireContext(), SplashActivity::class.java)
                                    startActivity(intent)
                                    requireActivity().finish() // Tutup activity saat ini agar tidak kembali ke halaman masuk
                                }
                            }.addOnFailureListener { e ->
                                // Gagal mendapatkan informasi role pengguna
                                Toast.makeText(requireContext(), "Gagal: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        // Masuk gagal, tampilkan pesan kesalahan
                        val exception = task.exception
                        if (exception is FirebaseAuthInvalidUserException || exception is FirebaseAuthInvalidCredentialsException) {
                            // Pesan untuk email atau password salah
                            Toast.makeText(requireContext(), "Email atau password salah", Toast.LENGTH_SHORT).show()
                        } else {
                            // Pesan kesalahan lainnya
                            Toast.makeText(requireContext(), "Masuk gagal: ${exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

        }

        // Listener untuk tombol daftar (redirect ke halaman daftar)
        tvDaftar.setOnClickListener {
            // Redirect ke halaman daftar jika tombol daftar ditekan
            // Misalnya:
            // val intent = Intent(requireContext(), DaftarActivity::class.java)
            // startActivity(intent)
        }
    }
}

