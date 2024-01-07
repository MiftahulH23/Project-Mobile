package com.example.app_pelaporan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.app_pelaporan.databinding.FragmentBerandaAdminBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class BerandaAdminFragment : Fragment() {
    lateinit var binding: FragmentBerandaAdminBinding
    private lateinit var beritaList: MutableList<Berita>
    private lateinit var ref: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBerandaAdminBinding.inflate(layoutInflater)
        binding.btnCeklaporan.setOnClickListener{
            val cekLaporan = LaporanAdminFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_adm,cekLaporan)
            transaction.commit()
        }
        binding.imgUser.setOnClickListener{
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_adm,AkunFragment())
            transaction.commit()
        }
        binding.btnCeksaran.setOnClickListener{
            val cekSaran = SaranAdminFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_adm,cekSaran)
            transaction.commit()
        }
        binding.btnTambahberita.setOnClickListener {
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_adm,TambahBeritaFragment())
            transaction.commit()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ref = FirebaseDatabase.getInstance().getReference("berita")
        beritaList = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (isAdded) {
                    if (snapshot.exists()) {
                        beritaList.clear()
                        for (a in snapshot.children) {
                            val berita = a.getValue(Berita::class.java)
                            berita?.let {
                                beritaList.add(it)
                            }
                        }
                        val adapter = BeritaAdminAdapter(
                            requireActivity(),
                            R.layout.item_berita_admin_layout,
                            beritaList
                        )
                        binding.lvOutput.adapter = adapter
                        println("Output: " + beritaList)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })
    }
}