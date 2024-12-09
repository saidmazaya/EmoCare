package com.emocare.application.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.emocare.application.R
import com.emocare.application.adapter.Doctor
import com.emocare.application.adapter.DoctorAdapter

class PsikologFragment : Fragment() {

    private lateinit var btnBackDetail: ImageButton
    private lateinit var btnPindah: View

    // Data dokter
    private val allDoctors = listOf(
        Doctor(
            "dr. Amanda Charoline Sp.Kj",
            "Psikolog Klinis",
            "5 Tahun",
            "Rp. 50.000",
            "Perempuan",
            R.drawable.dokter1
        ),
        Doctor(
            "dr. Andi Wirawan Sp.A",
            "Psikolog Anak",
            "8 Tahun",
            "Rp. 60.000",
            "Laki-laki",
            R.drawable.dokter2
        ),
        Doctor(
            "dr. Lisa Hartono Sp.Kj",
            "Psikolog Klinis",
            "10 Tahun",
            "Rp. 75.000",
            "Perempuan",
            R.drawable.dokter3
        ),
        Doctor(
            "dr. Rian Prasetya Sp.A",
            "Psikolog Anak",
            "6 Tahun",
            "Rp. 55.000",
            "Laki-laki",
            R.drawable.dokter4
        ),
        Doctor(
            "dr. Nadira Mahendra Sp.Kj",
            "Psikolog Klinis",
            "7 Tahun",
            "Rp. 65.000",
            "Perempuan",
            R.drawable.dokter5
        ),
        Doctor(
            "dr. Fahri Ramadhan Sp.A",
            "Psikolog Anak",
            "9 Tahun",
            "Rp. 70.000",
            "Laki-laki",
            R.drawable.dokter6
        ),
        Doctor(
            "dr. Silvia Anggraini Sp.Kj",
            "Psikolog Klinis",
            "12 Tahun",
            "Rp. 80.000",
            "Perempuan",
            R.drawable.dokter7
        )
    )

    private var filteredDoctors = allDoctors

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout untuk fragment ini
        return inflater.inflate(R.layout.fragment_detail_counseling, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inisialisasi RecyclerView
        val recyclerView: RecyclerView = view.findViewById(R.id.rvDoctors)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = DoctorAdapter(filteredDoctors) { selectedDoctor ->
            // Tangani klik item dokter
            handleDoctorClick(selectedDoctor)
        }

        // Inisialisasi spinner untuk filter
        val skillSpinner: Spinner = view.findViewById(R.id.filterSkill)
        val genderSpinner: Spinner = view.findViewById(R.id.filterGender)
        val experienceSpinner: Spinner = view.findViewById(R.id.filterExperience)
        val priceSpinner: Spinner = view.findViewById(R.id.filterPrice)

        // Set adapter untuk spinner
        val skills = arrayOf("Spesialisasi", "Psikolog Klinis", "Psikolog Anak")
        val genders = arrayOf("Gender", "Laki-laki", "Perempuan")
        val experiences = arrayOf(
            "Pengalaman",
            "5 Tahun",
            "6 Tahun",
            "7 Tahun",
            "8 Tahun",
            "9 Tahun",
            "10 Tahun",
            "12 Tahun"
        )
        val prices = arrayOf(
            "Harga",
            "Rp. 50.000",
            "Rp. 55.000",
            "Rp. 60.000",
            "Rp. 65.000",
            "Rp. 70.000",
            "Rp. 75.000",
            "Rp. 80.000"
        )

        skillSpinner.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, skills)
        genderSpinner.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, genders)
        experienceSpinner.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            experiences
        )
        priceSpinner.adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, prices)

        // Spinner listener untuk menangani filter
        skillSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                filterDoctors()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }

        genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                filterDoctors()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }

        recyclerView.adapter = DoctorAdapter(filteredDoctors) {
            findNavController().navigate(R.id.action_psikologFragment_to_detail3CounsellingFragment)
        }

        experienceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                filterDoctors()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }

        btnBackDetail = view.findViewById(R.id.btn_back_detail_counseling)
        btnBackDetail.setOnClickListener {
            // Memastikan untuk menghapus fragment sebelumnya di backstack
            findNavController().popBackStack(R.id.gangguanKecemasan, false)

            // Navigasi langsung ke EmotFragment
            findNavController().navigate(R.id.action_psikologFragment_to_counselingFragment)
        }

        priceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                filterDoctors()
            }

            override fun onNothingSelected(parentView: AdapterView<*>?) {}
        }
    }

    private fun filterDoctors() {
        val skill = view?.findViewById<Spinner>(R.id.filterSkill)?.selectedItem.toString()
        val gender = view?.findViewById<Spinner>(R.id.filterGender)?.selectedItem.toString()
        val experience = view?.findViewById<Spinner>(R.id.filterExperience)?.selectedItem.toString()
        val price = view?.findViewById<Spinner>(R.id.filterPrice)?.selectedItem.toString()

        filteredDoctors = allDoctors.filter {
            (skill == "Spesialisasi" || it.specialization.contains(skill, ignoreCase = true)) &&
                    (gender == "Gender" || it.gender.contains(gender, ignoreCase = true)) &&
                    (experience == "Pengalaman" || it.experience == experience) &&
                    (price == "Harga" || it.price == price)
        }

        // Update RecyclerView with filtered doctors
        val recyclerView: RecyclerView = view?.findViewById(R.id.rvDoctors) ?: return
        recyclerView.adapter = DoctorAdapter(filteredDoctors) { selectedDoctor ->
            handleDoctorClick(selectedDoctor)
        }
    }

    // Fungsi untuk menangani klik pada item dokter
    private fun handleDoctorClick(doctor: Doctor) {
        // Contoh: Tampilkan pesan Toast
        Toast.makeText(requireContext(), "Dokter dipilih: ${doctor.name}", Toast.LENGTH_SHORT)
            .show()
    }
}