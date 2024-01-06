package com.example.app_pelaporan

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.app_pelaporan.databinding.ActivityMainAdminBinding

class MainActivityAdmin : AppCompatActivity() {
    lateinit var binding : ActivityMainAdminBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainAdminBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeFragment(BerandaAdminFragment())
        binding.navbarAdm.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.tab_home_admin -> changeFragment(BerandaAdminFragment())
                R.id.tab_saran_admin -> changeFragment(SaranAdminFragment())
                R.id.tab_laporan_admin -> changeFragment(LaporanAdminFragment())
                else -> {}
            }
            true
        }
    }

    private fun changeFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container_adm, fragment)
        fragmentTransaction.commit()
    }
}