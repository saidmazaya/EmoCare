package com.emocare.application.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.emocare.application.R

class LoginActivity : AppCompatActivity() {

    private lateinit var L_Back: ImageView
    private lateinit var L_btn1: Button  // Inisialisasi tombol login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        // Cek apakah pengguna sudah login dan apakah login telah kedaluwarsa
        if (isLoginValid()) {
            // Jika sudah login dan belum kedaluwarsa, langsung ke MainActivity
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Tutup LoginActivity agar tidak muncul lagi
            return // Hentikan eksekusi lebih lanjut
        }

        L_Back = findViewById(R.id.L_Back)
        L_btn1 = findViewById(R.id.L_btn1)  // Temukan tombol login di layout

        btnBackLoginListener()
        btnLoginListener()
    }

    private fun btnBackLoginListener() {
        L_Back.setOnClickListener {
            // Arahkan ke WelcomeActivity saat tombol back ditekan
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun btnLoginListener() {
        L_btn1.setOnClickListener {
            // Tandai pengguna sebagai "logged in" dan arahkan ke MainActivity
            markUserAsLoggedIn()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    // Fungsi untuk menandai pengguna sebagai "logged in" dan menyimpan waktu login
    private fun markUserAsLoggedIn() {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("is_logged_in", true)
        editor.putLong("login_time", System.currentTimeMillis()) // Simpan waktu login dalam milidetik
        editor.apply()
    }

    // Fungsi untuk memeriksa apakah login masih valid (tidak kedaluwarsa)
    private fun isLoginValid(): Boolean {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
        val loginTime = sharedPreferences.getLong("login_time", 0L)

        if (!isLoggedIn) {
            return false
        }

        // Hitung selisih waktu antara sekarang dengan waktu login
        val currentTime = System.currentTimeMillis()
        val oneMinuteInMillis = 60 * 1000 // 1 menit dalam milidetik

        return (currentTime - loginTime) < oneMinuteInMillis
    }
}
