package com.example.proyecto_notas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.proyecto_notas.data.noteDatabase
import com.example.proyecto_notas.model.Note

class note_adapter(var notes: List<Note>): RecyclerView.Adapter<note_adapter.ViewHolder>(){

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        var name : TextView
        var description : TextView
        var btn_delete: ImageView
        var btn_edit : ImageView
        var btn_media : Button
        init{
            name = v.findViewById(R.id.txt_title_note)
            description = v.findViewById(R.id.txt_description_note)
            btn_delete = v.findViewById(R.id.btn_delete)
            btn_edit = v.findViewById(R.id.btn_edit)
            btn_media = v.findViewById(R.id.btn_media2)

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.note_row, parent, false)

        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = notes[position]
        holder.name.text = p.title.toString()
        holder.description.text =p.description.toString()
        holder.btn_delete.setOnClickListener{view : View ->
            noteDatabase.getDatabase(holder.name.context).mediaDao().deleteAllMedia(p.id)
            noteDatabase.getDatabase(holder.name.context).noteDao().deleteNote(p)
            var notes  = noteDatabase.getDatabase(holder.name.context).noteDao().getAllNotes()
            this.notes = notes
            this.notifyItemRemoved(position)
        }
        holder.btn_edit.setOnClickListener{ view : View ->
            var bundle = Bundle()
            bundle.putString("id",p.id.toString())
            bundle.putString("title", p.title)
            bundle.putString("description", p.description)
            bundle.putString("type", p.type.toString())
            view.findNavController().navigate(R.id.action_note_list_to_add_note,bundle)

        }
        holder.btn_media.setOnClickListener{view : View ->
            var bundle = Bundle()
            bundle.putString("id",p.id.toString())
            bundle.putString("title",p.title)
            bundle.putString("window","note_list")
            view?.findNavController()?.navigate(R.id.action_note_list_to_note_media,bundle)
        }


    }


    override fun getItemCount(): Int {
        return notes.size
    }







}