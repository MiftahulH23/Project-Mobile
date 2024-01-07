package com.example.app_pelaporan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.app_pelaporan.databinding.FragmentBerandaBinding


class BerandaFragment : Fragment() {
    lateinit var binding: FragmentBerandaBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBerandaBinding.inflate(layoutInflater)
        binding.btnLaporkan.setOnClickListener{
            val tambahLaporan = TambahLaporanFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container,tambahLaporan)
            transaction.commit()
        }
        binding.btnBerikansaran.setOnClickListener{
            val tambahSaran = TambahSaranFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container,tambahSaran)
            transaction.commit()
        }
        return binding.root
    }
}