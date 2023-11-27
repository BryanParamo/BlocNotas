import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_notas.R
import com.example.proyecto_notas.data.noteDatabase
import com.example.proyecto_notas.model.Note
import com.example.proyecto_notas.note_adapter
import com.example.proyecto_notas.task_adapter
import kotlinx.coroutines.launch


class note_list : Fragment() {

    lateinit var notes : List<Note>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_note_list, container, false)

        val rv = root.findViewById<RecyclerView>(R.id.rv_note_list)

        lifecycleScope.launch{
            notes  = noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getAllNotes()
        }

        rv.adapter = note_adapter(notes)
        rv.layoutManager = LinearLayoutManager(this@note_list.requireContext())
        // Inflate the layout for this fragment

        root.findViewById<Button>(R.id.btn_home).setOnClickListener{
            view?.findNavController()?.navigate(R.id.action_note_list_to_mainMenu)
        }

        var filter = root.findViewById<EditText>(R.id.txt_filter_note)
        root.findViewById<EditText>(R.id.txt_filter_note).addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                lifecycleScope.launch{
                    if(filter.text.toString().length>0){
                        var filtro = "%"+filter.text.toString().toUpperCase()+"%"
                        notes =  noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getByTitleDescription(filtro,filtro,2)
                        rv.adapter = note_adapter(notes)
                        rv.adapter!!.notifyDataSetChanged()
                        note_adapter(notes).notifyDataSetChanged()

                    }else{
                        notes =  noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getAllNotes()
                        rv.adapter = note_adapter(notes)
                        rv.adapter!!.notifyDataSetChanged()
                        note_adapter(notes).notifyDataSetChanged()

                    }


                }
            }
        })


        return root.rootView
    }

}