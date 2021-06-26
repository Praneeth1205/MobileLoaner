package com.loaner.mobile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.loaner.mobile.store.Prefs
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlin.random.Random
import kotlin.random.nextInt

class HomeFragment : Fragment() {

    lateinit var prefs: Prefs

    lateinit var loanAmounts : ArrayList<String>
    var string : String? =""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        prefs = Prefs(requireContext())
        view.apply_now?.setOnClickListener {
            if (prefs.allDetailsFilled != null && prefs.allDetailsFilled!!) {
                val intent = Intent(context, LoanDetailsActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(context, ApplyActivity::class.java)
                startActivity(intent)
            }
        }

        loanAmounts = ArrayList()
        loanAmounts.add("1,000")
        loanAmounts.add("2,000")
        loanAmounts.add("5,000")
        loanAmounts.add("10,000")
        loanAmounts.add("20,000")
        loanAmounts.add("30,000")
        loanAmounts.add("50,000")
        loanAmounts.add("1,00,000")



        for (i in 0..5) {
            val numberFirst = Random.nextInt(60..91)
            val numberSecond = Random.nextInt(11..99)
            val loanNumber = Random.nextInt(0..7)

            string = string + "User $numberFirst******$numberSecond successfully obtained a loan of Rs.${loanAmounts.get(loanNumber)}" + ", "
        }

        view.dataScroll?.setSingleLine()
        view.dataScroll?.isSelected = true
        view.dataScroll?.setHorizontallyScrolling(true)
        view.dataScroll.text = string


        return view
    }
}