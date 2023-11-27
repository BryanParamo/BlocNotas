package com.example.proyecto_notas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_notas.data.noteDatabase


class note_media : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_note_media, container, false)
        var id = arguments?.getString("id")!!.toInt()
        var title = arguments?.getString("title")!!.toString()
        var window = arguments?.getString("window")!!.toString()
        var titulo = root.findViewById<TextView>(R.id.txt_task_title)
        titulo.text = title
        var btn_back = root.findViewById<Button>(R.id.btn_back)
        btn_back.setOnClickListener{ view : View ->
            if(window.equals("task_list")){
                view.findNavController().navigate(R.id.action_note_media_to_task_list)
            }else{
                view.findNavController().navigate(R.id.action_note_media_to_note_list)
            }

        }
        var media  = noteDatabase.getDatabase(requireActivity().applicationContext).mediaDao().getAllMedia(id)
        val rv = root.findViewById<RecyclerView>(R.id.rv_all_media)
        rv.adapter = media_adapter2(media)
        rv.layoutManager = LinearLayoutManager(this@note_media.requireContext())
        return root.rootView
    }

}