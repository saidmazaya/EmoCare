package com.emocare.application.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emocare.application.R
import com.emocare.application.adapter.CalendarAdapter
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class EmotFragment : Fragment() {
    private lateinit var btnPindah: Button
    private lateinit var btnPindah2: Button
    private lateinit var btnPrevMonth: Button
    private lateinit var btnNextMonth: Button
    private lateinit var calendarAdapter: CalendarAdapter
    private lateinit var calendar: Calendar
    private lateinit var currentMonthDates: List<Date>
    private var selectedEmot: Int? = null
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var tvCurrentMonth: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_emot, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("EmotData", Context.MODE_PRIVATE)
        calendar = Calendar.getInstance()
        btnPindah = view.findViewById(R.id.btn_arrowGangguanKecemasan)
        btnPindah.setOnClickListener {
            findNavController().popBackStack(R.id.emotFragment, false)
            findNavController().navigate(R.id.action_emotFragment_to_gangguanKecemasan)
        }

        btnPindah2 = view.findViewById(R.id.btn_arrowDepresi)
        btnPindah2.setOnClickListener {
            findNavController().popBackStack(R.id.emotFragment, false)
            findNavController().navigate(R.id.action_emotFragment_to_depresiFragment)
        }

        btnPrevMonth = view.findViewById(R.id.btn_arrowPrev)
        btnNextMonth = view.findViewById(R.id.btn_arrowNext)

        btnPrevMonth.setOnClickListener {
            calendar.add(Calendar.MONTH, -1) // Pindah ke bulan sebelumnya
            calendar.set(Calendar.DAY_OF_MONTH, 1) // Reset to the first day of the previous month
            calendar.set(Calendar.HOUR_OF_DAY, 0) // Set time to midnight
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            updateCalendar()
            updateMonthLabel() // Perbarui teks bulan
        }

        btnNextMonth.setOnClickListener {
            calendar.add(Calendar.MONTH, +1) // Pindah ke bulan berikutnya
            calendar.set(Calendar.DAY_OF_MONTH, +1) // Reset to the first day of the next month
            calendar.set(Calendar.HOUR_OF_DAY, 0) // Set time to midnight
            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            updateCalendar()
            updateMonthLabel() // Perbarui teks bulan
        }


        tvCurrentMonth = view.findViewById(R.id.tv_current_month)
        updateMonthLabel()

        val rvCalendar = view.findViewById<RecyclerView>(R.id.rv_calendar)
        rvCalendar.layoutManager = GridLayoutManager(requireContext(), 7)

        currentMonthDates = generateDatesForMonth(calendar)
        calendarAdapter = CalendarAdapter(currentMonthDates) { date ->
            val dateString = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(date)
            if (selectedEmot != null) {
                calendarAdapter.setEmot(dateString, selectedEmot!!)
                saveEmotData(dateString, selectedEmot!!)
            }
        }

        // Load previously saved emoticons from SharedPreferences for each day in the current month
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        currentMonthDates.forEach { date ->
            val dateString = formatter.format(date)
            val savedEmot = getEmotData(dateString)
            if (savedEmot != null && savedEmot != -1) {
                calendarAdapter.setEmot(dateString, savedEmot)
            }
        }

        rvCalendar.adapter = calendarAdapter
        setupEmotButtons(view)
    }

    private fun saveEmotData(date: String, emotRes: Int) {
        sharedPreferences.edit()
            .putInt(date, emotRes)
            .apply()
        Log.d("EmotFragment", "Saved emoticon $emotRes for $date")
    }

    private fun getEmotData(date: String): Int? {
        return if (sharedPreferences.contains(date)) {
            sharedPreferences.getInt(date, -1) // -1 is the default if not found
        } else {
            null
        }
    }

    private fun updateCalendar() {
        currentMonthDates = generateDatesForMonth(calendar)
        calendarAdapter.updateDates(currentMonthDates)

        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        currentMonthDates.forEach { date ->
            val dateString = formatter.format(date)
            val savedEmot = getEmotData(dateString)
            if (savedEmot != null && savedEmot != -1) {
                calendarAdapter.setEmot(dateString, savedEmot)
            }
        }
        calendarAdapter.notifyDataSetChanged()
    }

    private fun generateDatesForMonth(calendar: Calendar): List<Date> {
        val tempCalendar = Calendar.getInstance().apply {
            time = calendar.time // Correctly set to the target month
            set(Calendar.DAY_OF_MONTH, 1) // Start from the first day of the month
        }

        val dates = mutableListOf<Date>()

        // Add previous month's days to fill the first week
        val firstDayOfWeek = tempCalendar.get(Calendar.DAY_OF_WEEK) - 1
        val previousMonthCalendar = tempCalendar.clone() as Calendar
        previousMonthCalendar.add(Calendar.DAY_OF_MONTH, -firstDayOfWeek)

        for (i in 0 until firstDayOfWeek) {
            dates.add(previousMonthCalendar.time)
            previousMonthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        // Add current month's days
        val daysInMonth = tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (day in 1..daysInMonth) {
            tempCalendar.set(Calendar.DAY_OF_MONTH, day)
            dates.add(tempCalendar.time)
        }

        // Add next month's days to complete the week grid
        val nextMonthCalendar = tempCalendar.clone() as Calendar
        nextMonthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        while (dates.size % 7 != 0) {
            dates.add(nextMonthCalendar.time)
            nextMonthCalendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        return dates
    }


    private fun setupEmotButtons(view: View) {
        val emotButtons = mapOf(
            R.id.btn_emotBahagia to R.drawable.emot_bahagia,
            R.id.btn_emotSedih to R.drawable.emot_sedih,
            R.id.btn_emotNetral to R.drawable.emot_netral,
            R.id.btn_emotKaget to R.drawable.emot_kaget,
            R.id.btn_emotMual to R.drawable.emot_mual,
            R.id.btn_emotMarah to R.drawable.emot_marah
        )

        for ((buttonId, emotRes) in emotButtons) {
            view.findViewById<Button>(buttonId).setOnClickListener {
                selectedEmot = emotRes
                Log.d("EmotFragment", "Selected emoticon: $emotRes")

                val today = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

                saveEmotData(today, emotRes)
                calendarAdapter.setEmot(today, emotRes)
            }
        }
    }

    private fun updateMonthLabel() {
        val monthFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        tvCurrentMonth.text = monthFormat.format(calendar.time)
    }

}
