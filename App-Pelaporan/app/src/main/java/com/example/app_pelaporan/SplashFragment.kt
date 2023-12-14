package com.example.app_pelaporan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.app_pelaporan.databinding.FragmentSplashBinding

class SplashFragment : Fragment() {
    lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSplashBinding.inflate(layoutInflater)

        binding.btnDaftarsp.setOnClickListener{
            val daftarFragment = DaftarFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.sp_container,daftarFragment)
            transaction.commit()
        }

        binding.btnMasuksp.setOnClickListener{
            val daftarFragment = MasukFragment()
            val transaction: FragmentTransaction = requireFragmentManager().beginTransaction()
            transaction.replace(R.id.sp_container,daftarFragment)
            transaction.commit()
        }
        return binding.root
    }

}