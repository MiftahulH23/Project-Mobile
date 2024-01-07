package com.example.app_pelaporan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.app_pelaporan.databinding.FragmentBerandaAdminBinding


class BerandaAdminFragment : Fragment() {
    lateinit var binding: FragmentBerandaAdminBinding
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
        binding.btnCeksaran.setOnClickListener{
            val cekSaran = SaranAdminFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container_adm,cekSaran)
            transaction.commit()
        }
        return binding.root
    }
}