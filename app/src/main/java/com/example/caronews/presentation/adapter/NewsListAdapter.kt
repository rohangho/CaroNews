package com.example.caronews.presentation.adapter

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.caronews.R
import com.example.caronews.model.NewsListResponse
import com.example.caronews.utils.TimeLogic
import com.example.caronews.utils.TimeLogic.getAgoFromTimeStamp


class NewsListAdapter(private val context:Context) :
    RecyclerView.Adapter<NewsListAdapter.MyViewHolder>() {
    private  var newsList: List<NewsListResponse> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v: View =
            LayoutInflater.from(parent.context).inflate(R.layout.news_content, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load(newsList[position].bannerUrl).into(holder.newsBanner)
        holder.newsTitle.text = newsList[position].title
        holder.newsSubtitle.text = newsList[position].description
        holder.newsPostTime.text = getAgoFromTimeStamp(newsList[position].timeCreated!!)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }


    fun updateList( updatedList:List<NewsListResponse>){
       this.newsList = updatedList
    }

    class MyViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
         val newsTitle: TextView
         val newsSubtitle: TextView
         val newsPostTime: TextView
         val newsBanner: ImageView

        init {
            newsTitle = itemView.findViewById(R.id.newsTitle)
            newsSubtitle = itemView.findViewById(R.id.newsSubtitle)
            newsPostTime = itemView.findViewById(R.id.newsPostTime)
            newsBanner = itemView.findViewById(R.id.newsBanner)

        }
    }

}