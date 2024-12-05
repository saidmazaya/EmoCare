package com.emocare.application.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emocare.application.R
import com.emocare.application.fragment.EmotFragment
import android.content.SharedPreferences
import com.emocare.application.singleton.CalendarItem

class CalendarAdapter(
    private val items: List<CalendarItem>,
    private val onItemClick: (CalendarItem) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_calendar, parent, false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size

    inner class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDay: TextView = itemView.findViewById(R.id.tv_day)
        private val tvEmoji: TextView = itemView.findViewById(R.id.tv_emoji)

        fun bind(item: CalendarItem) {
            tvDay.text = item.date
            tvEmoji.text = item.emoticon
            itemView.setOnClickListener {
                onItemClick(item)
            }
        }
    }
}
