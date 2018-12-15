package com.github.masterjey.simplenote.view.fragment

import android.content.Context
import com.ncapdevi.fragnav.FragNavController

open class BaseFragment : androidx.fragment.app.Fragment() {

    private lateinit var fragmentNavigation: FragmentNavigation
    private lateinit var fragNavController: FragNavController

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigation) {
            fragmentNavigation = context
            fragNavController = fragmentNavigation.getFragNavController()
        } else throw IllegalArgumentException("context must be instance of BaseFragment.FragmentNavigation")
    }

    fun pushFragment(fragment: androidx.fragment.app.Fragment) {
        fragNavController.pushFragment(fragment)
    }

    fun popFragment() {
        fragNavController.popFragment()
    }

    fun getFragNavController(): FragNavController {
        return fragmentNavigation.getFragNavController()
    }

    interface FragmentNavigation {
        fun getFragNavController(): FragNavController
    }

}