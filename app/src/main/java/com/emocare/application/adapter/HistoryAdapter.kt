package com.emocare.application.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emocare.application.R

data class HistoryItem(val date: String, val orderType: String, val doctor: String)

class HistoryAdapter(private val historyList: List<HistoryItem>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val tvOrderType: TextView = itemView.findViewById(R.id.tvOrderType)
        val tvDoctor: TextView = itemView.findViewById(R.id.tvDoctor)
        val btnStatus: Button = itemView.findViewById(R.id.btnStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)
        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val historyItem = historyList[position]
        holder.tvDate.text = historyItem.date
        holder.tvOrderType.text = historyItem.orderType
        holder.tvDoctor.text = historyItem.doctor
        holder.btnStatus.text = "SELESAI"
    }

    override fun getItemCount(): Int = historyList.size
}