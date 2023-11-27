package com.example.proyecto_notas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.proyecto_notas.databinding.FragmentMainMenuBinding


class MainMenu : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = DataBindingUtil.inflate<FragmentMainMenuBinding>(inflater,
            R.layout.fragment_main_menu,container,false)

        binding.btnAddNote.setOnClickListener{ view : View ->
        view.findNavController().navigate(R.id.action_mainMenu_to_add_note)

        }

        binding.btnTaskList.setOnClickListener{ view : View ->
            view.findNavController().navigate(R.id.action_mainMenu_to_task_list)

        }

        binding.btnNoteList.setOnClickListener { view: View ->
            view.findNavController().navigate(R.id.action_mainMenu_to_note_list)
        }

        // Inflate the layout for this fragment
        return binding.root
    }

}