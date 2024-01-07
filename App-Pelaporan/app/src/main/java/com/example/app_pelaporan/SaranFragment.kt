package com.example.app_pelaporan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.app_pelaporan.databinding.FragmentSaranBinding
import com.example.app_pesaran.SaranAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class SaranFragment : Fragment() {
    lateinit var binding: FragmentSaranBinding
    private lateinit var saranList: MutableList<Saran>
    private lateinit var ref: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSaranBinding.inflate(layoutInflater)
        binding.btntambahsaran.setOnClickListener{
            val tambahSaran = TambahSaranFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container,tambahSaran)
            transaction.commit()
        }
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ref = FirebaseDatabase.getInstance().getReference("saran")
        saranList = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (isAdded) {
                    if (snapshot.exists()) {
                        saranList.clear()
                        for (a in snapshot.children) {
                            val laporan = a.getValue(Saran::class.java)
                            laporan?.let {
                                saranList.add(it)
                            }
                        }
                        val adapter = SaranAdapter(
                            requireActivity(),
                            R.layout.item_saran_layout,
                            saranList
                        )
                        binding.lvOutput.adapter = adapter
                        println("Output: " + saranList)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })
    }
}