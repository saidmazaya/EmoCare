package com.emocare.application

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {
    private lateinit var etTtl: EditText
    private lateinit var ivCalendar: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryColor)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.primaryColor)

        etTtl = findViewById(R.id.etTtl)
        ivCalendar = findViewById(R.id.ivCalendar)

        // Listener untuk membuka DatePicker saat EditText diklik
        etTtl.setOnClickListener {
            showDatePickerDialog()
        }

        // Listener untuk membuka DatePicker saat ikon kalender diklik
        ivCalendar.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        // Set tanggal default ke hari ini
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Format tanggal menjadi "dd/MM/yyyy"
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                etTtl.setText(selectedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }
}
