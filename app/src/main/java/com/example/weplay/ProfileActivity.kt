package com.example.weplay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weplay.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        user = Firebase.auth.currentUser!!
        val id = user.uid

        Firebase.database.getReference("Users/$id").get().addOnSuccessListener {
            binding.apply {
                emailtext.text = it.child("email").value.toString()
                nicknametext.text = it.child("nickName").value.toString()
                agetext.text = it.child("age").value.toString()

                val gender = it.child("gender").value.toString().toInt()
                if (gender == 0) {
                    gendertext.text = "남자"
                } else {
                    gendertext.text = "여자"
                }
            }

            binding.updatebtn.setOnClickListener {
                val intent = Intent(this, ProfileUpdateActivity::class.java)
                startActivity(intent)
            }

            binding.gotomainbtn.setOnClickListener {
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        initLayout()
    }
}
