package com.emocare.application.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import com.emocare.application.R
import com.emocare.application.adapter.Doctor
import com.emocare.application.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Firebase initialization
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // SharedPreferences for login session management
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("is_logged_in", false)
        val loginTime = sharedPreferences.getLong("login_time", 0L)
        val currentTime = System.currentTimeMillis()

        // Set login expiration time (1 minute in milliseconds)
        val oneMinuteInMillis = 60 * 1000

        if (!isLoggedIn || (currentTime - loginTime) > oneMinuteInMillis) {
            // If not logged in or session expired, go to WelcomeActivity
            val editor = sharedPreferences.edit()
            editor.putBoolean("is_logged_in", false)
            editor.apply()

            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
            return
        } else {
            // If logged in, continue with MainActivity setup
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            // Set up edge-to-edge layout for window insets
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
                insets
            }

            // Set up navigation
            val navHost = supportFragmentManager
                .findFragmentById(R.id.navhost_home) as NavHostFragment
            binding.navBottom.setupWithNavController(navHost.navController)

            // Capture any data passed from the Intent
            val fragmentToLoad = intent.getStringExtra("EXTRA_FRAGMENT")
            val score = intent.getIntExtra("EXTRA_SCORE", 0)
            val testType = intent.getStringExtra("EXTRA_TEST_TYPE")
            val navController = navHost.navController

            // If fragmentToLoad is set to "HasilTesGkFragment", navigate to it
            if (fragmentToLoad == "HasilTesGkFragment") {
                val bundle = Bundle().apply {
                    putInt("score", score) // Adjust argument name as needed
                    putString("testType", testType)
                }
                Log.d(TAG, "Fragment: $fragmentToLoad, Score: $score, TestType: $testType")

                // Use popUpTo to remove previous fragments and ensure only one fragment is displayed
                val navOptions = NavOptions.Builder()
                    .setPopUpTo(R.id.homeFragment, true) // Remove all fragments until HomeFragment
                    .build()


                navController.navigate(R.id.hasilTesGkFragment, bundle, navOptions)
            }

            // Listener to change selected bottom nav item when certain fragments are displayed
            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id == R.id.hasilTesGkFragment) {
                    binding.navBottom.menu.findItem(R.id.homeFragment)?.isChecked = false
                }
            }

            // Log successful login
            Log.d(TAG, "Login successful: User is still logged in.")
        }
    }

    private fun validateAndRegister(
        email: String,
        password: String,
        name: String,
        gender: String,
        ttl: String
    ) {
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Sedang memvalidasi login...")
        progressDialog.show()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressDialog.dismiss()

                if (task.isSuccessful) {
                    val firebaseUser = auth.currentUser
                    firebaseUser?.let { user ->
                        val userProfile = hashMapOf(
                            "uid" to user.uid,
                            "name" to name,
                            "email" to email,
                            "gender" to gender,
                            "dateOfBirth" to ttl
                        )

                        firestore.collection("users")
                            .document(user.uid)
                            .set(userProfile)
                            .addOnSuccessListener {
                                Log.d(TAG, "User profile successfully stored in Firestore.")
                            }
                            .addOnFailureListener { e ->
                                Log.e(TAG, "Firestore error: ${e.message}")
                                Toast.makeText(this, "Gagal menyimpan data pengguna", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    Log.e(TAG, "Error: ${task.exception?.message}")
                    Toast.makeText(this, "Pendaftaran gagal.", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                progressDialog.dismiss()
                Log.e(TAG, "Registration error: ${exception.message}")
                Toast.makeText(this, "Terjadi kesalahan: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    val doctors = listOf(
        Doctor("dr. Amanda Charoline Sp.Kj", "Psikolog Klinis", "5 Tahun", "Rp. 50.000", R.drawable.dokter1),
        Doctor("dr. Andi Wirawan Sp.A", "Psikolog Anak", "8 Tahun", "Rp. 60.000", R.drawable.dokter2),
        Doctor("dr. Lisa Hartono Sp.Kj", "Psikolog Klinis", "10 Tahun", "Rp. 75.000", R.drawable.dokter3),
        Doctor("dr. Rian Prasetya Sp.A", "Psikolog Anak", "6 Tahun", "Rp. 55.000", R.drawable.dokter4),
        Doctor("dr. Nadira Mahendra Sp.Kj", "Psikolog Klinis", "7 Tahun", "Rp. 65.000", R.drawable.dokter5),
        Doctor("dr. Fahri Ramadhan Sp.A", "Psikolog Anak", "9 Tahun", "Rp. 70.000", R.drawable.dokter6),
        Doctor("dr. Silvia Anggraini Sp.Kj", "Psikolog Klinis", "12 Tahun", "Rp. 80.000", R.drawable.dokter7)
    )

//    val recyclerView: RecyclerView = findViewById(R.id.rvDoctors)
//    recyclerView.layoutManager = LinearLayoutManager(this)
//    recyclerView.adapter = DoctorAdapter(doctors)
}