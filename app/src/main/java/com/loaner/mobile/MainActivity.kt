package com.loaner.mobile

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.loaner.mobile.store.Prefs

class MainActivity : AppCompatActivity() {

    lateinit var prefs : Prefs

    private val onNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    moveToFragment(HomeFragment())
                    return@OnNavigationItemSelectedListener true
                }
                R.id.nav_profile -> {
                    if (prefs.allDetailsFilled!=null && prefs.allDetailsFilled!!){
                        val intent = Intent(this,LoanDetailsActivity::class.java)
                        startActivity(intent)
                    }else {
                        val intent = Intent(this,ApplyActivity::class.java)
                        startActivity(intent)
                    }
                    return@OnNavigationItemSelectedListener false
                }
                R.id.nav_more -> {
                    if (prefs.isLoggined!=null && !prefs.isLoggined!!){
                        val intent = Intent(this,LoginActivity::class.java)
                        startActivity(intent)
                    }else {
                        moveToFragment(MoreFragment())
                    }
                    return@OnNavigationItemSelectedListener true
                }

            }
            false
        }

    private fun moveToFragment(fragment: Fragment) {
        val fragmentTrans = supportFragmentManager.beginTransaction()
        fragmentTrans.replace(R.id.fragment_container, fragment)
        fragmentTrans.commit()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        prefs = Prefs(this)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        moveToFragment(HomeFragment())

    }
}