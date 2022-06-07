package com.example.mobileprogramming.UIUX

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mobileprogramming.R

class SportsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnClickListener()
    }

    verride fun onClick(v: View?) {
        super.onClick(v)
        when(v!!.id) {
            R.id.button -> {
                choiceClickFunction(0)
                startResultActivity()
                finish()
            }
        }
    }
}