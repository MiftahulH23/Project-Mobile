package com.example.app_pelaporan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.app_pelaporan.databinding.FragmentLaporanBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LaporanFragment : Fragment() {
    private lateinit var binding: FragmentLaporanBinding
    private lateinit var laporanList: MutableList<Laporan>
    private lateinit var ref: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLaporanBinding.inflate(inflater, container, false)
        binding.btntambahlaporan.setOnClickListener{
            val tambahFragmen = TambahLaporanFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container,tambahFragmen)
            transaction.commit()
        }
        binding.imgUser.setOnClickListener{
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container,AkunFragment())
            transaction.commit()
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ref = FirebaseDatabase.getInstance().getReference("laporan")
        laporanList = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (isAdded) {
                    if (snapshot.exists()) {
                        laporanList.clear()
                        for (a in snapshot.children) {
                            val laporan = a.getValue(Laporan::class.java)
                            laporan?.let {
                                laporanList.add(it)
                            }
                        }
                        val adapter = LaporanAdapter(
                            requireActivity(),
                            R.layout.item_laporan_layout,
                            laporanList
                        )
                        binding.lvOutput.adapter = adapter
                        println("Output: " + laporanList)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })
    }

}