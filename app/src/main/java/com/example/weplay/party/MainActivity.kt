package com.example.weplay.party

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weplay.R
import com.example.weplay.party.MyPartyAdapter
import com.example.weplay.databinding.ActivityMainBinding
import com.example.weplay.domain.Party
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.material.tabs.TabLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    lateinit var partiesFragment: PartiesFragment
    lateinit var profileFragment: ProfileFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        partiesFragment = PartiesFragment()
        profileFragment = ProfileFragment()

        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, partiesFragment).commit()
                    }

                    2-> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, profileFragment).commit()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })
    }

}