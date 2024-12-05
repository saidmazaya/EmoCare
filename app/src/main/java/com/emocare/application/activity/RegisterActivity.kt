package com.emocare.application.activity

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.emocare.application.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var etTtl: EditText
    private lateinit var ivCalendar: ImageView
    private lateinit var rBtn1: Button
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etKPassword: EditText
    private lateinit var genderSpinner: Spinner

    companion object {
        private const val TAG = "RegisterActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Set status and navigation bar colors
        window.statusBarColor = ContextCompat.getColor(this, R.color.primaryColor)
        window.navigationBarColor = ContextCompat.getColor(this, R.color.primaryColor)

        // Initialize Firebase components
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Initialize UI components
        initializeViews()

        // Set up listeners
        setupListeners()
    }

    private fun initializeViews() {
        etTtl = findViewById(R.id.etTtl)
        ivCalendar = findViewById(R.id.ivCalendar)
        rBtn1 = findViewById(R.id.R_btn1)
        etName = findViewById(R.id.etName)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etKPassword = findViewById(R.id.etKPassword)
        genderSpinner = findViewById(R.id.Gender)
    }

    private fun setupListeners() {
        // Back button listener
        findViewById<ImageView>(R.id.iconBack).setOnClickListener {
            finish()
        }

        // Date picker listeners
        etTtl.setOnClickListener { showDatePickerDialog() }
        ivCalendar.setOnClickListener { showDatePickerDialog() }

        // Register button listener
        rBtn1.setOnClickListener { validateAndRegister() }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                etTtl.setText(selectedDate)
            },
            year,
            month,
            day
        ).show()
    }

    private fun validateAndRegister() {
        // Input validation
        val name = etName.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()
        val confirmPassword = etKPassword.text.toString().trim()
        val gender = genderSpinner.selectedItem.toString()
        val ttl = etTtl.text.toString().trim()

        // Comprehensive input validation
        when {
            name.isEmpty() -> {
                etName.error = "Nama harus diisi"
                etName.requestFocus()
                return
            }
            email.isEmpty() -> {
                etEmail.error = "Email harus diisi"
                etEmail.requestFocus()
                return
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                etEmail.error = "Email tidak valid"
                etEmail.requestFocus()
                return
            }
            password.isEmpty() -> {
                etPassword.error = "Password harus diisi"
                etPassword.requestFocus()
                return
            }
            password.length < 6 -> {
                etPassword.error = "Password minimal 6 karakter"
                etPassword.requestFocus()
                return
            }
            confirmPassword.isEmpty() -> {
                etKPassword.error = "Konfirmasi password harus diisi"
                etKPassword.requestFocus()
                return
            }
            password != confirmPassword -> {
                Toast.makeText(this, "Password tidak cocok!", Toast.LENGTH_SHORT).show()
                return
            }
            ttl.isEmpty() -> {
                etTtl.error = "Tanggal lahir harus diisi"
                etTtl.requestFocus()
                return
            }
        }

        // Proceed with registration
        registerWithFirebase(email, password, name, gender, ttl)
    }

    private fun registerWithFirebase(email: String, password: String, name: String, gender: String, ttl: String) {
        // Show loading indicator
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Sedang mendaftar...")
        progressDialog.show()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressDialog.dismiss()

                if (task.isSuccessful) {
                    // Get current user
                    val firebaseUser = auth.currentUser
                    firebaseUser?.let { user ->
                        // Create user profile in Firestore
                        val userProfile = hashMapOf(
                            "uid" to user.uid,
                            "name" to name,
                            "email" to email,
                            "gender" to gender,
                            "dateOfBirth" to ttl
                        )
                        Log.d(TAG, "Data yang dikirim ke Firestore: $userProfile")

                        // Save to Firestore
                        firestore.collection("users")
                            .document(user.uid)
                            .set(userProfile)
                            .addOnSuccessListener {
                                // Save to SharedPreferences
                                saveUserToSharedPreferences(name, email, gender, ttl)

                                // Navigate to MainActivity
                                navigateToMainActivity()
                            }
                            .addOnFailureListener { e ->
                                Log.e(TAG, "Firestore error: ${e.message}")
                                Toast.makeText(this, "Gagal menyimpan profil", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    // Handle specific registration errors
                    handleRegistrationError(task.exception)
                }
            }
            .addOnFailureListener { exception ->
                progressDialog.dismiss()
                Log.e(TAG, "Registration failed: ${exception.message}")
                Toast.makeText(this, "Pendaftaran gagal: ${exception.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun handleRegistrationError(exception: Exception?) {
        val errorMessage = when (exception) {
            is FirebaseAuthUserCollisionException -> "Email sudah terdaftar"
            is FirebaseAuthWeakPasswordException -> "Password terlalu lemah"
            null -> "Terjadi kesalahan yang tidak diketahui"
            else -> exception.message ?: "Pendaftaran gagal"
        }

        Log.e(TAG, "Registration error: ${exception?.message}")
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    private fun saveUserToSharedPreferences(name: String, email: String, gender: String, ttl: String) {
        val sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putBoolean("is_logged_in", true)
            putString("user_name", name)
            putString("user_email", email)
            putString("user_gender", gender)
            putString("user_ttl", ttl)
        }.apply()
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}