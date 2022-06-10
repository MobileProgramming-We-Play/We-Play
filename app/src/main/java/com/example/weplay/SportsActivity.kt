package com.example.weplay

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.weplay.databinding.ActivitySportsBinding

class SportsActivity : AppCompatActivity() {
    lateinit var binding: ActivitySportsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySportsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initLayout()
    }

    private fun initLayout() {

        binding.sportsbtn.setOnClickListener {
            val field = binding.sportsedit.text.toString()
            val intent = Intent(this, HeadcountActivity::class.java).apply {
                putExtra("field", field)
            }
            startActivity(intent)
            finish()
        }
    }
}


