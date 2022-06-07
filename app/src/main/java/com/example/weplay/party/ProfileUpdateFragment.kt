package com.example.weplay.party

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.weplay.LoginActivity
import com.example.weplay.R
import com.example.weplay.User
import com.example.weplay.databinding.FragmentProfileBinding
import com.example.weplay.databinding.FragmentProfileUpdateBinding
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileUpdateFragment : Fragment() {
    lateinit var binding: FragmentProfileUpdateBinding
    lateinit var user: FirebaseUser
    lateinit var userList: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProfileUpdateBinding.inflate(layoutInflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        binding.apply {
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

                Toast.makeText(activity, "수정완료", Toast.LENGTH_SHORT).show()
                activity?.supportFragmentManager!!.beginTransaction()
                    .remove(this@ProfileUpdateFragment).commit()
                activity?.supportFragmentManager!!.popBackStack()
            }

            binding.cancelbtn.setOnClickListener {
                activity?.supportFragmentManager!!.beginTransaction()
                    .remove(this@ProfileUpdateFragment).commit()
                activity?.supportFragmentManager!!.popBackStack()
            }
        }
    }
}