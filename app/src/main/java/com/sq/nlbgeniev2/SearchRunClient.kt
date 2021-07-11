package com.sq.nlbgeniev2

import android.content.Context
import android.content.SharedPreferences
import android.widget.ProgressBar
import android.widget.TextView
import com.sq.nlbgeniev2.XMLClient.VolleyCallback
import com.sq.nlbgeniev2.data.BIDTitle
import com.sq.nlbgeniev2.data.ReturnResults
import com.sq.nlbgeniev2.data.XMLParser
import com.google.gson.Gson

class SearchRunClient {
    fun run(searches: ArrayList<Searches>, library: String, context: Context, adaptor: ResultsAdaptor, progressBar: ProgressBar,loadingtext: TextView) {
        data.clear()
        val sharedPreferences: SharedPreferences = context.getSharedPreferences(
            "Results",
            Context.MODE_PRIVATE
        )
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.clear()

        for (search in searches) {
            var BIDTitlelist: List<BIDTitle>
            XMLClient().keywordSearch(search.keyword, search.author, library, search.limit, context, object : VolleyCallback {
                override fun onSuccess(result: String?) {
                    if (result != null) {
                        BIDTitlelist = (XMLParser().searchParse(result.byteInputStream()))
                        if(BIDTitlelist.isNotEmpty()) {
                            for (item in BIDTitlelist) {
                                XMLClient().GetAvailabilityInfo(
                                    item.BID,
                                    context,
                                    object : VolleyCallback {
                                        override fun onSuccess(result: String?) {
                                            if (result != null) {
                                                val answer = XMLParser().availParse(
                                                    result.byteInputStream(),
                                                    library
                                                )
                                                if (answer != null) {
                                                    val result2 =
                                                        ReturnResults(item.Title, answer, library,search.keyword + search.author)
                                                    val gson = Gson()
                                                    val json = gson.toJson(result2)
                                                    editor.putString(result2.titleName, json)
                                                    editor.apply()
                                                    data += result2
                                                    adaptor.notifyDataSetChanged()
                                                    progressBar.progress += 1
                                                    val progress = progressBar.progress
                                                    val max = searches.size
                                                    if (progress==max) {
                                                        loadingtext.text = "Search complete with $progress of $max searches returned."
                                                    }
                                                    else {
                                                        loadingtext.text =
                                                            "$progress of $max searches completed."
                                                    }
                                                }

                                            }

                                        }
                                    })
                            }
                        }
                        if(BIDTitlelist.isEmpty()){
                            progressBar.progress += 1
                            val progress = progressBar.progress
                            val max = searches.size
                            if (progress==max) {
                                loadingtext.text = "Search complete with $progress of $max searches returned."
                            }
                            else {
                                loadingtext.text =
                                    "$progress of $max searches completed."
                            }
                        }
                    }
                }
            })


        }
    }
}