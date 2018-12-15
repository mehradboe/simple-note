package com.github.masterjey.simplenote.view.activity

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import butterknife.ButterKnife
import com.github.masterjey.simplenote.R
import com.github.masterjey.simplenote.view.fragment.BaseFragment
import com.github.masterjey.simplenote.view.fragment.SavedNotesFragment
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavSwitchController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.ncapdevi.fragnav.tabhistory.UniqueTabHistoryStrategy
import io.github.inflationx.viewpump.ViewPumpContextWrapper

class MainActivity : AppCompatActivity(), FragNavController.RootFragmentListener, BaseFragment.FragmentNavigation {
    
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
        fragNavController = FragNavController(supportFragmentManager, R.id.mainFragmentContainer)
        fragNavController.rootFragmentListener = this
        fragNavController.createEager = true
        fragNavController.fragmentHideStrategy = FragNavController.HIDE
        fragNavController.navigationStrategy =
                UniqueTabHistoryStrategy(object : FragNavSwitchController {
                    override fun switchTab(
                        index: Int,
                        transactionOptions: FragNavTransactionOptions?
                            index: Int,
                            transactionOptions: FragNavTransactionOptions?
                    ) {
                    }
                })
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

    override val numberOfRootFragments: Int
        get() = 1

    override fun getRootFragment(index: Int): androidx.fragment.app.Fragment {
        when (index) {
            savedNotesTab -> return savedNotesFragment
        }
        throw IllegalArgumentException("No fragment found")
    }

    override fun getFragNavController(): FragNavController {
        return fragNavController
    }
}
