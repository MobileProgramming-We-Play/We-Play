package com.example.weplay

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weplay.databinding.ActivityProfileUpdateBinding
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class ProfileUpdateActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileUpdateBinding
    lateinit var user: FirebaseUser
    lateinit var userList: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        user = Firebase.auth.currentUser!!
        userList = Firebase.database.getReference("Users")
        val id = user.uid

        userList.child(id).get().addOnSuccessListener {
            binding.apply {
                emailedit.setText(it.child("email").value.toString())
                nicknameeidt.setText(it.child("nickName").value.toString())
                ageedit.setText(it.child("age").value.toString())

                val gender = it.child("gender").value.toString().toInt()

                if (gender == 0) {
                    male.isChecked = true
                } else if (gender == 1) {
                    female.isChecked = true
                }
            }
        }

        binding.savebtn.setOnClickListener {
            val email = binding.emailedit.text.toString()
            val nickName = binding.nicknameeidt.text.toString()
            val age = binding.ageedit.text.toString().toInt()
            var gender = 0

            if (binding.female.isChecked) {
                gender = 1
            }

            val profile = User(email, nickName, age, gender)
            userList.child(user.uid).setValue(profile)

            Toast.makeText(this, "수정완료", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.cancelbtn.setOnClickListener {
            finish()
        }
    }
}