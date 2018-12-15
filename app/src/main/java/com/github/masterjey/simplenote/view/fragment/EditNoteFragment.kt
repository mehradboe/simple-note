package com.github.masterjey.simplenote.view.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import butterknife.BindView
import butterknife.ButterKnife
import com.github.masterjey.simplenote.R
import com.github.masterjey.simplenote.databinding.FragmentAddNewNoteBinding
import com.github.masterjey.simplenote.entity.Note
import com.github.masterjey.simplenote.viewmodel.EditNoteViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_add_new_note.*
import java.util.*

class EditNoteFragment : AddNewNoteFragment() {

    private lateinit var viewModel: EditNoteViewModel

    private lateinit var note: Note

    @BindView(R.id.addNewNoteToolbar)
    lateinit var fragmentTitle: TextView

    lateinit var dataBinding: FragmentAddNewNoteBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_add_new_note, container, false)

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ButterKnife.bind(this, view)

        viewModel = EditNoteViewModel(activity!!.application)
        if (arguments != null) {
            note = arguments!!.getParcelable("note") as Note
            initViews()
        }
    }

    private fun initViews() {
        fragmentTitle.text = getString(R.string.editNoteFragmentTitle)
        dataBinding.note = note
    }

    override fun saveNoteOnClick() {
        viewModel.saveNote(wrapNote())
        showSnackBar()
    }

    override fun onNoteChanged() {
        var canSave = (inputTitle.text.isNotBlank() && inputTitle.text.toString() != note.title)
        canSave = canSave or (inputContent.text.isNotBlank() && inputContent.text.toString() != note.content)
        val saveButtonVisibility = addNewNoteSave.visibility == View.VISIBLE

        if (canSave and !saveButtonVisibility)
            saveNote.show()
        else if (!canSave and saveButtonVisibility)
            saveNote.hide()
    }

    private fun showSnackBar() {
        val snackBar = Snackbar.make(view!!, getString(R.string.noteUpdated), Snackbar.LENGTH_LONG)
        snackBar.setAction(getString(R.string.undo)) {
            snackBar.dismiss()
            saveOldNote()
        }.show()
        Handler().postDelayed({
            getFragNavController().popFragment()
        }, 300)
    }

    override fun wrapNote(): Note {
        val editedNote = Note()
        return editedNote.apply {
            id = note.id
            title = inputTitle.text.toString()
            content = inputContent.text.toString()
            editedDate = Date().time
        }
    }

    private fun saveOldNote() {
        viewModel.saveNote(note)
    }

}