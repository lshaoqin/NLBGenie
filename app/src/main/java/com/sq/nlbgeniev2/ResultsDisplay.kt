package com.sq.nlbgeniev2

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sq.nlbgeniev2.data.Datasource

class ResultsDisplay : AppCompatActivity() {


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        var adDisplayed = false
        super.onCreate(savedInstanceState)
        setContentView(R.layout.results_display)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview2)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val loadingtext = findViewById<TextView>(R.id.textView2)
        val back = findViewById<ImageButton>(R.id.imageButton2)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ResultsAdaptor(data, this)


    if(!adDisplayed) {
        val mInterstitialAd = MainActivity().returnAd()
        Log.d("tester", mInterstitialAd.toString())
        if (mInterstitialAd != null) {
            adDisplayed = true
            mInterstitialAd.show(this)
        }
    }



        val library = intent.getStringExtra("LIBRARY")
        if (library != null) {
            val searchList = Datasource().loadSearches(this)
            progressBar.max = searchList.size
            progressBar.progress = 0
            val progress = progressBar.progress
            val max = searchList.size
            loadingtext.text = "$progress of $max searches completed."
            SearchRunClient().run(searchList, library, this.applicationContext, recyclerView.adapter as ResultsAdaptor, progressBar, loadingtext)
            }
        back.setOnClickListener {
            onBackPressed()
        }

    }



}


