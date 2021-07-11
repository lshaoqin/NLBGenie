package com.sq.nlbgeniev2

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

private const val NUM_PAGES = 5

class ScreenSlidePageFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.help_view, container, false)
        val image: ImageView = view.findViewById(R.id.imageview)
        val text: TextView = view.findViewById(R.id.desc)

        val step = arguments?.getInt("stepNo")?.plus(1)
        if(step == 1){
            image.setImageResource(R.drawable.screenshot1)
            text.text = resources.getString(R.string.instruction1)
        }
        else if(step == 2) {
            image.setImageResource(R.drawable.screenshot2)
            text.text = resources.getString(R.string.instruction2)
            text.textSize = 18F
        }
        else if(step == 3) {
            image.setImageResource(R.drawable.screenshot3)
            text.text = resources.getString(R.string.instruction3)
        }
        else if(step == 4) {
            image.setImageResource(R.drawable.screenshot4)
            text.text = resources.getString(R.string.instruction4)
        }
        else if(step == 5){
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }


        return view
    }




    companion object {

        // Method for creating new instances of the fragment
        fun newInstance(stepNo:Int): ScreenSlidePageFragment {

            // Store the movie data in a Bundle object
            val args = Bundle()
            args.putInt("stepNo",stepNo)


            // Create a new MovieFragment and set the Bundle as the arguments
            // to be retrieved and displayed when the view is created
            val fragment = ScreenSlidePageFragment()
            fragment.arguments = args
            return fragment
        }
    }
}





class ScreenSlidePagerActivity : FragmentActivity() {

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private lateinit var mPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.viewpager)

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = findViewById(R.id.pager)

        // The pager adapter, which provides the pages to the view pager widget.
        val pagerAdapter = ScreenSlidePagerAdapter(this)
        mPager.adapter = pagerAdapter
    }

    override fun onBackPressed() {
        if (mPager.currentItem == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed()
        } else {
            // Otherwise, select the previous step.
            mPager.currentItem = mPager.currentItem - 1
        }
    }


    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment = ScreenSlidePageFragment.newInstance(position)
    }

    companion object {
        const val TAG = "HelpPopup"
    }
}







