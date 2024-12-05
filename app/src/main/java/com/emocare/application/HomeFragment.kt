package com.emocare.application

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {
    private lateinit var btnPindah: Button
    private lateinit var tvHelloUser: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inisialisasi Firebase Auth dan Firestore
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi Views
        tvHelloUser = view.findViewById(R.id.tv_hello_user)
        btnPindah = view.findViewById(R.id.btn_chat)

        // Ambil nama pengguna dari Firestore dan tampilkan
        val currentUser = auth.currentUser
        if (currentUser != null) {
            val uid = currentUser.uid
            firestore.collection("users").document(uid).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val name = document.getString("name") ?: "Pengguna"
                        tvHelloUser.text = "Halo, $name"
                    } else {
                        tvHelloUser.text = "Halo, Pengguna"
                    }
                }
                .addOnFailureListener {
                    tvHelloUser.text = "Halo, Pengguna"
                }
        } else {
            tvHelloUser.text = "Halo, Pengguna"
        }

        // Set listener untuk tombol pindah
        btnPindah.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
            findNavController().navigate(R.id.action_homeFragment_to_chatFragment)
        }
    }
}
