package com.github.masterjey.simplenote.view.adapter

import android.content.Context
import android.os.Build
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.github.masterjey.simplenote.R
import com.github.masterjey.simplenote.entity.Note
import com.github.masterjey.simplenote.utils.convertToDate
import java.util.*


class SavedNotesAdapter(private val context: Context) :
        RecyclerView.Adapter<SavedNotesAdapter.SavedNotesViewHolder>() {

    private var onSavedNoteClickListener: OnSavedNoteClickListener? = null

    private var list: MutableList<Note> = ArrayList()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): SavedNotesViewHolder {
        return SavedNotesViewHolder(
                LayoutInflater.from(context).inflate(R.layout.list_saved_notes_item, viewGroup, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SavedNotesViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun setData(newList: MutableList<Note>) {
        val noteDiffCallBack = NoteDiffCallBack(list, newList)
        val diffResult = DiffUtil.calculateDiff(noteDiffCallBack)

        list.clear()
        list.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun setOnSavedNoteClickListener(onSavedNoteClickListener: OnSavedNoteClickListener) {
        this.onSavedNoteClickListener = onSavedNoteClickListener
    }

    inner class SavedNotesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
            View.OnClickListener {

        @BindView(R.id.savedNotesItemRootLayout)
        lateinit var rootLayout: CardView
        @BindView(R.id.savedNotesTitleNote)
        lateinit var titleTextView: TextView
        @BindView(R.id.savedNotesContentNote)
        lateinit var contentTextView: TextView
        @BindView(R.id.savedNotesEditedDate)
        lateinit var editedDateTextView: TextView

        init {
            ButterKnife.bind(this, itemView)
        }

        fun bind(note: Note) {
            rootLayout.setOnClickListener(this)
            titleTextView.text = note.title
            contentTextView.text = note.content
            editedDateTextView.text = convertToDate(context, note.createdDate)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                titleTextView.transitionName = "titleTransition$adapterPosition"
                contentTextView.transitionName = "contentTransition$adapterPosition"
            }
        }

        override fun onClick(v: View?) {
            when (v!!.id) {
                R.id.savedNotesItemRootLayout -> {
                    if (onSavedNoteClickListener != null) {
                        Handler().postDelayed({
                            onSavedNoteClickListener!!.onClick(list[adapterPosition])
                        }
                                , 500)
                    }
                }
            }
        }

    }

    inner class NoteDiffCallBack(
            private val oldNotes: List<Note>,
            private val newNotes: List<Note>
    ) :
            DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldNotes.size

        override fun getNewListSize(): Int = newNotes.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldNotes[oldItemPosition].id == newNotes[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldNotes[oldItemPosition] == newNotes[newItemPosition]
        }

    }

    interface OnSavedNoteClickListener {
        fun onClick(note: Note)
    }

}