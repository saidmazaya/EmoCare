package com.emocare.application

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.emocare.application.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

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
        val oneMinuteInMillis = 60 * 1000000000000

        if (!isLoggedIn || (currentTime - loginTime) > oneMinuteInMillis) {
            val editor = sharedPreferences.edit()
            editor.putBoolean("is_logged_in", false)
            editor.apply()

            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
            return
        } else {
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

            // Additional Firebase setup or UI integration if needed
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
}
