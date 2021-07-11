package com.sq.nlbgeniev2

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.sq.nlbgeniev2.data.ReturnResults


class ResultsAdaptor(private val listitems: List<ReturnResults>, val context: Context) : RecyclerView.Adapter<ResultsAdaptor.ViewHolder>()  {
        override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
            Log.d("create","onCreateViewHolder")
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recyclerview_item_tworow, parent, false))
        }

        override fun getItemCount(): Int{
            return listitems.size
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            holder.title.text = listitems[position].titleName
            holder.details.text = listitems[position].callNumber.plus(", ").plus(listitems[position].branchName)
            holder.searchDetails = listitems[position].searchTerm
            }


        class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
            val title = view.findViewById<TextView>(R.id.mtrl_list_item_text)
            val details = view.findViewById<TextView>(R.id.mtrl_list_item_secondary_text)
            var searchDetails = ""
            private val toast: Toast = Toast.makeText(
                view.context,
                "",
                Toast.LENGTH_SHORT)


            //3
            init {
                view.setOnClickListener(this)
            }

            //4
            override fun onClick(v: View?) {
                toast.setText("Search terms: $searchDetails")
                toast.show()
            }

            companion object {
                //5
                private val PHOTO_KEY = "PHOTO"
            }
        }
}



