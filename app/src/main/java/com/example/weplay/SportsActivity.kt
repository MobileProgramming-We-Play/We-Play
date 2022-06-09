package com.example.weplay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.weplay.databinding.ActivityProfileUpdateBinding
import com.example.weplay.databinding.ActivitySportsBinding
import com.example.weplay.domain.Party
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_headcount.*
import kotlinx.android.synthetic.main.activity_sports.*

class SportsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySportsBinding
    lateinit var user: FirebaseUser
    lateinit var userList: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sports)

        sportsbtn.setOnClickListener {
            val intent = Intent(this, HeadcountActivity::class.java)
            startActivity(intent)
        }
        initLayout()
    }

    private fun initLayout() {
        user = Firebase.auth.currentUser!!
        userList = Firebase.database.getReference("Parties/party")
        val id = user.uid

        userList.child(id).get().addOnSuccessListener {
            binding.apply {
                sportsedit.setText(it.child("sports").value.toString())
            }
        }

        binding.sportsbtn.setOnClickListener {
            val psports = binding.sportsedit.text.toString()
            finish()
        }
    }
}


