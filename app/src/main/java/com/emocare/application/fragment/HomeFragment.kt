package com.emocare.application.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emocare.application.R
import com.emocare.application.adapter.QuoteAdapter
import com.google.firebase.auth.FirebaseAuth
import com.emocare.application.singleton.DataSingleton

import com.google.firebase.firestore.FirebaseFirestore
import java.util.Calendar
import kotlin.random.Random

class HomeFragment : Fragment() {
    private lateinit var btnPindah: ImageButton
    private lateinit var btnCounselling: ImageButton
    private lateinit var btnTes: ImageButton
    private lateinit var btntesKejiwaan: Button
    private lateinit var btnArtikel: ImageButton
    private lateinit var tvHelloUser: TextView
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: QuoteAdapter
    private lateinit var quotes: List<String>
    private lateinit var btnTemuiPsikolog: ImageButton
    private lateinit var btnTemuiPsikolog2: Button

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
        btnCounselling = view.findViewById(R.id.btn_counselling)
        btnTes = view.findViewById(R.id.btn_tes_kejiwaan)
        btntesKejiwaan = view.findViewById(R.id.btn_tes_kejiwaan_2)
        btnArtikel = view.findViewById(R.id.btn_artikel_home)
        btnTemuiPsikolog = view.findViewById(R.id.img_btn_temui_psikolog)
        btnTemuiPsikolog2 = view.findViewById(R.id.btn_temui_pisokolog_2)

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

        val emotButtons = listOf(
            view.findViewById<Button>(R.id.btn_emotBahagia),
            view.findViewById<Button>(R.id.btn_emotSedih),
            view.findViewById<Button>(R.id.btn_emotMarah),
            view.findViewById<Button>(R.id.btn_emotNetral),
            view.findViewById<Button>(R.id.btn_emotMual),
            view.findViewById<Button>(R.id.btn_emotKaget)
        )

        emotButtons.forEach { button ->
            button.setOnClickListener {
                // Navigasi: popBackStack untuk memastikan kembali ke homeFragment
                findNavController().popBackStack(R.id.homeFragment, false)

                // Navigasi ke fragment target (emotFragment)
                findNavController().navigate(R.id.action_homeFragment_to_emotFragment)
            }
        }

        btntesKejiwaan.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
            findNavController().navigate(R.id.action_homeFragment_to_emotFragment)
        }

        // Set listener untuk tombol pindah
        btnPindah.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
            findNavController().navigate(R.id.action_homeFragment_to_chatFragment)
        }

        // Set listener untuk tombol konsultasi
        btnCounselling.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
            findNavController().navigate(R.id.action_homeFragment_to_counselingFragment)
        }

        // Set listener untuk tombol artikel
        btnArtikel.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
            findNavController().navigate(R.id.action_homeFragment_to_artikelFragment)
        }

        // Set listener untuk tombol temui psikolog
        btnTemuiPsikolog.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
            findNavController().navigate(R.id.action_homeFragment_to_psikologFragment)
        }

        btnTemuiPsikolog2.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
            findNavController().navigate(R.id.action_homeFragment_to_psikologFragment)
        }


        // Set listener untuk tombol tes
        btnTes.setOnClickListener {
            findNavController().popBackStack(R.id.homeFragment, false)
            findNavController().navigate(R.id.action_homeFragment_to_emotFragment)
        }

        // Load quotes from string-array
        quotes = resources.getStringArray(R.array.mental_health_quotes).toList()

        // Shuffle quotes based on the current day
        val randomSeed = Calendar.getInstance().get(Calendar.DAY_OF_YEAR)
        quotes = quotes.shuffled(Random(randomSeed)).take(3) // Limit to 3 items

        // Set up RecyclerView
        recyclerView = view.findViewById(R.id.rv_quotes)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = QuoteAdapter(quotes)
        recyclerView.adapter = adapter
    }
}