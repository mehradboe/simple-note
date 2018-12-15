package com.github.masterjey.simplenote.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.OnTextChanged
import com.github.masterjey.simplenote.R
import com.github.masterjey.simplenote.entity.Note
import com.github.masterjey.simplenote.viewmodel.AddNoteViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_add_new_note.*
import java.util.*

open class AddNewNoteFragment : BaseFragment() {

    private lateinit var viewModel: AddNoteViewModel

    @BindView(R.id.addNewNoteTitle)
    lateinit var inputTitle: EditText
    @BindView(R.id.addNewNoteContent)
    lateinit var inputContent: EditText
    @BindView(R.id.addNewNoteSave)
    lateinit var saveNote: FloatingActionButton

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_new_note, container, false)

        ButterKnife.bind(this, view)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(AddNoteViewModel::class.java)

    }

    @OnTextChanged(R.id.addNewNoteTitle, R.id.addNewNoteContent)
    open fun onNoteChanged() {
        val isNotBlank = inputTitle.text.isNotBlank() or inputContent.text.isNotBlank()
        val visible = addNewNoteSave.visibility == View.VISIBLE

        if (!visible and isNotBlank)
            saveNote.show()
        else if (visible and !isNotBlank)
            saveNote.hide()
    }

    @OnClick(R.id.addNewNoteSave)
    open fun saveNoteOnClick() {
        viewModel.addNewNote(wrapNote())
        popFragment()
        resetInputs()
    }

    open fun wrapNote(): Note {
        return Note().apply {
            if (inputTitle.text.isNotEmpty())
                title = inputTitle.text.toString()
            if (inputContent.text.isNotEmpty())
                content = inputContent.text.toString()
            createdDate = Date().time
            editedDate = Date().time
        }
    }

    private fun resetInputs() {
        inputTitle.text.clear()
        inputContent.text.clear()
    }

}