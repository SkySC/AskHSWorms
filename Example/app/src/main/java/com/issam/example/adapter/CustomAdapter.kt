package com.issam.example.com.issam.example.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.issam.example.News
import com.issam.example.R
import de.hdodenhof.circleimageview.CircleImageView

class CustomAdapter (val mylist : ArrayList<News>) : RecyclerView.Adapter<CustomAdapter.ViewHolder> (){

    override fun onCreateViewHolder(p0: ViewGroup , p1: Int): ViewHolder {
        val v:View = LayoutInflater.from(p0.context).inflate(R.layout.recycler_view_item,p0,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(p0: ViewHolder , p1: Int) {
        var  infNews : News = mylist.get(p1)
        p0.newsTitle.text = infNews.titleNews
        p0.newsText.text = infNews.dateNews


    }

    override fun getItemCount(): Int {
        return mylist.size
    }

    class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){

        val newsTitle = itemView.findViewById(R.id.titleNews) as TextView
        val newsText = itemView.findViewById(R.id.textNews) as TextView
        val newsPhoto = itemView.findViewById(R.id.imgNews) as ImageView




    }


}