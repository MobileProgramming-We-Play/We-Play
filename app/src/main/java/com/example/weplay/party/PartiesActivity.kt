package com.example.weplay.party

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weplay.databinding.ActivityPartiesBinding
import com.example.weplay.domain.Party
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class PartiesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPartiesBinding
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: PartiesAdapter
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPartiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    private fun init() {
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        database = Firebase.database.getReference("Parties/party")
        val query = database.limitToLast(50)
        val option = FirebaseRecyclerOptions.Builder<Party>()
            .setQuery(query, Party::class.java)
            .build()

        adapter = PartiesAdapter(option)
        adapter.itemClickListener = object : PartiesAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val intent = Intent(this@PartiesActivity, PartyContentActivity::class.java)
                val party = adapter.getItem(position)
                intent.putExtra("party", party)
                intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                startActivity(intent)
            }
        }

        with(binding) {
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter
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

    override fun onResume() {
        super.onResume()
        adapter.startListening()
    }
}