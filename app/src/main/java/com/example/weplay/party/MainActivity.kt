package com.example.weplay.party

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weplay.party.MyPartyAdapter
import com.example.weplay.databinding.ActivityMainBinding
import com.example.weplay.domain.Party
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MyPartyAdapter
    lateinit var rdb:DatabaseReference
    var findQuery = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rdb = Firebase.database.getReference("Parties/party")
        val query: Query = rdb.limitToLast(50)
        val option = FirebaseRecyclerOptions.Builder<Party>()
            .setQuery(query, Party::class.java)
            .build()
        adapter = MyPartyAdapter(option)
        adapter.itemClickListener = object:MyPartyAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                binding.apply {
                    pNameEdit.setText(adapter.getItem(position).psports)
                }
            }
        }
        binding.apply {
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter

            findbtn.setOnClickListener {
                if(!findQuery)
                    findQuery = true
                if(adapter!=null)
                    adapter.stopListening()
                val query: Query = rdb.orderByChild("psports").equalTo(pNameEdit.text.toString())
                val option = FirebaseRecyclerOptions.Builder<Party>()
                    .setQuery(query, Party::class.java)
                    .build()
                adapter = MyPartyAdapter(option)
                adapter.itemClickListener = object:MyPartyAdapter.OnItemClickListener{
                    override fun onItemClick(position: Int) {
                        binding.apply {
                            pNameEdit.setText(adapter.getItem(position).psports)
                        }
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }

        }
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }
    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}