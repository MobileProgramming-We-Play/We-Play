package com.example.weplay.party

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weplay.R
import com.example.weplay.databinding.ActivityMainBinding
import com.example.weplay.databinding.FragmentPartiesBinding
import com.example.weplay.domain.Party
import com.firebase.ui.database.FirebaseRecyclerOptions
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

            adapter.itemClickListener = object : MyPartyAdapter.OnItemClickListener {
                override fun onItemClick(position: Int) {
                    val intent = Intent(activity, PartyContentActivity::class.java)
                    val party = adapter.getItem(position)
                    intent.putExtra("party", party)
                    intent.putExtra("firebaseIndex", "Parties/party/${party.pid}")
                    startActivity(intent)
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
                        binding.apply {
                            pNameEdit.setText(adapter.getItem(position).psports)
                        }
                    }
                }
                recyclerView.adapter = adapter
                adapter.startListening()
            }
        }
        adapter.startListening()
    }

//    override fun onStart() {
//        super.onStart()
//        adapter.startListening()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        adapter.stopListening()
//    }

}