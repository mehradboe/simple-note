package com.github.masterjey.simplenote.view.fragment

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.os.Handler
import com.google.android.material.appbar.AppBarLayout
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnTextChanged
import com.github.masterjey.simplenote.R
import com.github.masterjey.simplenote.entity.Note
import com.github.masterjey.simplenote.view.adapter.SavedNotesAdapter
import com.github.masterjey.simplenote.viewmodel.SavedNoteViewModel
import kotlinx.android.synthetic.main.fragment_saved_notes.*

class SavedNotesFragment : BaseFragment(), PopupMenu.OnMenuItemClickListener,
    Observer<MutableList<Note>> {

    private lateinit var viewModel: SavedNoteViewModel
    private lateinit var adapter: SavedNotesAdapter
    private lateinit var notesList: MutableList<Note>

    private val searchHandler = Handler()

    @BindView(R.id.savedNotesAppBarLayout)
    lateinit var appBarLayout: AppBarLayout
    @BindView(R.id.savedNotesMoreAction)
    lateinit var moreAction: ImageView
    @BindView(R.id.savedNotesRecyclerView)
    lateinit var recyclerView: androidx.recyclerview.widget.RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_saved_notes, container, false)

        ButterKnife.bind(this, view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()

        viewModel = ViewModelProviders.of(this).get(SavedNoteViewModel::class.java)
        viewModel.getAllNotes().observe(this, this)
    }

    private fun initViews() {
        adapter = SavedNotesAdapter(context!!)
        adapter.setOnSavedNoteClickListener(object : SavedNotesAdapter.OnSavedNoteClickListener {
            override fun onClick(note: Note) {
                editNote(note)
            }
        })
        recyclerView.adapter = adapter
        recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
        recyclerView.itemAnimator = androidx.recyclerview.widget.DefaultItemAnimator()
    }

    override fun onChanged(notes: MutableList<Note>?) {
        if (notes!!.size > 0)
            savedNotesNoCreatedNotesLayout.visibility = View.GONE
        else savedNotesNoCreatedNotesLayout.visibility = View.VISIBLE

        notesList = notes
        recyclerView.scrollToPosition(0)
        adapter.setData(notes)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.savedNotesDeleteAll -> viewModel.deleteAll()
        }
        return true
    }

    @OnTextChanged(R.id.savedNotesSearchInput)
    fun searchInputOnTextChanged(text: CharSequence) {
        searchHandler.removeCallbacksAndMessages(null)
        searchHandler.postDelayed({
            searchInNotes(text.toString())
        }, 200)
    }

    @OnClick(R.id.savedNotesMoreAction)
    fun moreActionOnClick() {
        val popupMenu = PopupMenu(context, moreAction)
        popupMenu.inflate(R.menu.saved_notes_more_action)
        popupMenu.setOnMenuItemClickListener(this)
        popupMenu.show()
    }

    @OnClick(R.id.savedNotesAddNewNote)
    fun addNewNoteOnClick() {
        pushFragment(AddNewNoteFragment())
    }

    fun editNote(note: Note) {
        val bundle = Bundle()
        val fragment = EditNoteFragment()
        bundle.putParcelable("note", note)
        fragment.arguments = bundle
        pushFragment(fragment)
    }

    private fun searchInNotes(text: String) {
        val lowerCaseText = text.toLowerCase()

        val filteredList: MutableList<Note> = ArrayList()

        for (item in notesList) {
            if (item.title.toLowerCase().contains(lowerCaseText) || item.content.toLowerCase().contains(
                    lowerCaseText
                )
            )
                filteredList.add(item)
        }

        adapter.setData(filteredList)
        recyclerView.scrollToPosition(0)
    }

}