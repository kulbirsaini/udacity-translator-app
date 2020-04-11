package com.example.translator

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class FragmentAdapter(fa: FragmentActivity): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 4

    override fun createFragment(position: Int) : Fragment {
        val fragmentId = when (position) {
            1 -> ListFragment.FRAGMENT_FAMILY_MEMBERS
            2 -> ListFragment.FRAGMENT_COLORS
            3 -> ListFragment.FRAGMENT_PHRASES
            else -> ListFragment.FRAGMENT_NUMBERS
        }

        val fragment = ListFragment()
        fragment.arguments = Bundle().apply {
            putInt(FragmentCallback.FRAGMENT_ID, fragmentId)
        }

        return fragment
    }
}