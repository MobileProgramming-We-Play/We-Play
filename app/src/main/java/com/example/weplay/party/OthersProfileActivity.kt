package com.example.weplay.party

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weplay.LoginActivity
import com.example.weplay.R
import com.example.weplay.databinding.ActivityOthersProfileBinding
import com.example.weplay.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_profile.*

class OthersProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityOthersProfileBinding
    lateinit var userList: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOthersProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        val id = intent.getStringExtra("uid")

        Firebase.database.getReference("Users/$id").get().addOnSuccessListener {
            binding.apply {
                nicknametext.text = it.child("nickName").value.toString()
                agetext.text = it.child("age").value.toString()

                val gender = it.child("gender").value.toString().toInt()
                if (gender == 0) {
                    gendertext.text = "남자"
                } else {
                    gendertext.text = "여자"
                }
            }
        }
    }
}

