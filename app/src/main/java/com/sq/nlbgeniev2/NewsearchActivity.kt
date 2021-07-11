package com.sq.nlbgeniev2

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson


class NewsearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.newsearch)
        val sliderlabel: TextView = findViewById<TextView>(R.id.slidelabel)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        val keyword = findViewById<EditText>(R.id.keyword)
        val author = findViewById<EditText>(R.id.author)
        val back = findViewById<ImageButton>(R.id.imageButton2)


        val progress = seekBar.progress
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener)
        sliderlabel.text = "Progress: $progress"
        keyword.requestFocus()
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
        val saveButton: Button = findViewById<Button>(R.id.savebutton)
        val sharedPreferences: SharedPreferences = getSharedPreferences("SharedPreferences",
            Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        saveButton.setOnClickListener {
            if (keyword.text.isBlank() and author.text.isBlank()) {
                Toast.makeText(this, "Either keyword or author must be filled in.", Toast.LENGTH_SHORT).show()
            } else {
                val newSearch: Searches = Searches(keyword.text.toString(), author.text.toString(), seekBar.progress.toString())
                val gson = Gson()
                val json = gson.toJson(newSearch)
                if (keyword.text.isBlank()) {
                    editor.putString(author.text.toString(), json)
                }
                else{
                    editor.putString(keyword.text.toString(), json)
                }
                editor.apply()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }



        back.setOnClickListener(View.OnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        })
    }
    var seekBarChangeListener: SeekBar.OnSeekBarChangeListener = object :
        SeekBar.OnSeekBarChangeListener {
        @SuppressLint("SetTextI18n")
        override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
            // updated continuously as the user slides the thumb
            val sliderlabel: TextView = findViewById<TextView>(R.id.slidelabel)
            if (progress <= 20) {
                sliderlabel.text = "Number of searches: $progress"
            }
            if(progress > 20){
                sliderlabel.text = "Number of searches: $progress (Doing too many searches may result in longer search times and more irrelevant results.)"
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
            // called when the user first touches the SeekBar
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            // called after the user finishes moving the SeekBar
        }
    }


}