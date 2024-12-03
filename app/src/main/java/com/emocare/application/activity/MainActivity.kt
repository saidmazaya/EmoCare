package com.emocare.application.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.emocare.application.R
import com.emocare.application.databinding.ActivityMainBinding

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

            binding.navBottom.setupWithNavController(navHost.navController)

            // Tangkap data dari Intent
            val fragmentToLoad = intent.getStringExtra("EXTRA_FRAGMENT")
            val score = intent.getIntExtra("EXTRA_SCORE", 0)
            val testType = intent.getStringExtra("EXTRA_TEST_TYPE")
            val navController = navHost.navController


            if (fragmentToLoad == "HasilTesGkFragment") {
                val bundle = Bundle().apply {
                    putInt("score", score) // Sesuaikan nama argumen
                    putString("testType", testType)
                }
                Log.d(
                    "MainActivity",
                    "Fragment: $fragmentToLoad, Score: $score, TestType: $testType"
                )

                // Menggunakan popUpTo untuk menghapus fragment sebelumnya dan memastikan hanya satu fragment
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(
                        R.id.homeFragment,
                        true
                    ) // Pastikan semua fragment dihapus hingga HomeFragment
                    .build()

                navController.navigate(R.id.hasilTesGkFragment, bundle, navOptions)
            }

            navController.addOnDestinationChangedListener { _, destination, _ ->
                // Cek apakah fragment yang dipilih adalah HasilTesGkFragment
                if (destination.id == R.id.hasilTesGkFragment) {
                    binding.navBottom.menu.findItem(R.id.homeFragment)?.isChecked = false
                }
            }
        }
    }
}