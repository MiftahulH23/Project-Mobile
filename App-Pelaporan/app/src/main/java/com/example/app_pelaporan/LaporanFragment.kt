package com.example.app_pelaporan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.app_pelaporan.databinding.FragmentLaporanBinding
class LaporanFragment : Fragment() {
    private lateinit var binding: FragmentLaporanBinding
    lateinit var tambahlaporan : TambahLaporanFragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLaporanBinding.inflate(layoutInflater)
        binding.btntambahlaporan.setOnClickListener{
            val tambahFragmen = TambahLaporanFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.container,tambahFragmen)
            transaction.commit()
        }
        return binding.root
    }




}