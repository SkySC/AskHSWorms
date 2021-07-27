package com.issam.askworms_demo1.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.issam.askworms_demo1.model.OnboardingData
import com.issam.example.R

class OnboardingViewPagerAdapter(
    private var context: Context ,
    private var onBoardingList: List<OnboardingData>
) : PagerAdapter() {

    override fun getCount(): Int = onBoardingList.size

    override fun isViewFromObject(view: View , `object`: Any): Boolean = view == `object`

    override fun destroyItem(container: ViewGroup , position: Int , `object`: Any) =
        container.removeView(`object` as View)

    override fun instantiateItem(container: ViewGroup , position: Int): Any {

        val view = LayoutInflater.from(context).inflate(R.layout.slide_layout , null);

        val imageView: ImageView
        val title: TextView
        val desc: TextView

        imageView = view.findViewById(R.id.imageView)
        title = view.findViewById(R.id.slide_heading)
        desc = view.findViewById(R.id.slide_desc)

        imageView.setImageResource(onBoardingList[position].imgUrl)
        title.text = onBoardingList[position].title
        desc.text = onBoardingList[position].desc

        container.addView(view)

        return view
    }
}