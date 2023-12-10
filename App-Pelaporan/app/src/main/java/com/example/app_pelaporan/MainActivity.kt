package com.example.app_pelaporan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.app_pelaporan.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        changeFragment(TambahLaporanFragment())

        binding.navbar.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.tab_home -> changeFragment(BerandaFragment())
                R.id.tab_saran -> changeFragment(SaranFragment())
                R.id.tab_laporan -> changeFragment(LaporanFragment())
                else -> {}
            }
            true
        }
    }

    private fun changeFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
    }

}
