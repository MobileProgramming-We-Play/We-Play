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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth
    lateinit var user: FirebaseUser
    lateinit var partiesFragment: PartiesFragment
    lateinit var profileFragment: ProfileFragment
    lateinit var profileUpdateFragment: ProfileUpdateFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        auth = Firebase.auth
        user = auth.currentUser!!

        partiesFragment = PartiesFragment()
        profileFragment = ProfileFragment()
        profileUpdateFragment = ProfileUpdateFragment()

        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, partiesFragment).commit()
                    }

                    2 -> {
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

    fun changeToProfileUpdateFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerView, profileUpdateFragment)
            .addToBackStack(null).commit()
    }

}