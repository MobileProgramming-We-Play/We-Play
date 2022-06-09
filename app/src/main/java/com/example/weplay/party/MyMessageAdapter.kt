package com.example.weplay.party

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weplay.databinding.RowMeBinding
import com.example.weplay.databinding.RowOtherBinding
import com.example.weplay.domain.Message
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MyMessageAdapter(options: FirebaseRecyclerOptions<Message>) :
    FirebaseRecyclerAdapter<Message, RecyclerView.ViewHolder>(options) {

    val uid = Firebase.auth.currentUser?.uid

    override fun getItemViewType(position: Int): Int {
        if (getItem(position).uid != uid) {
            return 0
        } else {
            return 1
        }
    }

    inner class OtherViewHolder(val binding: RowOtherBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    inner class MyViewHolder(val binding: RowMeBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == 0) { // 상대 메세지
            val view = RowOtherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return OtherViewHolder(view)
        } else { // 내 메세지
            val view = RowMeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return MyViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, model: Message) {

        if (uid != model.uid) {
            (holder as OtherViewHolder).binding.apply {
                Firebase.database.getReference("Users/${model.uid}").get().addOnSuccessListener {
                    userid.text = it.child("nickName").value.toString()
                }
                time.text = model.time
                text.text = model.text
            }
        } else {
            (holder as MyViewHolder).binding.apply {
                time.text = model.time
                text.text = model.text
            }
        }
    }
}