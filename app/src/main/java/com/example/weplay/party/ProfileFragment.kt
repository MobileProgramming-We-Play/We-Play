package com.example.weplay.party

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.weplay.LoginActivity
import com.example.weplay.ProfileUpdateActivity
import com.example.weplay.R
import com.example.weplay.databinding.FragmentPartiesBinding
import com.example.weplay.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    lateinit var auth: FirebaseAuth
    lateinit var user: FirebaseUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_profile, container, false)

        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        init()
        return binding.root
    }

    private fun init() {
        binding.apply {
            auth = Firebase.auth
            user = auth.currentUser!!
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
                    (activity as MainActivity).changeToProfileUpdateFragment()
                }

                binding.logoutbtn.setOnClickListener {
                    auth.signOut()
                    Toast.makeText(activity, "로그아웃 완료", Toast.LENGTH_SHORT).show()
                    val intent = Intent(activity, LoginActivity::class.java)
                    startActivity(intent)
                    activity?.finish()
                }
            }
        }
    }
}