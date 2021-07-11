package com.sq.nlbgeniev2


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.sq.nlbgeniev2.data.Datasource
import com.sq.nlbgeniev2.data.ReturnResults
import kotlin.system.exitProcess


lateinit var linearLayoutManager: LinearLayoutManager
val data: MutableList<ReturnResults> = mutableListOf<ReturnResults>().toMutableList()


class MainActivity : AppCompatActivity() {
    companion object{ var mInterstitialAd: InterstitialAd? = null }
    private final var TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this) {}
        val adRequest = AdRequest.Builder().build()

        InterstitialAd.load(this,AdWordsID, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                mInterstitialAd = interstitialAd
            }
        })




        val spinnerPrefs = getSharedPreferences("spinnervalue",Context.MODE_PRIVATE)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        val queries: ArrayList<Searches> = Datasource().loadSearches(this)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val button = findViewById<Button>(R.id.button)
        val helpButton = findViewById<ImageButton>(R.id.helpButton)
        val newsearch = findViewById<FloatingActionButton>(R.id.floatingActionButton)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = MyAdaptor(queries, this){
            val intent = Intent(this, EditsearchActivity::class.java)
            intent.putExtra("keyword", it.keyword)
            intent.putExtra("author", it.author)
            intent.putExtra("limit", it.limit)
            startActivity(intent)
        }

        ArrayAdapter.createFromResource(
                this, R.array.library_branches, android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        spinner.onItemSelectedListener = object:AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val prefsEditor = spinnerPrefs.edit()
                prefsEditor.putInt("Value", position)
                prefsEditor.apply()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                val prefsEditor = spinnerPrefs.edit()
                prefsEditor.putInt("Value", 0)
                prefsEditor.apply()
            }

        }


        spinner.setSelection(spinnerPrefs.getInt("Value",0))

        newsearch.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, NewsearchActivity::class.java)
            startActivity(intent)
        })

        button.setOnClickListener(View.OnClickListener {
            data.clear()
            val intent = Intent(this, ResultsDisplay::class.java)
            intent.putExtra("LIBRARY", spinner.selectedItem.toString().split(" ", ignoreCase = true, limit = 2)[0])

            startActivity(intent)
        })

        helpButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ScreenSlidePagerActivity::class.java)
            startActivity(intent)
        })
    val firstTime = getSharedPreferences("firstTime",0)
    if(firstTime.getBoolean("myFirstTime",true)){
        firstTime.edit().putBoolean("myFirstTime", false).apply()
        val intent = Intent(this, ScreenSlidePagerActivity::class.java)
        startActivity(intent)
    }

    }

    private var doubleBackToExitPressedOnce = false
    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            exitProcess(0);
        }

        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({
            doubleBackToExitPressedOnce = false
        }, 2000)

    }


    fun returnAd(): InterstitialAd? {
        return mInterstitialAd
    }
}



