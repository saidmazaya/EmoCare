package com.emocare.application.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.emocare.application.R
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {
    private lateinit var etTtl: EditText
    private lateinit var ivCalendar: ImageView
    private lateinit var rBtn1: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryColor)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.primaryColor)

        etTtl = findViewById(R.id.etTtl)
        ivCalendar = findViewById(R.id.ivCalendar)
        rBtn1 = findViewById(R.id.R_btn1)  // Inisialisasi button R_btn1

        // Listener untuk membuka DatePicker saat EditText diklik
        etTtl.setOnClickListener {
            showDatePickerDialog()
        }

        // Listener untuk membuka DatePicker saat ikon kalender diklik
        ivCalendar.setOnClickListener {
            showDatePickerDialog()
        }

        // Listener untuk mengatur nilai true saat R_btn1 diklik
        rBtn1.setOnClickListener {
            setButtonStateTrue()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                etTtl.setText(selectedDate)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    // Fungsi untuk mengatur nilai menjadi true saat R_btn1 ditekan
    private fun setButtonStateTrue() {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        // Simpan status login dan waktu login saat ini
        editor.putBoolean("is_logged_in", true)
        editor.putLong("login_time", System.currentTimeMillis())  // Simpan waktu login dalam milidetik
        editor.apply()

        // Pindah ke MainActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

}
