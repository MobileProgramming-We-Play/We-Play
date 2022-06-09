//package com.example.weplay
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.widget.Toast
//import com.example.weplay.databinding.ActivityMainBinding
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.auth.FirebaseUser
//import com.google.firebase.auth.ktx.auth
//import com.google.firebase.database.ktx.database
//import com.google.firebase.ktx.Firebase
//
//class MainActivity2 : AppCompatActivity() {
//    lateinit var binding: ActivityMainBinding
//    lateinit var auth: FirebaseAuth
//    lateinit var user: FirebaseUser
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        initLayout()
//    }
//
//    private fun initLayout() {
//        auth = Firebase.auth
//        user = auth.currentUser!! // 현재 사용자 (user.uid <- id값 가져올 수 있음)
//
//
//
//
//
//        Firebase.database.getReference("Users/${user.uid}").get().addOnSuccessListener {
//            binding.nicknametext.text = it.child("nickName").value.toString()
//        }
//
//        binding.logoutbtn.setOnClickListener {
//            auth.signOut()
//            Toast.makeText(this, "로그아웃 완료", Toast.LENGTH_SHORT).show()
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
//            finish()
//        }
//
//        binding.profilebtn.setOnClickListener {
//            val intent = Intent(this, ProfileActivity::class.java)
//            startActivity(intent)
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        user = Firebase.auth.currentUser!!
//        Firebase.database.getReference("Users/${user.uid}").get().addOnSuccessListener {
//            binding.nicknametext.text = it.child("nickName").value.toString()
//        }
//    }
//}