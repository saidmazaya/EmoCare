package com.emocare.application.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.emocare.application.R
import java.text.SimpleDateFormat
import java.util.*

class CalendarAdapter(
    private var days: List<Date>, // Ubah menjadi var agar bisa diperbarui
    private val onDateClick: (Date) -> Unit
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    private val emotMap = HashMap<String, Int>()

    // Highlight today's date by comparing with current date
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    fun setEmot(date: String, emotRes: Int) {
        emotMap[date] = emotRes // This stores emoticons for the exact date
        notifyDataSetChanged() // Update RecyclerView UI
    }


    // Fungsi untuk memperbarui data tanggal
    fun updateDates(newDays: List<Date>) {
        days = newDays
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_calendar_date, parent, false)
        return CalendarViewHolder(view)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date = days[position]
        val dateString = dateFormat.format(date)

        // Get current month and year for comparison
        val currentCalendar = Calendar.getInstance()
        val currentMonth = currentCalendar.get(Calendar.MONTH)
        val currentYear = currentCalendar.get(Calendar.YEAR)

        // Get month and year of the date to compare
        val dateCalendar = Calendar.getInstance()
        dateCalendar.time = date
        val dateMonth = dateCalendar.get(Calendar.MONTH)
        val dateYear = dateCalendar.get(Calendar.YEAR)

        // Check if the date is from the current month and year
        val isInCurrentMonth = currentMonth == dateMonth && currentYear == dateYear
        val isToday = isToday(date)

        Log.d(
            "CalendarAdapter",
            "Binding date: $dateString, isToday: $isToday, isInCurrentMonth: $isInCurrentMonth"
        )

        // Bind data to the ViewHolder
        holder.bind(dateString, emotMap[dateString], isToday, isInCurrentMonth)
    }

    override fun getItemCount(): Int = days.size

    class CalendarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvDate: TextView = itemView.findViewById(R.id.tv_date)
        private val ivEmoticon: ImageView = itemView.findViewById(R.id.iv_emoticon)

        fun bind(date: String, emotRes: Int?, isToday: Boolean, isInCurrentMonth: Boolean) {
            tvDate.text = date.split("/")[0] // Display the day of the month

            // Apply special styling if today
            if (isToday) {
                tvDate.setBackgroundColor(Color.parseColor("#E9C2C5")) // Red background for today
                tvDate.setTextColor(Color.BLACK) // Black text for today
            } else {
                tvDate.setBackgroundColor(Color.TRANSPARENT) // Default transparent background
                tvDate.setTextColor(Color.BLACK) // Default text color
            }

            // Apply gray color for dates not in the current month
            if (!isInCurrentMonth) {
                tvDate.setTextColor(Color.GRAY) // Change text color to gray for dates outside the current month
            }

            if (emotRes != null) {
                ivEmoticon.visibility = View.VISIBLE
                try {
                    ivEmoticon.setImageResource(emotRes)
                    Log.d("CalendarAdapter", "ImageView set with resource ID: $emotRes")
                } catch (e: Exception) {
                    Log.e("CalendarAdapter", "Invalid resource ID: $emotRes", e)
                }
            } else {
                ivEmoticon.visibility = View.GONE
            }
        }
    }

    private fun isToday(date: Date): Boolean {
        val calendarToday = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val calendarInput = Calendar.getInstance().apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        return calendarToday.timeInMillis == calendarInput.timeInMillis
    }
}
