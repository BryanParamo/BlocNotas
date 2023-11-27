package com.example.proyecto_notas

import android.media.Image
import android.os.Bundle
import android.provider.DocumentsContract.Root
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_notas.data.noteDatabase
import com.example.proyecto_notas.model.Note
import kotlinx.coroutines.launch


class task_list : Fragment() {

    lateinit var notes : List<Note>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_task_list, container, false)
        var filter = root.findViewById<TextView>(R.id.txt_filter)
        val rv = root.findViewById<RecyclerView>(R.id.rv_task_list)

        updateRecycler()
        root.findViewById<Button>(R.id.btn_home_task_list).setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_task_list_to_mainMenu)
        }


        rv.adapter = task_adapter(notes)
        rv.layoutManager = LinearLayoutManager(this@task_list.requireContext())
        root.findViewById<EditText>(R.id.txt_filter).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                lifecycleScope.launch{
                    if(filter.text.toString().length>0){
                        var filtro = "%"+filter.text.toString().toUpperCase()+"%"
                        notes =  noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getByTitleDescription(filtro,filtro,1)
                        rv.adapter = task_adapter(notes)
                        rv.adapter!!.notifyDataSetChanged()
                        task_adapter(notes).notifyDataSetChanged()

                    }else{
                        notes =  noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getAllTasks()
                        rv.adapter = task_adapter(notes)
                        rv.adapter!!.notifyDataSetChanged()
                        task_adapter(notes).notifyDataSetChanged()

                    }


                }
            }
        })



        // Inflate the layout for this fragment


        return root.rootView
    }

    fun updateRecycler(){
        lifecycleScope.launch{
            notes  = noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getAllTasks()

        }
    }


}