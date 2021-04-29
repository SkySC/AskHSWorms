package com.issam.askworms_demo1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.issam.example.R
import com.issam.askworms_demo1.model.OnBoardingData
import androidx.viewpager.widget.PagerAdapter

class OnBoardingViewPagerAdapter(private var context : Context, private var onBoardingDataList : List <OnBoardingData>) : PagerAdapter() {

    override fun getCount() : Int = onBoardingDataList.size

    override fun isViewFromObject(view : View, `object` : Any) : Boolean = (view == `object`)

    override fun destroyItem(container : ViewGroup, position : Int, `object` : Any) = container.removeView(`object` as View)

    override fun instantiateItem(container : ViewGroup, position : Int) : Any {

        val view = LayoutInflater.from(context).inflate(R.layout.slide_layout, null);
        val onBoardingImage = view.findViewById<ImageView>(R.id.imageView)
        val onBoardingTitle = view.findViewById<TextView>(R.id.slide_heading)
        val onBoardingDescription = view.findViewById<TextView>(R.id.slide_desc)

        onBoardingImage.setImageResource(onBoardingDataList[position].imgUrl)
        onBoardingTitle.text = onBoardingDataList[position].title
        onBoardingDescription.text = onBoardingDataList[position].desc

        container.addView(view)
        return view
    }
}