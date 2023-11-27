package com.example.proyecto_notas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.proyecto_notas.data.noteDatabase
import com.example.proyecto_notas.model.Reminder

class reminder_adapter(var reminders: List<Reminder>): RecyclerView.Adapter<reminder_adapter.ViewHolder>(){

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        var time : TextView
        var date : TextView
        var btn_delete : ImageView

        init{
            time = v.findViewById(R.id.reminder_time)
            date = v.findViewById(R.id.reminder_date)
            btn_delete = v.findViewById(R.id.btn_delete2)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.reminder_row, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = reminders[position]
        var id = p.noteID
        holder.time.text = p.time
        holder.date.text = p.date
        holder.btn_delete.setOnClickListener{view : View ->
            noteDatabase.getDatabase(holder.time.context).reminderDAO().deleteReminder(p)
            var reminders  = noteDatabase.getDatabase(holder.time.context).reminderDAO().getAllReminders(id)
            this.reminders = reminders
            this.notifyItemRemoved(position)
        }

    }

    override fun getItemCount(): Int {
        return reminders.size
    }

}