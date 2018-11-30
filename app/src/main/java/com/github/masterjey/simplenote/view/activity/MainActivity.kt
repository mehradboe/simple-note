package com.github.masterjey.simplenote.view.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import butterknife.BindView
import butterknife.ButterKnife
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.github.masterjey.simplenote.R
import com.github.masterjey.simplenote.view.fragment.AddNewNoteFragment
import com.github.masterjey.simplenote.view.fragment.BaseFragment
import com.github.masterjey.simplenote.view.fragment.SavedNotesFragment
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavSwitchController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import com.ncapdevi.fragnav.tabhistory.UniqueTabHistoryStrategy

class MainActivity : AppCompatActivity(), FragNavController.RootFragmentListener,
    AHBottomNavigation.OnTabSelectedListener, BaseFragment.FragmentNavigation {

    private val addNewNoteTab = FragNavController.TAB1
    private val savedNotesTab = FragNavController.TAB2

    private val addNewNoteFragment = AddNewNoteFragment()
    private val savedNotesFragment = SavedNotesFragment()

    private lateinit var fragNavController: FragNavController

    @BindView(R.id.mainBottomNavigation)
    lateinit var bottomNavigation: AHBottomNavigation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ButterKnife.bind(this)

        initFragNavController(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        bottomNavigation.accentColor =
                ResourcesCompat.getColor(resources, R.color.colorAccent, null)
        bottomNavigation.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW
        bottomNavigation.addItem(
            AHBottomNavigationItem(
                getString(R.string.newNote),
                R.drawable.ic_note_add_black_24dp
            )
        )
        bottomNavigation.addItem(
            AHBottomNavigationItem(
                getString(R.string.savedNotes),
                R.drawable.ic_folder_black_24dp
            )
        )
        bottomNavigation.setOnTabSelectedListener(this)
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
                    ) {
                        bottomNavigation.currentItem = index
                    }
                })
        fragNavController.initialize(addNewNoteTab, savedInstanceState)
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

    override fun onTabSelected(position: Int, wasSelected: Boolean): Boolean {
        when (position) {
            0 -> fragNavController.switchTab(addNewNoteTab)
            1 -> fragNavController.switchTab(savedNotesTab)
        }
        return true
    }

    override val numberOfRootFragments: Int
        get() = 2

    override fun getRootFragment(index: Int): Fragment {
        when (index) {
            addNewNoteTab -> return addNewNoteFragment
            savedNotesTab -> return savedNotesFragment
        }
        throw IllegalArgumentException("No fragment found")
    }

    override fun getFragNavController(): FragNavController {
        return fragNavController
    }
}
