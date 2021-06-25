package com.loaner.mobile.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.loaner.mobile.R
import com.loaner.mobile.model.DataSource


class SliderAdapter(private val sliderList : List<DataSource>)
    : RecyclerView.Adapter<SliderAdapter.ViewHolder>() {

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        private val title : TextView = itemView.findViewById(R.id.title_slider)
        private val desc : TextView = itemView.findViewById(R.id.slider_desc)
        private val imageUrl : ImageView = itemView.findViewById(R.id.slider_imageUrl)

        fun bind(sliders : DataSource){
            title.text = sliders.title
            desc.text = sliders.desc
            imageUrl.setImageResource(sliders.imageUrl)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.intro_slider_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return sliderList.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(sliderList[position])
    }

}