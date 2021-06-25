package com.loaner.mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.loaner.mobile.adapter.SliderAdapter
import com.loaner.mobile.model.DataSource
import com.loaner.mobile.store.Prefs
import kotlinx.android.synthetic.main.activity_intro_screen.*

class IntroScreenActivity : AppCompatActivity() {
    lateinit var prefs: Prefs

    private val sliderAdapter = SliderAdapter(
        listOf(
            DataSource(
                "Very Less Documents",
                "Aadhar card/Pan card/Driving Licence",
                R.drawable.report
            ),
            DataSource(

                "Simple Process",
                "Just three steps to get loan",
                R.drawable.credit
            ),
            DataSource(

                "Easy and Efficient loan",
                "Get instant loan within minutes",
                R.drawable.moneybag
            ),
        )

    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_screen)

        prefs = Prefs(this)

        skipIntro.setOnClickListener {
            gotoMainActivity()
        }

        sliderViewPager.adapter = sliderAdapter
        setUpIndicators()
        setUpCurrentIndicator(0)

        sliderViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                setUpCurrentIndicator(position)
            }
        })
        buttonNext.setOnClickListener {
            if (sliderViewPager.currentItem + 1 < sliderAdapter.itemCount) {
                sliderViewPager.currentItem += 1
            } else {
                gotoMainActivity()
            }
        }


    }

    private fun gotoMainActivity() {
        if (prefs.isLoggined != null && prefs.isLoggined!!) {
            prefs.introShown = true
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            prefs.introShown = true
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun setUpIndicators() {
        val indicators = arrayOfNulls<ImageView>(sliderAdapter.itemCount)
        val layoutParams: LinearLayout.LayoutParams =
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        layoutParams.setMargins(8, 0, 8, 0)
        for (i in indicators.indices) {
            indicators[i] = ImageView(applicationContext)
            indicators[i].apply {
                this?.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )
                this?.layoutParams = layoutParams
            }
            indicators_sliding.addView(indicators[i])
        }
    }

    private fun setUpCurrentIndicator(index: Int) {
        val childCount = indicators_sliding.childCount
        for (i in 0 until childCount) {
            val imageView = indicators_sliding[i] as ImageView
            if (i == index) {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_active
                    )
                )
            } else {
                imageView.setImageDrawable(
                    ContextCompat.getDrawable(
                        applicationContext,
                        R.drawable.indicator_inactive
                    )
                )

            }
        }

    }
}