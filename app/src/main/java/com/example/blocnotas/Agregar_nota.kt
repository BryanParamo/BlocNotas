package com.example.proyecto_notas

import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_notas.data.noteDatabase
import com.example.proyecto_notas.databinding.FragmentAddNoteBinding
import com.example.proyecto_notas.model.Media
import com.example.proyecto_notas.model.Note
import kotlinx.coroutines.launch
import java.io.File
import java.io.OutputStream


//import com.example.proyecto_notas.databinding.FragmentAddNoteBinding



class Add_note : Fragment() {
    lateinit var rv_media :RecyclerView
    var txt_title : EditText? = null
    var txt_description : EditText? = null
    var file_description : EditText? = null
    lateinit var sp_note_type: Spinner
    lateinit var outputStream:OutputStream
    lateinit var uri: Uri
    lateinit var videoUri : Uri
    lateinit var audioUri : Uri
    lateinit var genericUri : Uri


    var id1 : Int  = -1

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentAddNoteBinding>(inflater,
                R.layout.fragment_add_note,container,false)

        id1 =-1

        file_description = binding.fileDescription

        txt_title = binding.edtxtTitle
        txt_description = binding.edtxtDescription

        //VERIFICAR SI YA EXISTE PARA ESO NOS VAMOS A TRAER EL ID Y TAMBIEN DESHABILITAR EL TIPO DE NOTA
        sp_note_type = binding.spNoteType;

        if(arguments?.getString("title")!=null) {

            txt_title?.setText(arguments?.getString("title"))
            txt_description?.setText(arguments?.getString("description"))
            sp_note_type.setSelection(arguments?.getString("type")!!.toInt())
            id1 = arguments?.getString("id")!!.toInt()
            if (!sp_note_type.selectedItem.toString().equals("0")) {
                sp_note_type.isEnabled = false
            }
        }

        rv_media = binding.rvMedia
        rv_media.layoutManager = LinearLayoutManager(this@Add_note.requireContext())

        if(id1!=-1){
            var media = noteDatabase.getDatabase(requireActivity().applicationContext).mediaDao().getAllMedia(id1)
            rv_media.adapter = media_adapter(media)
            rv_media.adapter!!.notifyDataSetChanged()
            media_adapter(media).notifyDataSetChanged()
        }

        binding.btnAddImage.setOnClickListener{
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE).also {
                it.resolveActivity(requireActivity().packageManager).also { component ->
                    createPhotoFile()
                    val photoUri: Uri =
                            FileProvider.getUriForFile(
                                    this@Add_note.requireActivity(),
                                    BuildConfig.APPLICATION_ID + ".fileprovider", file
                            )
                    it.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                }
            }
            openCamera.launch(intent)


        }
        binding.btnAddVideo.setOnClickListener{
            recordVideo()

        }
        binding.btnAddAudio.setOnClickListener{
            val intent = Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION)
            startActivityForResult(intent, 2)
        }
        binding.btnAddFile.setOnClickListener{
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            val mimetypes = arrayOf("image/*", "video/*","audio/*")
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimetypes)
            startActivityForResult(intent, 111)
        }

        binding.btnNext.setOnClickListener{view : View ->
            if(txt_title!!.length()>0){

                if(sp_note_type.selectedItemId.toString().equals("1")){
                    if(txt_description!!.length()>0){
                        var bundle = Bundle()
                        bundle.putString("title", txt_title!!.text.toString())
                        bundle.putString("description", txt_description!!.text.toString())
                        if(!sp_note_type.isEnabled){
                            bundle.putString("date",arguments?.getString("date"))
                            bundle.putString("hour",arguments?.getString("hour"))
                            bundle.putString("completed",arguments?.getString("completed"))
                            bundle.putString("id",arguments?.getString("id"))
                        }

                        parentFragmentManager.setFragmentResult("key",bundle)
                        view.findNavController().navigate(R.id.action_add_note_to_edit_task)
                    }else{
                        Toast.makeText(activity, "Debes agregar una descripción", Toast.LENGTH_SHORT).show()
                    }

                }else if(sp_note_type.selectedItemId.toString().equals("2")){

                    lifecycleScope.launch{
                        var note = noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getById(id1)
                        if(!note.toString().equals("[]")){

                            noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().updateNote(txt_title!!.text.toString().toUpperCase(),txt_description!!.text.toString().toUpperCase(),id1)
                            var notes : List<Note> = noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getAllNotes()

                        }else{
                            val newNote = Note( txt_title!!.text.toString().toUpperCase(),txt_description!!.text.toString(),2,"","",false)
                            noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().insert(newNote)
                            var notes : List<Note> = noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getAllNotes()
                            noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().insertLastID(noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getMaxID())

                        }


                        //Toast.makeText(this@Add_note.requireContext(),notes.size.toString(), Toast.LENGTH_SHORT).show()
                        view.findNavController().navigate(R.id.action_add_note_to_note_list)
                    }

                }else{
                    Toast.makeText(activity, "Debes elegir un tipo de nota", Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(activity, "Debes agregar un título", Toast.LENGTH_SHORT).show()

            }
        }

        binding.btnCancel.setOnClickListener{view : View ->
            if(id1==-1){
                var lastID = noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getLastID()

                noteDatabase.getDatabase(requireActivity().applicationContext).mediaDao().deleteAllMedia(lastID+1)
            }
            view.findNavController().navigate(R.id.action_add_note_to_mainMenu)
        }

        sp_note_type.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                var btn_next = binding.btnNext;
                if(position==2){
                    btn_next.text = "Terminar"
                }else if(position==1){
                    btn_next.text = "Siguiente"
                }
            }

        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private val openCamera =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    // val data = result.data!!
                    // val bitmap = data.extras!!.get("data") as Bitmap
                    val bitmap = getBitmap()

                    saveToGallery()

                }
            }

    fun recordVideo(){
        val videoFile = createVideoFile()

        if(videoFile!=null){

            videoUri = FileProvider.getUriForFile(
                    this@Add_note.requireActivity(),
                    "com.example.proyecto_notas.fileprovider",
                    videoFile
            )


            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_OUTPUT,videoUri)
            startActivityForResult(intent,1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==2 && resultCode== RESULT_OK){
            if (data?.data!=null){
                audioUri = data.data!!
            }

            if(id1==-1){
                var id_maximo = noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getMaxID()
                if(id_maximo==0){
                    id_maximo = noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getLastID()

                }

                var newMedia = Media(id_maximo+1,this.audioUri.toString(),file_description!!.text.toString(),3)
                noteDatabase.getDatabase(requireActivity().applicationContext).mediaDao().insert(newMedia)
                var media = noteDatabase.getDatabase(requireActivity().applicationContext).mediaDao().getAllMedia(id_maximo+1)
                rv_media.adapter = media_adapter(media)
                rv_media.adapter!!.notifyDataSetChanged()
                media_adapter(media).notifyDataSetChanged()
            }else{

                var newMedia = Media(id1,this.audioUri.toString(),file_description!!.text.toString(),3)
                noteDatabase.getDatabase(requireActivity().applicationContext).mediaDao().insert(newMedia)
                var media = noteDatabase.getDatabase(requireActivity().applicationContext).mediaDao().getAllMedia(id1)
                rv_media.adapter = media_adapter(media)
                rv_media.adapter!!.notifyDataSetChanged()
                media_adapter(media).notifyDataSetChanged()
            }

        }else if(requestCode==1 && resultCode== RESULT_OK){
            if(id1==-1){
                var id_maximo = noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getMaxID()
                if(id_maximo==0){
                    id_maximo = noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getLastID()

                }

                var newMedia = Media(id_maximo+1,this.videoUri.toString(),file_description!!.text.toString(),2)
                noteDatabase.getDatabase(requireActivity().applicationContext).mediaDao().insert(newMedia)
                var media = noteDatabase.getDatabase(requireActivity().applicationContext).mediaDao().getAllMedia(id_maximo+1)
                rv_media.adapter = media_adapter(media)
                rv_media.adapter!!.notifyDataSetChanged()
                media_adapter(media).notifyDataSetChanged()
            }else{

                var newMedia = Media(id1,this.videoUri.toString(),file_description!!.text.toString(),2)
                noteDatabase.getDatabase(requireActivity().applicationContext).mediaDao().insert(newMedia)
                var media = noteDatabase.getDatabase(requireActivity().applicationContext).mediaDao().getAllMedia(id1)
                rv_media.adapter = media_adapter(media)
                rv_media.adapter!!.notifyDataSetChanged()
                media_adapter(media).notifyDataSetChanged()
            }
        }else if (requestCode == 111 && resultCode == RESULT_OK) {
            genericUri = data?.data!!
            var type = genericUri.toString().substring(genericUri.toString().length-18,genericUri.toString().length-17)


            var intType = 0
            if(type.equals("i")){
                intType = 1
            }else if(type.equals("v")){
                intType = 2
            }else{
                intType = 3
            }
            if(id1==-1){
                var id_maximo = noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getMaxID()
                if(id_maximo==0){
                    id_maximo = noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getLastID()

                }

                var newMedia = Media(id_maximo+1,genericUri.toString(),file_description!!.text.toString(),intType)
                noteDatabase.getDatabase(requireActivity().applicationContext).mediaDao().insert(newMedia)
                var media = noteDatabase.getDatabase(requireActivity().applicationContext).mediaDao().getAllMedia(id_maximo+1)
                rv_media.adapter = media_adapter(media)
                rv_media.adapter!!.notifyDataSetChanged()
                media_adapter(media).notifyDataSetChanged()
            }else{

                var newMedia = Media(id1,genericUri.toString(),file_description!!.text.toString(),intType)
                noteDatabase.getDatabase(requireActivity().applicationContext).mediaDao().insert(newMedia)
                var media = noteDatabase.getDatabase(requireActivity().applicationContext).mediaDao().getAllMedia(id1)
                rv_media.adapter = media_adapter(media)
                rv_media.adapter!!.notifyDataSetChanged()
                media_adapter(media).notifyDataSetChanged()
            }


        }

    }

    private fun createVideoFile():File{
        val fileName = "MyVideo"
        val storageDir = this@Add_note.requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
                fileName,
                "mp4",
                storageDir
        )

    }

    lateinit var file:File

    private fun createPhotoFile() {
        val dir = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        file = File.createTempFile("IMG_${System.currentTimeMillis()}_", ".jpg", dir)
    }

    private fun saveToGallery() {
        val content = createContent()
        val uri = save(content)


        if(id1==-1){
            var id_maximo = noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getMaxID()
            if(id_maximo==0){
                id_maximo = noteDatabase.getDatabase(requireActivity().applicationContext).noteDao().getLastID()

            }

            var newMedia = Media(id_maximo+1,uri.toString(),file_description!!.text.toString(),1)
            noteDatabase.getDatabase(requireActivity().applicationContext).mediaDao().insert(newMedia)
            var media = noteDatabase.getDatabase(requireActivity().applicationContext).mediaDao().getAllMedia(id_maximo+1)
            rv_media.adapter = media_adapter(media)
            rv_media.adapter!!.notifyDataSetChanged()
            media_adapter(media).notifyDataSetChanged()
        }else{

            var newMedia = Media(id1,uri.toString(),file_description!!.text.toString(),1)
            noteDatabase.getDatabase(requireActivity().applicationContext).mediaDao().insert(newMedia)
            var media = noteDatabase.getDatabase(requireActivity().applicationContext).mediaDao().getAllMedia(id1)
            rv_media.adapter = media_adapter(media)
            rv_media.adapter!!.notifyDataSetChanged()
            media_adapter(media).notifyDataSetChanged()
        }

        clearContents(content, uri)
    }

    private fun createContent(): ContentValues {
        val fileName = file.name
        val fileType = "image/jpg"
        return ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.Files.FileColumns.MIME_TYPE, fileType)
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES)
            put(MediaStore.MediaColumns.IS_PENDING, 1)
        }
    }

    private fun save(content: ContentValues): Uri {
        var outputStream: OutputStream?
        var uri: Uri?
        this@Add_note.requireActivity().application.contentResolver.also { resolver ->
            uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, content)
            outputStream = resolver.openOutputStream(uri!!)
        }
        outputStream.use { output ->
            getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, output)
        }
        return uri!!
    }


    private fun clearContents(content: ContentValues, uri: Uri) {
        content.clear()
        content.put(MediaStore.MediaColumns.IS_PENDING,0)
        this@Add_note.requireActivity().contentResolver.update(uri,content,null,null)
    }

    private fun getBitmap(): Bitmap {
        return BitmapFactory.decodeFile(file.toString())
    }







}