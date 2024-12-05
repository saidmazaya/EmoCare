package com.emocare.application.model

import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import java.util.Locale
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emocare.application.singleton.CalendarItem

class CalendarViewModel : ViewModel() {
    private val _calendarItems = MutableLiveData<List<CalendarItem>>()
    val calendarItems: LiveData<List<CalendarItem>> = _calendarItems

    init {
        // Inisialisasi kalender dengan tanggal bulan ini
        val calendar = Calendar.getInstance()
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val currentMonth = SimpleDateFormat("yyyy-MM", Locale.getDefault()).format(calendar.time)
        val items = (1..daysInMonth).map {
            CalendarItem(date = String.format("%02d", it))
        }
        _calendarItems.value = items
    }

    // Update emoji untuk tanggal tertentu
    fun updateEmoticon(date: String, emoticon: String) {
        _calendarItems.value = _calendarItems.value?.map {
            if (it.date == date) it.copy(emoticon = emoticon) else it
        }
    }
}
