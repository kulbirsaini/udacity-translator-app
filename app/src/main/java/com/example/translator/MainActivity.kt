package com.example.translator

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("TransLog", "MainActivity:onCreate")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.pager)
        viewPager.adapter = FragmentAdapter(this)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabName(position + 1)
        }.attach()
    }

    override fun onBackPressed() {
        Log.e("TransLog", "MainActivity:onBackPressed")

        if (viewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            viewPager.currentItem = viewPager.currentItem - 1
        }
    }

    private fun getTabName(fragmentId: Int): String {
        return when (fragmentId) {
            ListFragment.FRAGMENT_COLORS -> getString(R.string.category_colors)
            ListFragment.FRAGMENT_FAMILY_MEMBERS -> getString(R.string.category_family_members)
            ListFragment.FRAGMENT_PHRASES -> getString(R.string.category_phrases)
            else -> getString(R.string.category_numbers)
        }
    }
}
