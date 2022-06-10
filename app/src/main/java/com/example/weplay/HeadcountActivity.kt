package com.example.weplay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weplay.databinding.ActivityHeadcountBinding
import com.example.weplay.databinding.PickerDlgBinding
import com.example.weplay.domain.BaseCity

import java.util.*
import kotlin.collections.ArrayList


class HeadcountActivity : AppCompatActivity() {
    lateinit var binding: ActivityHeadcountBinding

    private var selectedCnt = 0
    private val calendar = Calendar.getInstance()
    private var isTimeSet = false
    private var selectedGu = ""

    private lateinit var adapter: BaseCityAdapter
    private val gus = ArrayList<BaseCity>()
    private val koreanGu = arrayOf("종로구", "중구", "용산구", "성동구", "광진구", "동대문구", "중랑구", "성북구",
        "강북구", "도봉구", "노원구", "은평구", "서대문구", "마포구", "양천구", "강서구", "구로구",
        "금천구", "영등포구", "동작구", "관악구", "서초구", "강남구", "송파구", "강동구")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeadcountBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()
        initRecyclerView()
        initLayout()
    }

    private fun initData() {
        koreanGu.forEach { guName -> gus.add(BaseCity(guName)) }
    }

    private fun initRecyclerView() {
        with(binding) {
            adapter = BaseCityAdapter(gus)
            adapter.listener = object : BaseCityAdapter.OnItemClickListener {
                override fun onItemClick(data: BaseCity, position: Int) {
                    selectedGu = data.name
                    selectedGuArea.text = selectedGu
                }
            }

            seoulGuRecyclerView.layoutManager = LinearLayoutManager(
                this@HeadcountActivity,
                LinearLayoutManager.VERTICAL, false
            )
            seoulGuRecyclerView.adapter = adapter
        }
    }

    private fun initLayout() {

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
                if (isTimeSet && selectedGu.isNotEmpty()) {
                    val cnt = selectedCnt
                    val intent = Intent(this@HeadcountActivity, MapsActivity::class.java).apply {
                        putExtra("person", cnt)
                        putExtra("pdate", calendar.timeInMillis)
                        putExtra("field", intent.getStringExtra("field"))
                        putExtra("pcity", selectedGu)
                    }
                    startActivity(intent)
                    finish()
                }
            }
        }

    }
}