package com.sq.nlbgeniev2.data

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.sq.nlbgeniev2.Searches
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException


class Datasource {
    fun loadSearches(context: Context):ArrayList<Searches>{
        val finalList:ArrayList<Searches> = arrayListOf<Searches>()
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE)
        val sharedPreferenceIds = sharedPreferences.all.map { it.key } //returns List<String>
        for (item in sharedPreferenceIds){
                val gson = Gson()
                val json: String? = sharedPreferences.getString(item, "An error has occurred :(")
                try {
                    val searchItem: Searches = gson.fromJson(json, Searches::class.java)
                    finalList.add(searchItem)
                }
                catch(e:JsonSyntaxException){
                    Toast.makeText(context, "There was an error loading your wishlist. Some data may have been deleted as a result.", Toast.LENGTH_SHORT).show()
                    val editor: SharedPreferences.Editor = sharedPreferences.edit()
                    editor.remove(item)
                    editor.apply()
            }

        }
        return finalList


    }


}