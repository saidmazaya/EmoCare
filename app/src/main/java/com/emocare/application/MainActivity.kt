package com.emocare.application

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.emocare.application.databinding.ActivityMainBinding
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
        val loginTime = sharedPreferences.getLong("login_time", 0L)
        val currentTime = System.currentTimeMillis()

        // Set waktu kedaluwarsa login (1 menit dalam milidetik)
        val oneMinuteInMillis = 60 * 1000

        // Cek apakah login sudah kadaluarsa
        if (!isLoggedIn || (currentTime - loginTime) > oneMinuteInMillis) {
            // Jika belum login atau login sudah kadaluarsa, arahkan ke WelcomeActivity
            val editor = sharedPreferences.edit()
            editor.putBoolean("is_logged_in", false) // Set ulang status login menjadi false
            editor.apply()

            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
            return
        } else {
            // Jika login masih valid, lanjutkan inisialisasi MainActivity
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
                insets
            }

            val navHost = supportFragmentManager
                .findFragmentById(R.id.navhost_home) as NavHostFragment

            val doctors = listOf(
                Doctor("dr. Amanda Charoline Sp.Kj", "Psikologi Klinis", "5 Tahun", "Rp. 50.000", R.drawable.dokter1),
                Doctor("dr. Andi Wirawan Sp.A", "Anak", "8 Tahun", "Rp. 60.000", R.drawable.dokter2),
                Doctor("dr. Lisa Hartono Sp.OG", "Obgyn", "10 Tahun", "Rp. 75.000", R.drawable.dokter3)
            )

            val recyclerView: RecyclerView = findViewById(R.id.rvDoctors)
            recyclerView.layoutManager = LinearLayoutManager(this)
            recyclerView.adapter = DoctorAdapter(doctors)



            binding.navBottom.setupWithNavController(navHost.navController)
        }

    }
}