package com.emocare.application.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.emocare.application.R
import com.emocare.application.activity.ManageProfileActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AccountFragment : Fragment() {

    private lateinit var profileImage: ImageView
    private lateinit var profileName: TextView
    private lateinit var profilePhone: TextView
    private lateinit var profileFollowers: TextView
    private lateinit var profileFollowing: TextView
    private lateinit var manageButton: Button
    private lateinit var transactionHistoryButton: Button

    private val db = FirebaseFirestore.getInstance()  // Firestore instance
    private val auth = FirebaseAuth.getInstance()  // FirebaseAuth instance

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_account, container, false)

        // Bind views
        profileImage = view.findViewById(R.id.profile_image)
        profileName = view.findViewById(R.id.profile_name)
        profilePhone = view.findViewById(R.id.profile_phone)
        profileFollowers = view.findViewById(R.id.profile_followers)
        profileFollowing = view.findViewById(R.id.profile_following)
        manageButton = view.findViewById(R.id.manage_button)
        transactionHistoryButton = view.findViewById(R.id.transaction_history)

        // Fetch user data from Firestore
        loadUserData()

        // Set a listener for the "Kelola" button
        manageButton.setOnClickListener {
            Log.d("AccountFragment", "Kelola button clicked")
            // Start ManageProfileActivity and listen for result
            val intent = Intent(requireActivity(), ManageProfileActivity::class.java)
            startActivityForResult(intent, 100)  // 100 is the request code
        }

        // Set a listener for the "Riwayat Pesanan" button
        transactionHistoryButton.setOnClickListener {
            findNavController().popBackStack(R.id.accountFragment, false)
            findNavController().navigate(R.id.action_accountFragment_to_historyFragment)
        }

        return view
    }

    // Load user data from Firestore
    private fun loadUserData() {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val fullName = document.getString("name") ?: "Nama Pengguna"
                        val phone = document.getString("email") ?: "Email"

                        // Set profile data
                        profileName.text = fullName
                        profilePhone.text = phone

                        // Set dummy followers and following data
                        profileFollowers.text = "100 Pengikut"  // Dummy data
                        profileFollowing.text = "50 Mengikuti"  // Dummy data
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("AccountFragment", "Error fetching user data: ${e.message}")
                }
        }
    }

    // Handle the result from ManageProfileActivity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == AppCompatActivity.RESULT_OK) {
            // If the result is OK, reload the user data to refresh the profile
            loadUserData()
        }
    }
}
