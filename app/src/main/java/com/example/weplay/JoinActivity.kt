package com.example.weplay

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.weplay.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {
    lateinit var binding: ActivityJoinBinding
    lateinit var auth: FirebaseAuth
    lateinit var userList: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initLayout()
    }

    private fun initLayout() {
        auth = Firebase.auth
        userList = Firebase.database.getReference("Users")

        binding.joinbtn.setOnClickListener {
            val email = binding.emailedit.text.toString()
            val password = binding.pwedit.text.toString()
            val nickname = binding.nicknameeidt.text.toString()
            val age = binding.ageedit.text.toString().toInt()
            var gender = 0
            if (binding.female.isChecked) {
                gender = 1
            }

            // Firebase Authentication에 추가 (email, password)
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser

                        // Firebase Realtime Database에 추가(email, nickname, age, gender)
                        val profile = User(email, nickname, age, gender)
                        userList.child(user?.uid.toString()).setValue(profile)

                        Toast.makeText(this, "가입완료", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        try {
                            throw task.exception!!
                        } catch (e: FirebaseAuthWeakPasswordException) {
                            Toast.makeText(this, "비밀번호는 6자리 이상이어야 합니다.", Toast.LENGTH_SHORT).show()
                        } catch (e: FirebaseAuthInvalidCredentialsException) {
                            Toast.makeText(this, "이메일 형식이 올바르지 않습니다.", Toast.LENGTH_SHORT).show()
                        } catch (e: FirebaseAuthUserCollisionException) {
                            Toast.makeText(this, "이미 존재하는 email입니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
        }

        binding.gotoLoginbtn.setOnClickListener {
            finish()
        }
    }
}