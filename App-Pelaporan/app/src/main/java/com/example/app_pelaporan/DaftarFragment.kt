package com.example.app_pelaporan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.app_pelaporan.databinding.FragmentDaftarBinding


class DaftarFragment : Fragment() {
    lateinit var binding: FragmentDaftarBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDaftarBinding.inflate(layoutInflater)
        binding.tvMasuk.setOnClickListener(){
            val masukFragment = MasukFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.sp_container,masukFragment)
            transaction.commit()
        }
        return binding.root
    }
}