package com.sq.nlbgeniev2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdaptor(private val listitems: ArrayList<Searches>,val context: Context,private val onItemClicked: (Searches) -> Unit) : RecyclerView.Adapter<MyAdaptor.ViewHolder>()  {
    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item_tworow, parent, false)
        return ViewHolder(viewHolder) {
            onItemClicked(listitems[it])
        }
    }

    override fun getItemCount(): Int{
       return listitems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if(listitems[position].author.isBlank()) {
            holder.SearchQuery.text = listitems[position].keyword
            holder.SearchNumber.text = "Number of searches:".plus(listitems[position].limit)
        }
        else if(listitems[position].keyword.isBlank()) {
            holder.SearchQuery.text = listitems[position].author
            holder.SearchNumber.text = "Number of searches:".plus(listitems[position].limit)
        }
        else{
            holder.SearchQuery.text = listitems[position].keyword.plus(" by ").plus(listitems[position].author)
            holder.SearchNumber.text = "Number of searches:".plus(listitems[position].limit)
        }
    }

    class ViewHolder(view: View,onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
        val SearchQuery = view.findViewById<TextView>(R.id.mtrl_list_item_text)
        val SearchNumber = view.findViewById<TextView>(R.id.mtrl_list_item_secondary_text)
        init {
            itemView.setOnClickListener {
                onItemClicked(adapterPosition)
            }

        }
    }
}


