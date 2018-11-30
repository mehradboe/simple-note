package com.github.masterjey.simplenote.view.fragment

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.TextView
import butterknife.BindView
import com.github.masterjey.simplenote.R
import com.github.masterjey.simplenote.entity.Note
import com.github.masterjey.simplenote.model.EditNoteViewModel
import kotlinx.android.synthetic.main.fragment_add_new_note.*
import java.util.*

class EditNoteFragment : AddNewNoteFragment() {

    private lateinit var viewModel: EditNoteViewModel

    private lateinit var note: Note

    @BindView(R.id.addNewNoteToolbar)
    lateinit var fragmentTitle: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = EditNoteViewModel(activity!!.application)
        if (arguments != null) {
            note = arguments!!.getParcelable("note") as Note
            initViews()
        }
    }

    private fun initViews() {
        fragmentTitle.text = getString(R.string.editNoteFragmentTitle)
        inputTitle.setText(note.title)
        inputContent.setText(note.content)
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
            animateView.fadeIn()
        else if (!canSave and saveButtonVisibility)
            animateView.fadeOut()
    }

    private fun showSnackBar() {
        val snackBar = Snackbar.make(view!!, getString(R.string.noteUpdated), Snackbar.LENGTH_LONG)
        snackBar.setAction(getString(R.string.undo)) {
            snackBar.dismiss()
            saveOldNote()
        }.show()
        popFragment()
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

    private fun popFragment() {
        Handler().postDelayed({
            getFragNavController().popFragment()
        }, 300)
    }

}