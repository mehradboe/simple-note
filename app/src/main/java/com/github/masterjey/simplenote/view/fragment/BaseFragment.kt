package com.github.masterjey.simplenote.view.fragment

import android.content.Context
import android.support.v4.app.Fragment
import com.ncapdevi.fragnav.FragNavController

open class BaseFragment : Fragment() {

    private lateinit var fragmentNavigation: FragmentNavigation
    private lateinit var fragNavController: FragNavController

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is FragmentNavigation) {
            fragmentNavigation = context
            fragNavController = fragmentNavigation.getFragNavController()
        } else throw IllegalArgumentException("context must be instance of BaseFragment.FragmentNavigation")
    }

    fun pushFragment(fragment: Fragment) {
        fragNavController.pushFragment(fragment)
    }

    fun getFragNavController(): FragNavController {
        return fragmentNavigation.getFragNavController()
    }

    interface FragmentNavigation {
        fun getFragNavController(): FragNavController
    }

}