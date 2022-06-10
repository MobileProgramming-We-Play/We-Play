package com.example.weplay.party

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weplay.R
import com.example.weplay.SportsActivity
import com.example.weplay.databinding.ActivityMainBinding
import com.example.weplay.databinding.FragmentPartiesBinding
import com.example.weplay.domain.Party
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.Query
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PartiesFragment : Fragment() {
    lateinit var binding: FragmentPartiesBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MyPartyAdapter
    lateinit var rdb: DatabaseReference
    var findQuery = false

    private val activityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK) {
            Toast.makeText(context, "참가했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPartiesBinding.inflate(layoutInflater, container, false)
        init()
        return binding.root
    }

    private fun init(){
        binding.apply {
            layoutManager = LinearLayoutManager(context)
            rdb = Firebase.database.getReference("Parties/party")
            val query: Query = rdb.limitToLast(50)
            val option = FirebaseRecyclerOptions.Builder<Party>()
                .setQuery(query, Party::class.java)
                .build()
            adapter = MyPartyAdapter(option)

            fabMain.setOnClickListener{
                val intent = Intent(activity, SportsActivity::class.java)
                startActivity(intent)
            }

            adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    changeIntent(position)
                }
            }
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter

            findbtn.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb.orderByChild("psports").equalTo(pNameEdit.text.toString())
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }

            val engGu = arrayOf(jongrogu, junggu, yongsangu, seongdonggu, gwangjingu,
                dongdaemungu, jungranggu, seongbukgu, gangbukgu, dobonggu, nowongu, eunpyeonggu,
                seodaemungu, mapogu, yangcheongu, gangseogu, gurogu, geumcheongu, yeongdeungpogu,
                dongjakgu, gwanakgu, seochogu, gangnamgu, songpagu, gangdonggu)

            val koreanGu = arrayOf("종로구", "중구", "용산구", "성동구", "광진구", "동대문구", "중랑구", "성북구",
                "강북구", "도봉구", "노원구", "은평구", "서대문구", "마포구", "양천구", "강서구", "구로구",
                "금천구", "영등포구", "동작구", "관악구", "서초구", "강남구", "송파구", "강동구")

            engGu.forEachIndexed { index, gu ->
                gu.setOnClickListener {
                    if (!findQuery)
                        findQuery = true
                    if (adapter != null)
                        adapter.stopListening()
                    val query: Query =
                        rdb.orderByChild("pcity").equalTo(koreanGu[index])
                    val option = FirebaseRecyclerOptions.Builder<Party>()
                        .setQuery(query, Party::class.java)
                        .build()

                    adapter = MyPartyAdapter(option)
                    adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            changeIntent(position)
                        }
                    }
                    recyclerView.adapter = adapter
                    adapter.startListening()
                }
            }
            ///지역 버튼
            all.setOnClickListener {
                if (!findQuery)
                    findQuery = true
                if (adapter != null)
                    adapter.stopListening()
                val query: Query =
                    rdb
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                    override fun onItemClick(position: Int) {
                        changeIntent(position)
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }

        }
        adapter.startListening()
    }

    private fun changeIntent(position: Int) {
        val intent = Intent(activity, PartyContentActivity::class.java)
        val party = adapter.getItem(position)
        intent.putExtra("party", party)
        intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
        activityResultLauncher.launch(intent)
    }
}