package com.example.quanlychitieu.activities

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.quanlychitieu.R
import com.example.quanlychitieu.databinding.ActivityMainBinding
import com.example.quanlychitieu.fragments.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.background = null
        window.statusBarColor = ContextCompat.getColor(this, R.color.blue)

        val homeFragment = HomeFragment()
        val addFragment = AddFragment()
        val settingFragment = SettingFragment()
        val historyFragment = HistoryFragment()
        val chartFragment = ChartFragment()

//        val transaction = supportFragmentManager.beginTransaction() //bat dau phien lam viec
//        transaction.add(R.id.frame_layout, addFragment)
//        transaction.replace(R.id.frame_layout, AddFragment())
//        transaction.addToBackStack(null)
//        transaction.commit()

        makeCurentFragment(homeFragment)
        binding.bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> makeCurentFragment(homeFragment)
                R.id.menu_chart -> makeCurentFragment(chartFragment)
                R.id.menu_setting -> makeCurentFragment(settingFragment)
                R.id.menu_history -> makeCurentFragment(historyFragment)
            }
            true
        }
        binding.fab.setOnClickListener {
            makeCurentFragment(addFragment)
        }
    }

    private fun makeCurentFragment(fragment : androidx.fragment.app.Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frame_layout, fragment)
            commit()
        }

}