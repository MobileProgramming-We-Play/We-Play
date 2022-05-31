package com.example.weplay.party

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weplay.databinding.RowBinding
import com.example.weplay.domain.Party
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class MyPartyAdapter(options: FirebaseRecyclerOptions<Party>)
    : FirebaseRecyclerAdapter<Party, MyPartyAdapter.ViewHolder>(options){

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    var itemClickListener:OnItemClickListener?=null;

    inner class ViewHolder(val binding: RowBinding):RecyclerView.ViewHolder(binding.root){
        init{
            binding.root.setOnClickListener{
                itemClickListener!!.onItemClick(bindingAdapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Party) {
        holder.binding.apply {
            ptitle.text = model.ptitle.toString()
            psports.text = model.psports.toString()
            //pnownum.text = model.pnowNum.toString()
            ptotalnum.text = model.pparticipantsNum.toString()
        }
    }


}
