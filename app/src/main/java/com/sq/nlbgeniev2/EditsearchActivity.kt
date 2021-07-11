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


class EditsearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val keywordStr = intent.getStringExtra("keyword")
        val authorStr = intent.getStringExtra("author")
        val limitStr = intent.getStringExtra("limit")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.editsearch)
        val sliderlabel: TextView = findViewById<TextView>(R.id.slidelabel)
        val seekBar = findViewById<SeekBar>(R.id.seekBar)
        val keyword = findViewById<EditText>(R.id.keyword)
        val author = findViewById<EditText>(R.id.author)
        val back = findViewById<ImageButton>(R.id.imageButton2)
        val deleteButton = findViewById<Button>(R.id.deleteButton)

        val progress = seekBar.progress
        if (limitStr != null) {
            seekBar.progress = limitStr.toInt()
        }
        keyword.setText(keywordStr)
        author.setText(authorStr)

        keyword.requestFocus()
        val imm: InputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0)
        
        seekBar.setOnSeekBarChangeListener(seekBarChangeListener)
        sliderlabel.text = "Progress: $progress"

        val saveButton: Button = findViewById<Button>(R.id.savebutton)
        val sharedPreferences: SharedPreferences = getSharedPreferences("SharedPreferences",
            Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        saveButton.setOnClickListener {
            if (keyword.text.isBlank() and author.text.isBlank()) {
                Toast.makeText(this, "If you wish to delete this search, please press \"delete\" instead.", Toast.LENGTH_SHORT).show()
            } else {
                val newSearch: Searches = Searches(keyword.text.toString(), author.text.toString(), seekBar.progress.toString())
                val gson = Gson()
                val json = gson.toJson(newSearch)
                if (keywordStr != null) {
                    editor.remove(keywordStr)
                } else {
                    editor.remove(authorStr)
                }
                editor.putString(keyword.text.toString(), json)
                editor.apply()
                val savedName = sharedPreferences.getString(keyword.text.toString(), "error")
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        }

        deleteButton.setOnClickListener{
            if (keywordStr != null) {
                if (keywordStr.isNotBlank()) {
                    editor.remove(keywordStr)
                } else {
                    editor.remove(authorStr)
                }
            }
            editor.apply()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
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
            sliderlabel.text = "Progress: $progress"
        }

        override fun onStartTrackingTouch(seekBar: SeekBar) {
            // called when the user first touches the SeekBar
        }

        override fun onStopTrackingTouch(seekBar: SeekBar) {
            // called after the user finishes moving the SeekBar
        }
    }


}