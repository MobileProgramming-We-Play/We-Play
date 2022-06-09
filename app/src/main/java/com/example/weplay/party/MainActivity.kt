package com.example.weplay.party

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.weplay.HeadcountActivity
import com.example.weplay.R
import com.example.weplay.SportsActivity
import com.example.weplay.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_parties.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth
    lateinit var user: FirebaseUser
    lateinit var partiesFragment: PartiesFragment
    lateinit var myPartiesFragment: MyPartiesFragment
    lateinit var profileFragment: ProfileFragment
    lateinit var profileUpdateFragment: ProfileUpdateFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()

//        fabMain.setOnClickListener {
//            val intent = Intent(this, SportsActivity::class.java)
//            startActivity(intent)
//        }
    }

    private fun init() {
        auth = Firebase.auth
        user = auth.currentUser!!

        partiesFragment = PartiesFragment()
        myPartiesFragment = MyPartiesFragment()
        profileFragment = ProfileFragment()
        profileUpdateFragment = ProfileUpdateFragment()

        binding.tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, partiesFragment).commit()
                    }

                    1->{
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainerView, myPartiesFragment).commit()
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