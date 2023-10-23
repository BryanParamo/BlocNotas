package com.example.blocnotas


import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import java.util.*


class MainActivity : AppCompatActivity() {
    //val app = applicationContext as NoteApp
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Toast.makeText( this, applicationContext.toString(),Toast.LENGTH_SHORT).show()
        val languageToLoad = "en" // your language

        val locale = Locale(languageToLoad)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
        val languagepref = getSharedPreferences("language", MODE_PRIVATE)
        val editor = languagepref.edit()
        editor.putString("languageToLoad", languageToLoad)
        editor.commit()


    }

    override fun onBackPressed() {

        finish()
    }

}