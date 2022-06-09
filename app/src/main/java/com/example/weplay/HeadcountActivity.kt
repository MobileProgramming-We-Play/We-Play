package com.example.weplay

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.NumberPicker
import com.example.weplay.databinding.ActivityHeadcountBinding
import com.example.weplay.databinding.ActivitySportsBinding
import com.google.android.gms.common.config.GservicesValue.value
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_headcount.*


class HeadcountActivity : AppCompatActivity() {
    lateinit var binding: ActivityHeadcountBinding
    lateinit var user: FirebaseUser
    lateinit var userList: DatabaseReference

    private var selectedCnt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeadcountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
    }

    private fun initLayout() {
        user = Firebase.auth.currentUser!!
        userList = Firebase.database.getReference("Parties/party")
        val id = user.uid

        userList.child(id).get().addOnSuccessListener {
            binding.apply {
//                numberPicker.value
                numberPicker.minValue = 1
                numberPicker.maxValue = 20
                numberPicker.value = 1
                numberPicker.setOnValueChangedListener { numberPicker, old, new ->
                    selectedCnt = new
                }
            }
        }


        binding.btnCountHead.setOnClickListener {
//            val cnt = binding.numberPicker.value
            val cnt = selectedCnt
            val intent = Intent(this, MapsActivity::class.java).apply {
                putExtra("person", cnt)
                putExtra("field", intent.getStringExtra("field"))
            }
            startActivity(intent)
        }
    }
}