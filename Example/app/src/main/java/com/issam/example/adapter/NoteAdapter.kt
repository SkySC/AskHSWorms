package com.issam.example.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.issam.example.R
import com.issam.example.com.issam.example.model.Note
import kotlinx.android.synthetic.main.notes_items.view.*

class NoteAdapter(context: Context , noteList: ArrayList<Note>) :
    ArrayAdapter<Note>(context , 0 , noteList) {

    override fun getView(position: Int , convertView: View? , parent: ViewGroup): View {

        val view = LayoutInflater.from(context).inflate(R.layout.notes_items , parent , false)
        val note: Note? = getItem(position)

        if (note != null) {
            view.datum_text.text = note.timestamp.toString()
            view.titel_note.text = note.title
            view.contenu_notizen.text = note.note
        }

        return view
    }
}