package com.example.proyecto_notas

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.proyecto_notas.data.noteDatabase
import com.example.proyecto_notas.model.Note
import kotlinx.coroutines.launch

class task_adapter(var tasks: List<Note>): RecyclerView.Adapter<task_adapter.ViewHolder>(){

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v){
        var name : TextView
        var description : TextView
        var date_hour : TextView
        var btn_delete: ImageView
        var btn_edit: ImageView
        var cb_completed : CheckBox
        var btn_media : Button
        init{
            name = v.findViewById(R.id.txt_title_note)
            description = v.findViewById(R.id.txt_description_note)
            date_hour = v.findViewById(R.id.txt_date_hour)
            btn_delete = v.findViewById(R.id.btn_delete)
            btn_edit = v.findViewById(R.id.btn_edit)
            cb_completed = v.findViewById(R.id.cb_completed)
            btn_media = v.findViewById(R.id.btn_media)

        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.task_row, parent, false)

        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = tasks[position]
        holder.name.text = p.title.toString()
        holder.description.text =p.description.toString()
        holder.date_hour.text = p.date+" "+p.hour
        holder.cb_completed.isChecked = p.completed

        holder.btn_media.setOnClickListener{view : View ->
            var bundle = Bundle()
            bundle.putString("id",p.id.toString())
            bundle.putString("title",p.title)
            bundle.putString("window","task_list")
            view.findNavController().navigate(R.id.action_task_list_to_note_media,bundle)
        }

        holder.btn_delete.setOnClickListener{view : View ->
            var alarmManager =  holder.name.context.applicationContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            var lista = noteDatabase.getDatabase(holder.name.context.applicationContext).reminderDAO().getAllReminders(p.id)
            for(it in lista){
                val intent  = Intent(holder.name.context.applicationContext, Notification::class.java)
                val pendingIntent = PendingIntent.getBroadcast(
                    holder.name.context.applicationContext,
                    it.id,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
                alarmManager.cancel(pendingIntent)
            }
            noteDatabase.getDatabase(holder.name.context).reminderDAO().deleteAllReminders(p.id)
            noteDatabase.getDatabase(holder.name.context).mediaDao().deleteAllMedia(p.id)
            noteDatabase.getDatabase(holder.name.context).noteDao().deleteNote(p)
            var notes  = noteDatabase.getDatabase(holder.name.context).noteDao().getAllTasks()
            this.tasks = notes
            this.notifyItemRemoved(position)
        }

        holder.cb_completed.setOnClickListener{
            noteDatabase.getDatabase(holder.name.context).noteDao().updateTask(p.title,p.description,p.date,p.hour,holder.cb_completed.isChecked,p.id)
            var notes  = noteDatabase.getDatabase(holder.name.context).noteDao().getAllTasks()
            this.tasks = notes
            this.notifyDataSetChanged()
        }


        holder.btn_edit.setOnClickListener{ view : View ->
            var bundle = Bundle()
            bundle.putString("id",p.id.toString())
            bundle.putString("title", p.title)
            bundle.putString("description", p.description)
            bundle.putString("type", p.type.toString())
            bundle.putString("date", p.date)
            bundle.putString("hour", p.hour)
            bundle.putString("completed", p.completed.toString())
            view.findNavController().navigate(R.id.action_task_list_to_add_note,bundle)

        }

    }


    override fun getItemCount(): Int {
        return tasks.size
    }







}