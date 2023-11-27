package com.example.proyecto_notas

import android.media.MediaPlayer
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_notas.model.Media


class media_adapter2(var media: List<Media>): RecyclerView.Adapter<media_adapter2.ViewHolder>(){

    class ViewHolder(v : View) : RecyclerView.ViewHolder(v){


        var img_tumbnail : ImageView
        var media_description : TextView
        var vv_player : VideoView
        var btn_play_audio : Button

        init{
            img_tumbnail = v.findViewById(R.id.big_tumbnail)
            media_description = v.findViewById(R.id.big_description)
            vv_player = v.findViewById(R.id.vv_player)
            btn_play_audio = v.findViewById(R.id.btn_play_audio2)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.media_row_2, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val p = media[position]
        holder.vv_player.visibility = View.GONE
        holder.btn_play_audio.visibility = View.GONE
        holder.img_tumbnail.visibility = View.GONE

        if(p.type==1){
            holder.img_tumbnail.visibility = View.VISIBLE
            holder.img_tumbnail.setImageURI( Uri.parse(p.path))
            var x = -90
            holder.img_tumbnail.rotation = x.toFloat()

        }else if(p.type==2){
            holder.vv_player.visibility = View.VISIBLE
            holder.vv_player.setVideoURI(Uri.parse(p.path))
            val mediaController = MediaController(holder.img_tumbnail.context)
            mediaController.setAnchorView(holder.vv_player)
            holder.vv_player.setMediaController(mediaController)
        }else if(p.type==3){
            holder.btn_play_audio.visibility = View.VISIBLE
            holder.btn_play_audio.setOnClickListener{

                val mediaPlayer = MediaPlayer.create(holder.img_tumbnail.context, Uri.parse(p.path))
                mediaPlayer.start()
            }
        }

        holder.media_description.text = p.description

    }

    override fun getItemCount(): Int {
        return media.size
    }

}