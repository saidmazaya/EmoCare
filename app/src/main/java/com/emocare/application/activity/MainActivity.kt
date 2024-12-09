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
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.emocare.application.R
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

            binding.navBottom.setOnItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.homeFragment -> {
                        navController.navigateWithClearStack(R.id.homeFragment)
                        true
                    }

                    R.id.exploreFragment -> {
                        navController.navigateWithClearStack(R.id.exploreFragment)
                        true
                    }

                    R.id.emotFragment -> {
                        navController.navigateWithClearStack(R.id.emotFragment)
                        true
                    }

                    R.id.counselingFragment -> {
                        navController.navigateWithClearStack(R.id.counselingFragment)
                        true
                    }

                    R.id.accountFragment -> {
                        navController.navigateWithClearStack(R.id.accountFragment)
                        true
                    }

                    else -> false
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
                                Toast.makeText(
                                    this,
                                    "Gagal menyimpan data pengguna",
                                    Toast.LENGTH_SHORT
                                ).show()
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
                Toast.makeText(this, "Terjadi kesalahan: ${exception.message}", Toast.LENGTH_SHORT)
                    .show()
            }
    }

    private fun NavController.navigateWithClearStack(destinationId: Int) {
        val navOptions = NavOptions.Builder()
            .setPopUpTo(
                graph.startDestinationId,
                true
            ) // Clear all fragments until the start destination
            .setLaunchSingleTop(true) // Prevent duplicate fragments
            .build()
        this.navigate(destinationId, null, navOptions)
    }
}