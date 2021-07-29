package com.issam.askworms_demo1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.issam.example.model.Onboarding
import com.issam.example.R

class OnboardingViewPagerAdapter(
    private var context: Context ,
    private var onBoardingList: List<Onboarding>
) : PagerAdapter() {

    override fun getCount(): Int = onBoardingList.size

    override fun isViewFromObject(view: View , `object`: Any): Boolean = view == `object`

    override fun destroyItem(container: ViewGroup , position: Int , `object`: Any) =
        container.removeView(`object` as View)

    override fun instantiateItem(container: ViewGroup , position: Int): Any {

        val view = LayoutInflater.from(context).inflate(R.layout.slide_layout , null);
        val imageView: ImageView = view.findViewById(R.id.imageView)
        val title: TextView = view.findViewById(R.id.slide_heading)
        val desc: TextView = view.findViewById(R.id.slide_desc)

        imageView.setImageResource(onBoardingList[position].tabImgURL)
        title.text = onBoardingList[position].tabTitle
        desc.text = onBoardingList[position].tabDescription

        container.addView(view)

        return view
    }
}