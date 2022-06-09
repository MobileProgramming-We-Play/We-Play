package com.example.weplay.party

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weplay.databinding.RowBinding
import com.example.weplay.domain.Party
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import java.util.*

class MyPartyAdapter(options: FirebaseRecyclerOptions<Party>)
    : FirebaseRecyclerAdapter<Party, MyPartyAdapter.ViewHolder>(options){
    private lateinit var calendar: Calendar
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
            calendar = Calendar.getInstance()
            calendar.timeInMillis = model.pdate

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH) + 1
            val day = calendar.get(Calendar.DATE)
            var text:String = year.toString() + "년 " + month.toString() + "월 " + day.toString() + "일"

            ptitle.text = model.ptitle.toString()
            psports.text = model.psports.toString()
            pnownum.text = model.pparticipants.size.toString()
            ptotalnum.text = model.pparticipantsNum.toString()
            pcity.text = model.pcity.toString()
            pdate.text = text

        }
    }


}
