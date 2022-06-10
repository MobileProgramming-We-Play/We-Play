package com.example.weplay

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weplay.databinding.RowSeoulGuBinding
import com.example.weplay.domain.BaseCity

class BaseCityAdapter(val items: ArrayList<BaseCity>) : RecyclerView.Adapter<BaseCityAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(data: BaseCity, position: Int)
    }

    lateinit var listener: OnItemClickListener

    inner class ViewHolder(val binding: RowSeoulGuBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            with(binding) {
                seoulGu.setOnClickListener {
                    listener.onItemClick(items[adapterPosition], adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowSeoulGuBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.binding) {
            seoulGu.text = items[position].name
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}