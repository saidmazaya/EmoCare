package com.emocare.application.activity

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.emocare.application.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.auth.FirebaseAuth
import java.util.*

class ManageProfileActivity : AppCompatActivity() {

    private lateinit var saveButton: Button
    private lateinit var fullNameEditText: EditText
    private lateinit var birthDateTextView: TextView
    private lateinit var genderGroup: RadioGroup

    private val db = FirebaseFirestore.getInstance()  // Firestore instance
    private val auth = FirebaseAuth.getInstance()  // FirebaseAuth instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_manage_profile)

        // Find the back arrow ImageView
        val backArrow = findViewById<ImageView>(R.id.back_arrow)
        backArrow.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()  // Using updated onBackPressed
        }

        // Bind views
        saveButton = findViewById(R.id.save_changes_button)
        fullNameEditText = findViewById(R.id.full_name)
        birthDateTextView = findViewById(R.id.birth_date)
        genderGroup = findViewById(R.id.gender_group)

        // Get current user's UID
        val userId = auth.currentUser?.uid

        if (userId != null) {
            // Fetch user data from Firestore and populate the form
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val fullName = document.getString("name")
                        val birthDate = document.getString("dateOfBirth")
                        val gender = document.getString("gender")

                        // Set the initial data in the form fields
                        fullNameEditText.setText(fullName)
                        birthDateTextView.text = birthDate

                        // Set gender radio button
                        if (gender == "Laki-laki") {
                            genderGroup.check(R.id.male)
                        } else if (gender == "Perempuan") {
                            genderGroup.check(R.id.female)
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Gagal memuat data: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                }
        }

        // Set DatePickerDialog for birthDate TextView
        birthDateTextView.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH)

            // Create DatePickerDialog
            val datePickerDialog = DatePickerDialog(
                this,
                { _, year, monthOfYear, dayOfMonth ->
                    val selectedDate = "$dayOfMonth/${monthOfYear + 1}/$year"
                    birthDateTextView.text = selectedDate
                },
                year,
                month,
                dayOfMonth
            )
            datePickerDialog.show()
        }

        // Button save action
// Button save action
        saveButton.setOnClickListener {
            val fullName = fullNameEditText.text.toString()
            val birthDate = birthDateTextView.text.toString()

            // Determine selected gender from RadioGroup
            val selectedGender = when (genderGroup.checkedRadioButtonId) {
                R.id.male -> "Laki-laki"
                R.id.female -> "Perempuan"
                else -> "Tidak Ditentukan"
            }

            if (userId != null) {
                // Create a map with the updated user data
                val userData = hashMapOf(
                    "name" to fullName,
                    "dateOfBirth" to birthDate,
                    "gender" to selectedGender
                )

                // Update the user's document in Firestore
                db.collection("users").document(userId)
                    .update(userData as Map<String, Any>)
                    .addOnSuccessListener {
                        // Show a success message
                        Toast.makeText(this, "Profil berhasil diperbarui!", Toast.LENGTH_LONG)
                            .show()

                        // Set result and finish to notify AccountFragment
                        setResult(RESULT_OK)
                        finish()  // Close the activity and return to AccountFragment
                    }
                    .addOnFailureListener { e ->
                        // Show an error message
                        Toast.makeText(this, "Terjadi kesalahan: ${e.message}", Toast.LENGTH_LONG)
                            .show()
                    }
            } else {
                Toast.makeText(this, "Pengguna tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        }

    }
}
