package com.github.masterjey.simplenote.view.activity

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import com.github.masterjey.simplenote.R
import com.github.masterjey.simplenote.view.fragment.BaseFragment
import com.github.masterjey.simplenote.view.fragment.SavedNotesFragment
import com.ncapdevi.fragnav.FragNavController
import io.github.inflationx.viewpump.ViewPumpContextWrapper

class MainActivity : AppCompatActivity(), BaseFragment.FragmentNavigation {

    // change

    private val savedNotesTab = FragNavController.TAB1

    private val savedNotesFragment = SavedNotesFragment()

    private lateinit var fragNavController: FragNavController

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase!!))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)

        initFragNavController(savedInstanceState)
    }

    private fun initFragNavController(savedInstanceState: Bundle?) {
        fragNavController = FragNavController(supportFragmentManager, R.id.mainFragmentContainer).apply {
            rootFragments = listOf(savedNotesFragment)
            createEager = true
            fragmentHideStrategy = FragNavController.HIDE
        }
        fragNavController.initialize(savedNotesTab, savedInstanceState)
        fragNavController.executePendingTransactions()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        fragNavController.onSaveInstanceState(outState)
    }

    override fun onBackPressed() {
        if (fragNavController.popFragment().not()) {
            super.onBackPressed()
        }
    }

    override fun getFragNavController(): FragNavController {
        return fragNavController
    }
}
