package com.example.weplay

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import com.example.weplay.databinding.ActivityHeadcountBinding
import com.example.weplay.databinding.ActivitySportsBinding
import com.example.weplay.databinding.PickerDlgBinding
import com.google.android.gms.common.config.GservicesValue.value
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_headcount.*
import java.util.*


class HeadcountActivity : AppCompatActivity() {
    lateinit var binding: ActivityHeadcountBinding
    lateinit var user: FirebaseUser
    lateinit var userList: DatabaseReference

    private var selectedCnt = 0
    private val calendar = Calendar.getInstance()
    private var isTimeSet = false

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

        with(binding) {
            numberPicker.minValue = 1
            numberPicker.maxValue = 100
            numberPicker.value = 3
            selectedCnt = numberPicker.value

            numberPicker.setOnValueChangedListener { numberPicker, old, new ->
                selectedCnt = new + 1
            }

            calendarView.setOnDateChangeListener { calendarView, year, month, day ->
                val dlgBinding = PickerDlgBinding.inflate(layoutInflater)
                val dlgBuilder = AlertDialog.Builder(this@HeadcountActivity)
                dlgBuilder.setView(dlgBinding.root)
                    .setPositiveButton("추가") { _, _ ->
                        val hour = dlgBinding.timePicker.hour
                        val minute = dlgBinding.timePicker.minute
                        calendar.set(year, month, day, hour, minute)
                        isTimeSet = true
                    }
                    .setNegativeButton("취소") { _, _ ->

                    }
                    .show()
            }

            btnCountHead.setOnClickListener {
                if (isTimeSet) {
                    val cnt = selectedCnt
                    val intent = Intent(this@HeadcountActivity, MapsActivity::class.java).apply {
                        putExtra("person", cnt)
                        putExtra("pdate", calendar.timeInMillis)
                        putExtra("field", intent.getStringExtra("field"))
                    }
                    startActivity(intent)
                }
            }
        }


        binding.btnCountHead.setOnClickListener {
//            val cnt = binding.numberPicker.value

        }
    }
}