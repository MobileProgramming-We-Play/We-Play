package com.example.weplay.party

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weplay.databinding.PartyRowBinding
import com.example.weplay.domain.Party
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class PartiesAdapter(options: FirebaseRecyclerOptions<Party>) :
    FirebaseRecyclerAdapter<Party, PartiesAdapter.ViewHolder>(options) {

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    var itemClickListener: OnItemClickListener? = null

    inner class ViewHolder(val binding: PartyRowBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                itemClickListener!!.onItemClick(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = PartyRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Party) {
        with(holder.binding) {
            thumbPartyTitle.text = model.ptitle
            thumbPartyParticipantsNum.text = model.pparticipantsNum.toString()
        }
    }

}