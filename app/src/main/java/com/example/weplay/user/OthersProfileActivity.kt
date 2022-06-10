package com.example.weplay.user

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weplay.databinding.ActivityOthersProfileBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

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

