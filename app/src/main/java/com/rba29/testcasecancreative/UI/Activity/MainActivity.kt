package com.rba29.testcasecancreative.UI.Activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.rba29.testcasecancreative.R
import com.rba29.testcasecancreative.UI.Fragment.FavoriteFragment
import com.rba29.testcasecancreative.UI.Fragment.HomeFragment
import com.rba29.testcasecancreative.databinding.ActivityMainBinding
import me.ertugrul.lib.OnItemSelectedListener

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var currentFragment: Fragment? = null

    private var backPressedTime: Long = 0
    private val TIME_INTERVAL: Long =
        2000 // Waktu interval antara dua tekanan tombol "back", dalam milidetik

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            // Memakai library karena bawaan google material over height

            bottomBar.setOnNavigationItemSelectedListener { item ->
                var selectedFragment: Fragment? = null
                when (item.itemId) {
                    R.id.nb_home -> selectedFragment = HomeFragment()
                    R.id.nb_fav -> selectedFragment = FavoriteFragment()
                }
                if (selectedFragment != null) {
                    supportFragmentManager.beginTransaction().replace(R.id.frame_layout, selectedFragment).commit()
                    currentFragment = selectedFragment
                }else{
                    Toast.makeText(
                        this@MainActivity,
                        "Opps!, Halaman Belum Tersedia",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                true
            }

            // Tampilkan fragment default saat aplikasi dibuka
            if (savedInstanceState == null) {
                val homeFragment = HomeFragment()
                supportFragmentManager.beginTransaction().replace(R.id.frame_layout, homeFragment).commit()
                currentFragment = homeFragment
                binding.bottomBar.selectedItemId = R.id.nb_home

            }
        }

    }

    override fun onBackPressed() {

        if (currentFragment is FavoriteFragment) {
            // Jika saat ini berada di FavoriteFragment, kembali ke HomeFragment
            val homeFragment = HomeFragment()
            supportFragmentManager.beginTransaction().replace(R.id.frame_layout, homeFragment).commit()
            currentFragment = homeFragment
            binding.bottomBar.selectedItemId = R.id.nb_home
        } else {
            if (backPressedTime + TIME_INTERVAL > System.currentTimeMillis()) {
                super.onBackPressed()
                return
            } else {
                Toast.makeText(this, getString(R.string.confirm_close), Toast.LENGTH_SHORT).show()
            }
            backPressedTime = System.currentTimeMillis()
        }


    }
}