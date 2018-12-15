package com.github.masterjey.simplenote.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Note() : Parcelable {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    var title: String = "No Title"
    var content: String = "No Content"
    var createdDate: Long = 0
    var editedDate: Long = 0

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        title = parcel.readString()!!
        content = parcel.readString()!!
        createdDate = parcel.readLong()
        editedDate = parcel.readLong()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeLong(createdDate)
        parcel.writeLong(editedDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }

}