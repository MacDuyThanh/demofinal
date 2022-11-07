package com.example.demofinal.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.paging.ExperimentalPagingApi
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.demofinal.databinding.ActivityMainBinding
import com.example.demofinal.ui.screen1.Screen1Fragment
import com.example.demofinal.ui.screen2.Screen2Fragment
import com.example.demofinal.ui.screen3.Screen3Fragment
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.DaggerAppCompatActivity

@ExperimentalPagingApi
class MainActivity : DaggerAppCompatActivity() {
    private val titles = arrayListOf("Screen 1", "Screen 2", "Screen 3")
    private lateinit var binding: ActivityMainBinding
    private var appPagerAdapter: AppPagerAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        appPagerAdapter = AppPagerAdapter(this)
        val viewPager2 = binding.viewPager2Main
        val tabLayout = binding.tablayout
        binding.viewPager2Main.adapter = appPagerAdapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    class AppPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun getItemCount(): Int {
            return 3
        }

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> Screen1Fragment()
                1 -> Screen2Fragment()
                2 -> Screen3Fragment()
                else -> Screen1Fragment()
            }
        }

    }


}