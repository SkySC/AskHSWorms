package com.issam.example.com.issam.example.adapter

import android.content.Context
import com.google.firebase.storage.FirebaseStorage
import com.issam.example.com.issam.example.model.News
import com.issam.example.R
import com.issam.example.com.issam.example.glide.GlideApp
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.recycler_view_item.*

class NewsAdapter(val news: News , val context: Context) : Item() {

    private val storageInstance: FirebaseStorage by lazy {
        FirebaseStorage.getInstance()
    }

    override fun bind(viewHolder: GroupieViewHolder , position: Int) {
        viewHolder.titleNews.text = news.titleNews
        viewHolder.textNews.text = news.ContenuNews
        viewHolder.dateNews.text = news.dateNews

        if (news.mNewsPhoto.isNotEmpty()) {
            GlideApp.with(context)
                .load(storageInstance.getReference(news.mNewsPhoto))
                .into(viewHolder.imgNews)
        } else {
            viewHolder.imgNews.setImageResource(R.drawable.newsbild)
        }
    }

    override fun getLayout(): Int {
        return R.layout.recycler_view_item
    }
}