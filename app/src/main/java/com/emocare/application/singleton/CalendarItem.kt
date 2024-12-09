package com.emocare.application.singleton

data class CalendarItem(
    val date: String, // Format tanggal: "yyyy-MM-dd"
    var emoticon: String? = null // Nilai emoticon, bisa null jika belum dipilih
)