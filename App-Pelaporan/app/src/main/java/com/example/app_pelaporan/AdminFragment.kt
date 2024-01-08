package com.example.app_pelaporan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.app_pelaporan.databinding.FragmentAdminBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class AdminFragment : Fragment() {
    lateinit var binding: FragmentAdminBinding
    private lateinit var userList: MutableList<User>
    private lateinit var ref: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAdminBinding.inflate(layoutInflater)
        binding.btnTambahuser.setOnClickListener {
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_adm, TambahAdminFragment())
            transaction.commit()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ref = FirebaseDatabase.getInstance().getReference("User")
        userList = mutableListOf()

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (isAdded) { // Pastikan Fragment terpasang sebelum menggunakan requireActivity()
                    if (snapshot.exists()) {
                        userList.clear()
                        for (a in snapshot.children) {
                            val user = a.getValue(User::class.java)
                            user?.let {
                                userList.add(it)
                            }
                        }
                        val adapter = UserAdapter(
                            requireActivity(),
                            R.layout.item_user,
                            userList
                        )
                        binding.lvOutput.adapter = adapter
                        println("Output: " + userList)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle onCancelled
            }
        })
    }


}