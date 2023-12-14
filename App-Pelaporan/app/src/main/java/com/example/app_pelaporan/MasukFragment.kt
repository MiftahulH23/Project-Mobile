package com.example.app_pelaporan

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.app_pelaporan.databinding.FragmentMasukBinding


class MasukFragment : Fragment() {
    lateinit var binding: FragmentMasukBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMasukBinding.inflate(layoutInflater)
        binding.btnMasuk.setOnClickListener(){
            val i = Intent(context, MainActivity::class.java)
            startActivity(i)
        }
        binding.tvDaftar.setOnClickListener{
            val daftarFragment = DaftarFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.sp_container,daftarFragment)
            transaction.commit()
        }
        return binding.root
    }


}