package com.loaner.mobile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.loaner.mobile.store.Prefs
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {
    lateinit var prefs: Prefs

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        prefs = Prefs(this)

        splash_image?.alpha = 0f
        splash_image?.animate()?.setDuration(3000)?.alpha(1f)?.withEndAction {
            if(prefs.introShown!=null && prefs.introShown!!){
                if (prefs.isLoggined!=null && prefs.isLoggined!!){
                    val intent = Intent(this,MainActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                    finish()
                }else{
                    val intent = Intent(this,LoginActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                    finish()
                }
            }else{
                val intent = Intent(this,IntroScreenActivity::class.java)
                startActivity(intent)
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
                finish()
            }

        }
    }
}