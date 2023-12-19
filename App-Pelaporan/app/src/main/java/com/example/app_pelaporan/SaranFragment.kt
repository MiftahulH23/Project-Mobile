package com.example.app_pelaporan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.app_pelaporan.databinding.FragmentSaranBinding


class SaranFragment : Fragment() {
    lateinit var binding: FragmentSaranBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSaranBinding.inflate(layoutInflater)
        binding.btnTambah.setOnClickListener{
            val tambahSaran = TambahSaranFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container,tambahSaran)
            transaction.commit()
        }
        return binding.root
    }
}