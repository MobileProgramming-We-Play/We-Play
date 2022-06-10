package com.example.weplay.party.read

import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weplay.databinding.ActivityChattingBinding
import com.example.weplay.domain.Message
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChattingActivity : AppCompatActivity() {
    lateinit var binding: ActivityChattingBinding
    lateinit var layoutManager: LinearLayoutManager
    lateinit var adapter: MyMessageAdapter
    lateinit var chattingRoom: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChattingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val i = intent
        val roomID = i.getIntExtra("roomID", -1)
        chattingRoom = Firebase.database.getReference("ChattingRooms/$roomID")

        val query = chattingRoom.limitToLast(50)
        val option = FirebaseRecyclerOptions.Builder<Message>()
            .setQuery(query, Message::class.java)
            .build()
        adapter = MyMessageAdapter(option)

        adapter.registerAdapterDataObserver(object:RecyclerView.AdapterDataObserver(){
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                binding.recyclerView.scrollToPosition(adapter.itemCount-1)
            }
        })


        binding.apply {
            recyclerView.layoutManager = layoutManager
            recyclerView.adapter = adapter

            sendbtn.setOnClickListener {
                val id = Firebase.auth.currentUser!!.uid
                val text = sendtext.text.toString()
                val time = System.currentTimeMillis()
                val timestr = SimpleDateFormat("HH:mm").format(time)

                val message = Message(id, text, timestr)
                chattingRoom.push().setValue(message)
                binding.sendtext.text.clear()
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